package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysAdmin;
import com.rbac.project.entity.dto.*;
import com.rbac.project.entity.vo.CaptchaVo;
import com.rbac.project.entity.vo.LoginSuccessVo;
import com.rbac.project.entity.vo.QuerySysAdminVo;
import com.rbac.project.entity.vo.SysAdminVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户 服务层接口
 */
public interface SysAdminService extends IService<SysAdmin> {

    Page<QuerySysAdminVo> querySysAdminPageList(BaseQueryDto baseQueryDto, QuerySysAdminDto querySysAdminDto);

    Boolean addSysAdmin(AddSysAdminDto addSysAdminDto);

    SysAdminVo querySysAdminVoById(Integer id);

    Boolean updateSysAdmin(EditSysAdminDto editSysAdminDto);

    Boolean deleteSysAdmin(Integer id);

    Boolean updateSysAdminStatus(UpdateStatusDto updateStatusDto);

    Boolean resetPassword(ResetPasswordDto resetPasswordDto);

    SysAdmin updateCurrentSysAdmin(SysAdminEditDto sysAdminEditDto);

    Boolean updateCurrentAdminPassword(AdminResetPwdDto adminResetPwdDto);

    CaptchaVo captcha(HttpServletResponse response) throws IOException;

    LoginSuccessVo login(SysAdminLoginDto sysAdminLoginDto);

    Boolean logout();

}
