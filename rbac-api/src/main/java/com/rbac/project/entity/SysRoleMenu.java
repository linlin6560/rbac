package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色与菜单关系
 */
@Data
@TableName("sys_role_menu")
@Accessors(chain = true)
public class SysRoleMenu {
    private Integer roleId;
    private Integer menuId;
}
