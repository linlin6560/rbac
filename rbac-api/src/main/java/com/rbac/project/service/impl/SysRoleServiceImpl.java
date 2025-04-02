package com.rbac.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.common.exception.BusinessException;
import com.rbac.common.result.ResultCode;
import com.rbac.project.entity.SysAdminRole;
import com.rbac.project.entity.SysRole;
import com.rbac.project.entity.SysRoleMenu;
import com.rbac.project.entity.vo.QueryRoleVo;
import com.rbac.project.entity.dto.*;
import com.rbac.project.mapper.SysAdminRoleMapper;
import com.rbac.project.mapper.SysRoleMapper;
import com.rbac.project.mapper.SysRoleMenuMapper;
import com.rbac.project.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 角色 服务层接口实现类
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysAdminRoleMapper sysAdminRoleMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Page<SysRole> querySysRoleList(BaseQueryDto baseQueryDto, QueryRoleDto queryRoleDto) {
        return sysRoleMapper.selectPage(new Page<>(baseQueryDto.getPageNum(), baseQueryDto.getPageSize()), new QueryWrapper<SysRole>()
                .like(StringUtils.isNotEmpty(queryRoleDto.getRoleName()), "role_name", queryRoleDto.getRoleName())
                .eq(StringUtils.isNotEmpty(queryRoleDto.getRoleKey()), "role_key", queryRoleDto.getRoleKey())
                .eq(StringUtils.isNotEmpty(queryRoleDto.getStatus()), "status", queryRoleDto.getStatus())
                .ge(StringUtils.isNotEmpty(queryRoleDto.getBeginTime()), "create_time", queryRoleDto.getBeginTime())
                .le(StringUtils.isNotEmpty(queryRoleDto.getEndTime()), "create_time", queryRoleDto.getEndTime())
                .orderByDesc("create_time"));
    }

    @Override
    public Boolean addSysRole(AddSysRoleDto addSysRoleDto) {
        SysRole existSysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_name", addSysRoleDto.getRoleName()));
        if (!ObjectUtils.isEmpty(existSysRole)) {
            throw new BusinessException(ResultCode.ROLE_NAME_IS_EXIST.getCode(), ResultCode.ROLE_KEY_IS_EXIST.getMessage());
        }
        SysRole sysRole = new SysRole()
                .setRoleName(addSysRoleDto.getRoleName())
                .setRoleKey(addSysRoleDto.getRoleKey())
                .setStatus(addSysRoleDto.getStatus())
                .setDescription(addSysRoleDto.getDescription())
                .setCreateTime(new Date());
        sysRoleMapper.insert(sysRole);
        return true;
    }

    @Override
    public Boolean updateSysRole(EditSysRoleDto editSysRoleDto) {
        SysRole existSysRole = sysRoleMapper.selectById(editSysRoleDto.getId());
        SysRole sysRoleByName = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_name", editSysRoleDto.getRoleName()));
        if (null != sysRoleByName && sysRoleByName.getId() != editSysRoleDto.getId()) {
            throw new BusinessException(ResultCode.ROLE_NAME_IS_EXIST.getCode(), ResultCode.ROLE_NAME_IS_EXIST.getMessage());
        }
        SysRole sysRoleByKey = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_key", editSysRoleDto.getRoleKey()));
        if (null != sysRoleByKey && sysRoleByKey.getId() != editSysRoleDto.getId()) {
            throw new BusinessException(ResultCode.ROLE_KEY_IS_EXIST.getCode(), ResultCode.ROLE_KEY_IS_EXIST.getMessage());
        }
        BeanUtils.copyProperties(editSysRoleDto, existSysRole);
        sysRoleMapper.updateById(existSysRole);
        return true;
    }

    @Override
    public Boolean deleteSysRole(Integer id) {
        Integer count = sysAdminRoleMapper.selectCount(new QueryWrapper<SysAdminRole>().eq("role_id", id));
        if (count > 0) {
            throw new BusinessException(ResultCode.NOR_DELETE_ROLE.getCode(), ResultCode.NOR_DELETE_ROLE.getMessage());
        }
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        sysRoleMapper.deleteById(id);
        return true;
    }

    @Override
    public Boolean updateSysRoleStatus(UpdateStatusDto updateStatusDto) {
        SysRole sysRole = sysRoleMapper.selectById(updateStatusDto.getId());
        sysRole.setStatus(updateStatusDto.getStatus());
        sysRoleMapper.updateById(sysRole);
        return true;
    }

    @Override
    public List<QueryRoleVo> queryRoleVoList() {
        return sysRoleMapper.queryRoleVoList();
    }

    @Override
    public List<Integer> queryRoleMenuList(Integer id) {
        return sysRoleMapper.queryRoleMenuList(id);
    }

    @Override
    public Boolean assignPermission(AssPermissionsDto assPermissionsDto) {
        List<SysRoleMenu> list = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", assPermissionsDto.getId()));
        SysRoleMenu addSysRoleMenu = new SysRoleMenu();
        if (list.size() == 0 || CollectionUtils.isEmpty(list)) {
            for (Integer menuId : assPermissionsDto.getMenuIds()) {
                addSysRoleMenu.setRoleId(assPermissionsDto.getId());
                addSysRoleMenu.setMenuId(menuId);
                sysRoleMenuMapper.insert(addSysRoleMenu);
            }
        } else {
            for (SysRoleMenu sysRoleMenu : list) {
                sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", sysRoleMenu.getRoleId()));
            }
            for (Integer menuId : assPermissionsDto.getMenuIds()) {
                addSysRoleMenu.setRoleId(assPermissionsDto.getId());
                addSysRoleMenu.setMenuId(menuId);
                sysRoleMenuMapper.insert(addSysRoleMenu);
            }
        }
        return true;
    }
}
