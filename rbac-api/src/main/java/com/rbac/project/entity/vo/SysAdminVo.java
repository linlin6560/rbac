package com.rbac.project.entity.vo;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class SysAdminVo {
    private Integer id;
    private Integer postId;
    private Integer deptId;
    private Integer roleId;
    private String username;
    private String nickname;
    private Integer sex;
    private String email;
    private String phone;
    private String note;
    private Integer status;
}
