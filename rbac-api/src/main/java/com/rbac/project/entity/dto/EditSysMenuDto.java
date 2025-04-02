package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改菜单参数对象
 */
@Data
public class EditSysMenuDto {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;
    private Integer parentId;
    private Integer sort;
    private String url;
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;
    private Integer menuStatus;
    private String value;
    private String icon;
}
