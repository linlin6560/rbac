package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 新增用户参数对象
 */
@Data
public class AddSysAdminDto {
    @NotNull(message = "请选择部门")
    private Integer deptId;
    @NotNull(message = "请选择岗位")
    private Integer postId;
    @NotNull(message = "角色不能为空")
    private Integer roleId;
    @NotEmpty(message = "账号不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "昵称不能为空")
    private String nickname;
    private Integer sex;
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;
    @NotEmpty(message = "联系电话不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;
    private Integer status;
    @NotEmpty(message = "备注不能为空")
    private String note;
}
