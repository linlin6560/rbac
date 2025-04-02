package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户
 */
@Data
@TableName("sys_admin")
@Accessors(chain = true)
public class SysAdmin {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer postId;
    private Integer deptId;
    private String username;
    private String password;
    private String nickname;
    private Integer sex;
    private String icon;
    private String email;
    private String phone;
    private String note;
    private Integer status;
    private Date createTime;
}
