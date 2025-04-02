package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysRole;
import com.rbac.project.entity.vo.QueryRoleVo;
import com.rbac.project.entity.dto.*;

import java.util.List;

/**
 * 角色 服务层接口
 */
public interface SysRoleService extends IService<SysRole> {

    Page<SysRole> querySysRoleList(BaseQueryDto baseQueryDto, QueryRoleDto queryRoleDto);

    Boolean addSysRole(AddSysRoleDto addSysRoleDto);

    Boolean updateSysRole(EditSysRoleDto editSysRoleDto);

    Boolean deleteSysRole(Integer id);

    Boolean updateSysRoleStatus(UpdateStatusDto updateStatusDto);

    List<QueryRoleVo> queryRoleVoList();

    List<Integer> queryRoleMenuList(Integer id);

    Boolean assignPermission(AssPermissionsDto assPermissionsDto);
}
