package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 查询菜单列表参数
 */
@Data
public class QuerySysMenuDto {
    private String menuName;
    private String menuStatus;
}
