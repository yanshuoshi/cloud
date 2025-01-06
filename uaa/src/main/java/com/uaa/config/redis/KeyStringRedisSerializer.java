package com.uaa.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 添加默认前缀
 * @author lijianbin
 * @date 2021-09-15 09:57
 **/
@Component
public class KeyStringRedisSerializer implements RedisSerializer<String> {
    private final Charset charset;
    @Value("${spring.application.name}")
    private String cachePrefix;

    public KeyStringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public KeyStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    /**
     * 获取redis缓存:
     * 默认添加项目name为前缀;
     * 调用其他项目redis缓存,需在以redis字段为前缀+项目name+redisKey
     */
    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null) {
            String redis = "redis:";
            return new String(bytes, charset).startsWith(redis) ?
                    new String(bytes, charset).replaceFirst(redis, "") :
                    new String(bytes, charset).replaceFirst(cachePrefix + ":", "");
        }
        return null;
    }

    /**
     * 设置redis缓存:
     * 默认添加项目name为前缀
     */
    @Override
    public byte[] serialize(String string) throws SerializationException {
        String redis = "redis:";
        if (string == null) {
            return null;
        } else {
            string = string.startsWith(redis) ? string.replaceFirst(redis, "") : cachePrefix + ":" + string;
            return string.getBytes(this.charset);
        }
    }
}
