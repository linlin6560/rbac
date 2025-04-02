package com.rbac.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rbac.project.entity.SysAdmin;
import com.rbac.project.entity.dto.QuerySysAdminDto;
import com.rbac.project.entity.vo.QuerySysAdminVo;
import com.rbac.project.entity.vo.SysAdminVo;
import com.rbac.project.entity.vo.SysMenuOneVo;
import com.rbac.project.entity.vo.SysMenuTwoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 数据层
 */
public interface SysAdminMapper extends BaseMapper<SysAdmin> {

    Page<QuerySysAdminVo> querySysAdminPageList(@Param("page") Page page, @Param("querySysAdminDto") QuerySysAdminDto querySysAdminDto);

    SysAdminVo querySysAdminVoById(@Param("id") Integer id);

    List<String> queryValuesList(@Param("id") Integer id);

    List<SysMenuTwoVo> menuList(@Param("id") Integer id, @Param("menuId") Integer menuId);

    List<SysMenuOneVo> treeList(@Param("id") Integer id);
}
