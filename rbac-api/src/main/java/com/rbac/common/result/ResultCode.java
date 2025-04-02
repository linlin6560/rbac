package com.rbac.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码定义
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    UNAUTHORIZED(401, "token已过期或者未登录"),
    FORBIDDEN(403, "没有权限"),
    POST_NAME_IS_EXIST(405, "岗位名称已存在"),
    POST_CODE_IS_EXIST(406, "岗位编码已存在"),
    DEPT_NAME_IS_EXIST(407, "部门名称已存在，请重新输入"),
    COMPANY_IS_EXIST(408, "公司已存在"),
    DEPT_HAS_CHILD(409, "部门存在子部门，不能删除"),
    DEPT_HAS_USER(410, "部门存在用户,不允许删除"),
    MENU_NAME_IS_EXIST(411, "菜单名称已存在，请重新输入"),
    NOR_DELETE_MENU(412, "菜单已分配，不能删除"),
    MENU_HAS_CHILD(413, "菜单存在子菜单，不能删除"),
    ROLE_NAME_IS_EXIST(414, "角色名称已存在"),
    ROLE_KEY_IS_EXIST(415, "角色标识已存在"),
    NOR_DELETE_ROLE(416, "角色已分配，不能删除"),
    USER_NAME_IS_EXIST(417, "账号已存在，请重新输入"),
    NICKNAME_IS_EXIST(418, "昵称已存在，请重新输入"),
    PHONE_IS_EXIST(419, "手机号已存在，请重新输入"),
    EMAIL_IS_EXIST(420, "邮箱已存在，请重新输入"),
    QUERY_ADMIN_IS_NULL(421, "未查询到该用户，请重新查询"),
    OLD_PASSWORD_IS_FALSE(422, "您输入的旧密码不正确"),
    THE_NEW_PASSWORD_IS_NOT_THE_SAME_AS_THE_REPEATED_PASSWORD(423, "新密码和重复密码不一样"),
    CAPTCHA_LOSE_EFFICACY(424, "验证码已失效，请获取新的验证码"),
    CAPTCHA_IS_ERROR(425, "您输入的验证码不正确，请您重新输入"),
    USER_NAME_IS_NOT_EXIST(426, "账号不存在，请您确认你的账号"),
    PASSWORD_IS_NOT_TRUE(427, "您输入的密码不正确"),
    USERNAME_IS_DISABLE(428, "您的账号已停用，请联系管理员"),
    LOGIN_SUCCESS(1, "登录成功"),
    LOGIN_FAIL(2, "登录失败"),
    OPERATOR_SUCCESS(0, "正常"),
    OPERATOR_FAIL(1, "失败"),
    ENABLE(1, "启用"),
    DISABLE(2, "停用"),
    ;

    private Integer code;
    private String message;
}
