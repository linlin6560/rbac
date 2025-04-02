package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 新增部门参数
 */
@Data
public class AddSysDeptDto {
    private Integer parentId;
    private Integer deptType;
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;
    private Integer deptStatus;
}
