package com.yss.baseservice.infrastructure.common.response;


import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String jti;
}
