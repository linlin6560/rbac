package com.rbac.project.entity.vo;

import com.rbac.project.entity.SysAdmin;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 登录成功后对象
 */
@Data
@Accessors(chain = true)
public class LoginSuccessVo {
    private String token;
    private SysAdmin sysAdmin;
    private List<String> valuesList;
    private List<SysMenuOneVo> list;
}
