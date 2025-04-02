package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 查询用户参数对象
 */
@Data
public class QuerySysAdminDto {
	private String username;
	private Integer status;
	private String beginTime;
	private String endTime;
}
