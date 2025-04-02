package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 用户修改参数对象
 */
@Data
public class SysAdminEditDto {
	@NotEmpty(message = "账号不能为空")
	private String username;
	@NotEmpty(message = "昵称不能为空")
	private String nickname;
	@NotEmpty(message = "邮箱不能为空")
	@Email(message = "邮箱格式错误")
	private String email;
	@NotEmpty(message = "联系电话不能为空")
	@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
	private String phone;
	private String icon;
	private String note;
}
