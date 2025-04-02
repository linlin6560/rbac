package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 查询部门列表参数
 */
@Data
public class QuerySysDeptDto {
    private String deptName;
    private String deptStatus;
}
