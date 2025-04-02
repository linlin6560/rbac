package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 重置密码参数对象
 */
@Data
public class ResetPasswordDto {
    @NotNull(message = "用户id不能为空")
    private Integer id;
    @NotEmpty(message = "用户密码不能为空")
    private String password;
}
