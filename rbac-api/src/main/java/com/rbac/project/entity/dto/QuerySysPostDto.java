package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 查询岗位参数对象
 */
@Data
public class QuerySysPostDto {
    private String postName;
    private String postStatus;
    private String beginTime;
    private String endTime;
}
