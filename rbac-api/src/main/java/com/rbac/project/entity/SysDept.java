package com.rbac.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 部门
 */
@Data
@TableName("sys_dept")
@Accessors(chain = true)
public class SysDept {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private Integer deptType;
    private String deptName;
    private Integer deptStatus;
    private Date createTime;
}
