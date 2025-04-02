package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 查询操作日志参数
 */
@Data
public class QueryOperatorLogDto {
    private String operatorName;
    private String operatorStatus;
    private String beginTime;
    private String endTime;
}
