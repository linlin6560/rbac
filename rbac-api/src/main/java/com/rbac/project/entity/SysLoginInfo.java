package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 登录日志记录
 */
@Data
@TableName("sys_login_info")
@Accessors(chain = true)
public class SysLoginInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String ipAddress;
    private String loginLocation;
    private String browser;
    private String os;
    private Integer loginStatus;
    private String message;
    private Date loginTime;
}
