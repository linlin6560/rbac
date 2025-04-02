package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改角色参数对象
 */
@Data
public class EditSysRoleDto {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;
    @NotEmpty(message = "角色权限字符串不能为空")
    private String roleKey;
    @NotNull(message = "角色状态不能为空")
    private Integer status;
    @NotEmpty(message = "备注不能为空")
    private String description;
}
