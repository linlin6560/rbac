package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 新增角色参数对象
 */
@Data
public class AddSysRoleDto {
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;
    @NotEmpty(message = "角色权限字符串不能为空")
    private String roleKey;
    @NotNull(message = "角色状态不能为空")
    private Integer status;
    @NotEmpty(message = "备注不能为空")
    private String description;
}
