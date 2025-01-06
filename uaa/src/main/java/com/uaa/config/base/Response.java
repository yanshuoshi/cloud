package com.uaa.config.base;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * json响应消息对象
 * @author
 * @date 2021-07-21 09:51
 **/
@Data
@Builder
@NoArgsConstructor
public class Response<T> {
    /** 状态码 */
    Integer code;
    /** 响应状态信息 */
    String message;
    /** 响应数据 */
    T data;

    Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功响应.
     */
    public static <T> Response<T> ok(){
        return new Response<>(HttpStatus.OK.value(), "操作成功！", null);
    }

    /**
     * 返回成功响应.
     * @param data  数据
     */
    public static <T> Response<T> ok(T data){
        return new Response<>(HttpStatus.OK.value(), "操作成功！", data);
    }

    /**
     * 返回错误的响应.
     * @param message   错误信息
     */
    public static <T> Response<T> failed(String message){
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    /**
     * 返回错误的响应.
     * @param code      状态码
     * @param message   错误信息
     */
    public static <T> Response<T> error(Integer code, String message){
        return new Response<>(code, message, null);
    }

    /**
     * 返回错误的响应.
     * @param code      状态码
     * @param message   错误信息
     */
    public static <T> Response<T> error(Integer code, String message,T data){
        return new Response<>(code, message, data);
    }
}
