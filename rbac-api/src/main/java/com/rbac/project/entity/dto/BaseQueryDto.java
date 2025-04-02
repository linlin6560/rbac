package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 分页参数基础类
 */
@Data
public class BaseQueryDto {
    private Long pageSize = Long.valueOf("10");
    private Long pageNum = Long.valueOf("1");
}
