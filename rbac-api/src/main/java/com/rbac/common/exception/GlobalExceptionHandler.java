package com.rbac.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.rbac.common.result.JsonResult;
import com.rbac.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public JsonResult businessException(BusinessException e) {
        if (e.getCode() == null) {
            return JsonResult.failed(e.getMessage());
        }
        return JsonResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 未登录处理
     */
    @ExceptionHandler(value = NotLoginException.class)
    public JsonResult handleNotLoginException(NotLoginException e) {
        return JsonResult.error(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage());
    }

    /**
     * 校验参数
     */
    @ExceptionHandler(value = BindException.class)
    public JsonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return JsonResult.failed(message);
    }

    /**
     * 没有权限异常处理
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public JsonResult handleNotPermissionException(NotPermissionException e) {
        return JsonResult.error(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage());
    }
}
