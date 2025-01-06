package com.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.gateway.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 全局过滤器
 * 用于检查传入请求的 OAuth2 访问令牌的有效性。如果令牌有效，它将从令牌中提取用户信息，
 * 并将这些信息添加到请求头中以便后续的服务使用。如果令牌无效或不存在，
 * 它将返回一个未授权的 HTTP 响应。
 */
@Component
@Slf4j
public class GatewayFilterConfig implements GlobalFilter, Ordered {


    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        //1 uaa服务所有放行
        if (pathMatcher.match("/uaa/**", requestUrl)
                || pathMatcher.match("/school/actuator/sentinel", requestUrl)) {
            return chain.filter(exchange);
        }
        //2 检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)) {
            return noTokenMono(exchange);
        }
        //3 判断是否是黑名单里的token
        String s = redisTemplate.opsForValue().get("base-service:jwtblacklist:" + token);
        if(StringUtils.isNotBlank(s)){
            return blacklistToken(exchange);
        }
        //4 判断是否是有效的token
        OAuth2AccessToken oAuth2AccessToken;
        try {
            oAuth2AccessToken = tokenStore.readAccessToken(token);
            // token是否过期
            if (oAuth2AccessToken == null || oAuth2AccessToken.isExpired()) { // 新增对令牌过期的检查
                throw new InvalidTokenException("Access token is expired or invalid.");
            }
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            //取出用户身份信息
            String principal = MapUtils.getString(additionalInformation, "user_name");
            //获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("principal", principal);
            jsonObject.put("authorities", authorities);
            //给header里面添加值
            String base64 = EncryptUtil.encodeUTF8StringBase64(jsonObject.toJSONString());
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("json-token", base64).build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        } catch (InvalidTokenException e) {
            log.error("无效的token: {}", token, e);
            return invalidTokenMono(exchange);
        }
    }


    /**
     * 获取token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }


    /**
     * 无效的token
     */
    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
        JSONObject json = new JSONObject();
        json.put("code", HttpStatus.UNAUTHORIZED.value());
        json.put("data", "无效的token");
        return buildReturnMono(json, exchange);
    }

    /**
     * 黑名单中的token
     */
    private Mono<Void> blacklistToken(ServerWebExchange exchange) {
        JSONObject json = new JSONObject();
        json.put("code", HttpStatus.UNAUTHORIZED.value());
        json.put("data", "token已失效");
        return buildReturnMono(json, exchange);
    }

    private Mono<Void> noTokenMono(ServerWebExchange exchange) {
        JSONObject json = new JSONObject();
        json.put("code", HttpStatus.UNAUTHORIZED.value());
        json.put("data", "没有token");
        return buildReturnMono(json, exchange);
    }


    private Mono<Void> buildReturnMono(JSONObject json, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        byte[] bits = json.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}