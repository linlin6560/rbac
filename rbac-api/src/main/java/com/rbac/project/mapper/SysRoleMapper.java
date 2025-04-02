package com.rbac.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.project.entity.SysRole;
import com.rbac.project.entity.vo.QueryRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 数据层
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<QueryRoleVo> queryRoleVoList();

    List<Integer> queryRoleMenuList(@Param("id") Integer id);
}
