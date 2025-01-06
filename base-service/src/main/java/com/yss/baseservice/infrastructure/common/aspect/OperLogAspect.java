package com.yss.baseservice.infrastructure.common.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yss.baseservice.application.system.LogApplication;
import com.yss.baseservice.domain.system.entity.SysUser;
import com.yss.baseservice.domain.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;

/**
 * 切面处理类，操作日志记录处理
 *
 * @author lijianbin
 * @date 2020-12-25 11:29
 **/
@Aspect
@Component
@RequiredArgsConstructor
public class OperLogAspect {
    private final RedisTemplate<String, String> redisTemplate;
    private final LogApplication logApplication;
    private final SysUserMapper sysUserMapper;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 统计请求的处理时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

//    public OperLogAspect(RedisTemplate<String, String> redisTemplate, LogApplication logApplication, SysUserMapper sysUserMapper) {
//        this.redisTemplate = redisTemplate;
//        this.logApplication = logApplication;
//        this.sysUserMapper = sysUserMapper;
//    }

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.yss.baseservice.infrastructure.common.aspect.OperLog)")
    public void operLogPoincut() {
    }


    @Before("operLogPoincut()")
    public void doBefore() {
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     */
    @AfterReturning(value = "operLogPoincut()")
    public void saveOperLog(JoinPoint joinPoint) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        OperLog operLog = method.getAnnotation(OperLog.class);

        // 用户名
        // 无token接口处理
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = (String) authentication.getPrincipal();
            SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username).eq("status", 1));
            logApplication.saveOperationLog(operLog.operModule(), operLog.operType(), sysUser.getRealName(), 1, sysUser.getUserId());
        }
    }
}
