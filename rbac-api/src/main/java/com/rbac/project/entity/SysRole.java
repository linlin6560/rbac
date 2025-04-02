package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 角色
 */
@Data
@TableName("sys_role")
@Accessors(chain = true)
public class SysRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String roleName;
    @TableField("role_key")
    private String roleKey;
    private Integer status;
    private String description;
    private Date createTime;
}
