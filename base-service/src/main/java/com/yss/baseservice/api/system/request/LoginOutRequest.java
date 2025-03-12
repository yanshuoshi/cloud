package com.yss.baseservice.api.system.request;


import lombok.Data;

@Data
public class LoginOutRequest {

    private String access_token;

    private String refresh_token;
}
