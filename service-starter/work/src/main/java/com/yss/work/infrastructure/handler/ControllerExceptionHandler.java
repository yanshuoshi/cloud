package com.yss.work.infrastructure.handler;

import com.common.base.BaseException;
import com.common.base.Response;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.common.base.Response.error;

/**
 * 全局异常处理器，用于处理各种类型的异常。
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 处理使用 @Valid 或 @RequestBody 注解时，参数验证失败的情况。
     *
     * @param e 验证失败异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldErrors().stream().findFirst().orElse(null);
        if (fieldError != null) {
            return error(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage());
        } else {
            return error(HttpStatus.BAD_REQUEST.value(), "Validation failed");
        }
    }

    /**
     * 处理使用 @RequestParam 上的 validate 失败后抛出的异常。
     *
     * @param e 验证失败异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 处理 GET 请求中使用 @Valid 验证路径中请求实体校验失败后抛出的异常。
     *
     * @param e 绑定异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 处理自定义的 BaseException 异常。
     *
     * @param e 自定义异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response baseExceptionHandler(BaseException e) {
        return error(e.getCode(), e.getMessage());
    }

    /**
     * 处理认证失败的异常。
     *
     * @param e 认证异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Response authenticationExceptionHandler(AuthenticationException e) {
        log.error("Authentication failed", e);
        return error(HttpStatus.UNAUTHORIZED.value(), "认证异常");
    }

    /**
     * 处理访问被拒绝的异常。
     *
     * @param e 访问被拒绝异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response accessDeniedExceptionHandler(AccessDeniedException e) {
        log.error("Access denied", e);
        return error(HttpStatus.FORBIDDEN.value(), "没有权限");
    }

    /**
     * 处理无权限访问的异常。
     *
     * @param e 访问被拒绝异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(FeignException.Forbidden.class)
    @ResponseBody
    public Response handleFeignForbiddenException(FeignException.Forbidden e) {
        log.error("Access denied", e);
        return error(HttpStatus.FORBIDDEN.value(), "没有权限");
    }

    /**
     * 处理未预期的异常。
     *
     * @param e 未知异常
     * @return 包含错误信息的响应对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response globalExceptionHandler(Exception e) {
        log.error("Unknown error", e);
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统故障！");
    }

}

