package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 分页查询登录日志参数对象
 */
@Data
public class QueryLoginInfoDto {
    private String username;
    private String loginStatus;
    private String beginTime;
    private String endTime;
}
