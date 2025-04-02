package com.rbac.project.entity.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录的对象参数
 */
@Data
public class SysAdminLoginDto {
    @NotEmpty(message = "账号不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "uuid不能为空")
    private String uuid;
    @NotEmpty(message = "验证码不能为空")
    private String image;
}
