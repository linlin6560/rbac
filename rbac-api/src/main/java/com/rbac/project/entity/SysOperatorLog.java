package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 操作日志记录
 */
@Data
@TableName("sys_operator_log")
@Accessors(chain = true)
public class SysOperatorLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer businessType;
    private String method;
    private String requestMethod;
    private String operatorName;
    private String operatorUrl;
    private String operatorIp;
    private String operatorLocation;
    private String operatorParam;
    private String jsonResult;
    private Integer operatorStatus;
    private String errorMsg;
    private Date operatorTime;
}
