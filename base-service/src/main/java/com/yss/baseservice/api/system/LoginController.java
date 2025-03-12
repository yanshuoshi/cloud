package com.yss.baseservice.api.system;


import com.common.base.BaseException;
import com.common.base.Response;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.yss.baseservice.api.system.request.LoginOutRequest;
import com.yss.baseservice.api.system.request.LoginRequest;
import com.yss.baseservice.api.system.request.RefreshRequest;
import com.yss.baseservice.domain.other.AuthorizationClient;
import com.yss.baseservice.domain.other.WorkService;
import com.yss.baseservice.infrastructure.common.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final AuthorizationClient authorizationClient;
    private final TokenStore tokenStore;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 登录
     * @param req
     * @return
     */
    @PostMapping("/login")
    public Response<TokenResponse> login(@RequestBody LoginRequest req) {
        TokenResponse token = authorizationClient.getToken("password", req.getUsername(),
                req.getPassword(), req.getUserType(),
                req.getClientId(), req.getSecret());
        return Response.ok(token);
    }

    /**
     * 刷新令牌
     *
     * @param req
     * @return
     */
    @PostMapping("/refreshToken")
    public Response<TokenResponse> login(@RequestBody RefreshRequest req) {
        TokenResponse token = authorizationClient.refreshToken("refresh_token",
                req.getRefreshToken(), req.getClientId(), req.getSecret());
        return Response.ok(token);
    }

    /**
     * 退出登录
     *
     * @param request
     */
    @GetMapping("/logout")
    public Response revokeToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String refreshTokenHeader = request.getHeader("Refresh-Token");
        if (StringUtils.isBlank(authHeader)) {
            throw new BaseException(500, "token不存在");
        }
        String tokenValue = authHeader.replace("Bearer", "").trim();
        String refreshTokenValue = StringUtils.trimToNull(refreshTokenHeader);
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        tokenStore.removeAccessToken(accessToken);
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 清除 session
            session.invalidate();
        }
        if (refreshTokenValue != null) {
            OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
            if (refreshToken != null) {
                tokenStore.removeRefreshToken(refreshToken);
            }
        }
        // 清除认证状态
        SecurityContextHolder.clearContext();
        // jwt黑名单 因为jwt是无状态的，所以需要将token加入黑名单
        redisTemplate.opsForValue().set("jwtblacklist:" + tokenValue, tokenValue, 24, TimeUnit.HOURS);
        // 如果有 Refresh Token，也加入黑名单
        if (refreshTokenValue != null) {
            redisTemplate.opsForValue().set("jwtblacklist:" + refreshTokenValue, refreshTokenValue, 24, TimeUnit.HOURS);
        }
        return Response.ok();
    }



}
