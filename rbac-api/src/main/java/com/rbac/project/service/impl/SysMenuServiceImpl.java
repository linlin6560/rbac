package com.rbac.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.common.exception.BusinessException;
import com.rbac.common.result.ResultCode;
import com.rbac.project.entity.SysMenu;
import com.rbac.project.entity.SysRoleMenu;
import com.rbac.project.entity.dto.AddSysMenuDto;
import com.rbac.project.entity.dto.EditSysMenuDto;
import com.rbac.project.entity.dto.QuerySysMenuDto;
import com.rbac.project.mapper.SysMenuMapper;
import com.rbac.project.mapper.SysRoleMenuMapper;
import com.rbac.project.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 菜单 服务层接口实现
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> querySysMenuList(QuerySysMenuDto querySysMenuDto) {
        return sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                .like(StringUtils.isNotEmpty(querySysMenuDto.getMenuName()), "menu_name", querySysMenuDto.getMenuName())
                .eq(StringUtils.isNotEmpty(querySysMenuDto.getMenuStatus()), "menu_status", querySysMenuDto.getMenuStatus())
                .orderByAsc("sort"));
    }

    @Override
    public Boolean addSysMenu(AddSysMenuDto addSysMenuDto) {
        SysMenu existSysMenu = sysMenuMapper.selectOne(new QueryWrapper<SysMenu>().eq("menu_name", addSysMenuDto.getMenuName()));
        if (!ObjectUtils.isEmpty(existSysMenu)) {
            throw new BusinessException(ResultCode.MENU_NAME_IS_EXIST.getCode(), ResultCode.MENU_NAME_IS_EXIST.getMessage());
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(addSysMenuDto, sysMenu);
        sysMenu.setCreateTime(new Date());
        sysMenuMapper.insert(sysMenu);
        return true;
    }

    @Override
    public Boolean updateSysMenu(EditSysMenuDto editSysMenuDto) {
        SysMenu existSysMenu = sysMenuMapper.selectById(editSysMenuDto.getId());
        SysMenu sysMenuByMenuName = sysMenuMapper.selectOne(new QueryWrapper<SysMenu>().eq("menu_name", editSysMenuDto.getMenuName()));
        if (null != sysMenuByMenuName && sysMenuByMenuName.getId() != editSysMenuDto.getId()) {
            throw new BusinessException(ResultCode.MENU_NAME_IS_EXIST.getCode(), ResultCode.MENU_NAME_IS_EXIST.getMessage());
        }
        BeanUtils.copyProperties(editSysMenuDto, existSysMenu);
        sysMenuMapper.updateById(existSysMenu);
        return true;
    }

    @Override
    public Boolean deleteSysMenu(Integer id) {
        List<SysMenu> list = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (list.size() != 0) {
            throw new BusinessException(ResultCode.MENU_HAS_CHILD.getCode(), ResultCode.MENU_HAS_CHILD.getMessage());
        }
        Integer count = sysRoleMenuMapper.selectCount(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        if (count > 0) {
            throw new BusinessException(ResultCode.NOR_DELETE_MENU.getCode(), ResultCode.NOR_DELETE_MENU.getMessage());
        }
        sysMenuMapper.deleteById(id);
        return true;
    }
}
