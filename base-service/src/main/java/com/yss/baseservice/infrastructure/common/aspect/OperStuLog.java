package com.yss.baseservice.infrastructure.common.aspect;

import java.lang.annotation.*;

/**
 * 操作日志
 * @author
 * @date 2020-12-25 11:26
 **/
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperStuLog {
    /** 操作模块 */
    String operModule() default "";
    /** 操作平台类型 */
    String operType() default "";
    /** 操作说明 */
    String operDesc() default "";
}
