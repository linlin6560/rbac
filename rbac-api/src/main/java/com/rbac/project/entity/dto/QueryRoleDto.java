package com.rbac.project.entity.dto;

import lombok.Data;

/**
 * 查询角色参数对象
 */
@Data
public class QueryRoleDto {
	private String roleName;
	private String roleKey;
	private String status;
	private String beginTime;
	private String endTime;
}
