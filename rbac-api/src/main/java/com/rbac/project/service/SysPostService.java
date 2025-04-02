package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysPost;
import com.rbac.project.entity.vo.QueryPostVo;
import com.rbac.project.entity.dto.AddSysPostDto;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.EditSysPostDto;
import com.rbac.project.entity.dto.QuerySysPostDto;

import java.util.List;

/**
 * 岗位 服务层接口
 */
public interface SysPostService extends IService<SysPost> {

    Page<SysPost> querySysPostList(BaseQueryDto baseQueryDto, QuerySysPostDto querySysPostDto);

    Boolean addSysPost(AddSysPostDto addSysPostDto);

    Boolean updateSysPost(EditSysPostDto editSysPostDto);

    List<QueryPostVo> queryPostVoList();
}
