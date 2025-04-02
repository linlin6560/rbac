package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 岗位
 */
@Data
@TableName("sys_post")
@Accessors(chain = true)
public class SysPost {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String postCode;
    private String postName;
    private Integer postStatus;
    private Date createTime;
    private String remark;
}
