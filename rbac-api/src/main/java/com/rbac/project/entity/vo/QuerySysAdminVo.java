package com.rbac.project.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户对象
 */
@Data
public class QuerySysAdminVo {
	private Integer id;
	private Integer deptId;
	private String deptName;
	private Integer postId;
	private String postName;
	private String roleName;
	private String username;
	private String nickname;
	private Integer sex;
	private String email;
	private String phone;
	private String icon;
	private Integer status;
	private Date createTime;
	private String note;
}
