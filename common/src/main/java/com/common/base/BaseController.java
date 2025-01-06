package com.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.common.config.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 基础
 * @author
 * @date 2020-12-29 11:29
 **/
public abstract class BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取当前登录用户账号
     * @return  用户账号
     */
    public String getUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取用户信息
     */
    public SecurityUser getUserInfo() {
        String username = this.getUsername();
        String text = redisTemplate.opsForValue().get("redis:uaa-server:" + username);
        return JSON.parseObject(text, new TypeReference<SecurityUser>() {});
    }

    /**
     * 获取用户id
     */
    public String getUserId() {
        return this.getUserInfo().getUserId();
    }
}
