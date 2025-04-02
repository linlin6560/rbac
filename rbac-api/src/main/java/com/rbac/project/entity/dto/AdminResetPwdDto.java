package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改用户密码参数对象
 */
@Data
public class AdminResetPwdDto {
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    @NotEmpty(message = "重置密码不能为空")
    private String resetPassword;
}
