package com.yss.baseservice.api.system.request;


import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

    private String userType;

    private String clientId;

    private String secret;
}
