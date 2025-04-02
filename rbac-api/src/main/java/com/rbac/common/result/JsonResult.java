package com.rbac.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> JsonResult<T> failed(String message) {
        return new JsonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    public static JsonResult error(int code, String message) {
        return new JsonResult(code, message, null);
    }
}
