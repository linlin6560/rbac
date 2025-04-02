package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户与角色关系
 */
@Data
@TableName("sys_admin_role")
@Accessors(chain = true)
public class SysAdminRole {
    private Integer adminId;
    private Integer roleId;
}
