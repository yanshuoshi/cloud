package com.common.base;

import lombok.Getter;

/**
 * 通用异常
 * @author
 * @date 2020-12-25 14:45
 **/
@Getter
public class BaseException extends RuntimeException{
    private final Integer code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
