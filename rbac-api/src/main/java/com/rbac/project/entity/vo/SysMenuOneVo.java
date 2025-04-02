package com.rbac.project.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 左侧菜单对象
 */
@Data
public class SysMenuOneVo {
	private Integer id;
	private String menuName;
	private String icon;
	private String url;
	private List<SysMenuTwoVo> children;
}
