package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分配权限参数对象
 */
@Data
public class AssPermissionsDto {
    @NotNull(message = "角色id不能为空")
    private Integer id;
    private List<Integer> menuIds;
}
