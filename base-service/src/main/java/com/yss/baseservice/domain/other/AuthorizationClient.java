package com.yss.baseservice.domain.other;

import com.yss.baseservice.infrastructure.common.response.TokenResponse;
import com.yss.baseservice.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "uaa-server",
        configuration = FeignConfig.class)
public interface AuthorizationClient {

    @PostMapping("/oauth/token")
    TokenResponse getToken(@RequestParam("grant_type") String grantType,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("userType") String userType,
                           @RequestParam("client_id") String client_id,
                           @RequestParam("client_secret") String secret);

    @PostMapping("/oauth/token")
    TokenResponse refreshToken(@RequestParam("grant_type") String grantType,
                               @RequestParam("refresh_token") String refreshToken,
                               @RequestParam("client_id") String clientId,
                               @RequestParam("client_secret") String clientSecret);

}
