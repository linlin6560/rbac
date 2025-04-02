package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 菜单权限
 */
@Data
@TableName("sys_menu")
@Accessors(chain = true)
public class SysMenu {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private String menuName;
    private String icon;
    private String value;
    private Integer menuType;
    private String url;
    private Integer menuStatus;
    private Integer sort;
    private Date createTime;
}
