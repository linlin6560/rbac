package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysMenu;
import com.rbac.project.entity.dto.AddSysMenuDto;
import com.rbac.project.entity.dto.EditSysMenuDto;
import com.rbac.project.entity.dto.QuerySysMenuDto;

import java.util.List;

/**
 * 菜单 服务层接口
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> querySysMenuList(QuerySysMenuDto querySysMenuDto);

    Boolean addSysMenu(AddSysMenuDto addSysMenuDto);

    Boolean updateSysMenu(EditSysMenuDto editSysMenuDto);

    Boolean deleteSysMenu(Integer id);
}
