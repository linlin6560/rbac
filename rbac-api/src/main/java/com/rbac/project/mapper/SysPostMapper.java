package com.rbac.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.project.entity.SysPost;
import com.rbac.project.entity.vo.QueryPostVo;

import java.util.List;

/**
 *  岗位 数据层
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    List<QueryPostVo> queryPostVoList();
}
