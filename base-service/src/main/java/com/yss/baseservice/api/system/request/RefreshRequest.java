package com.yss.baseservice.api.system.request;


import lombok.Data;

@Data
public class RefreshRequest {

    private String refreshToken;

    private String clientId;

    private String secret;
}
