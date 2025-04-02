package com.rbac.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.common.exception.BusinessException;
import com.rbac.common.result.ResultCode;
import com.rbac.project.entity.SysPost;
import com.rbac.project.entity.vo.QueryPostVo;
import com.rbac.project.entity.dto.AddSysPostDto;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.EditSysPostDto;
import com.rbac.project.entity.dto.QuerySysPostDto;
import com.rbac.project.mapper.SysPostMapper;
import com.rbac.project.service.SysPostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 岗位 服务层接口实现
 */
@Slf4j
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    @Resource
    private SysPostMapper sysPostMapper;

    @Override
    public Page<SysPost> querySysPostList(BaseQueryDto baseQueryDto, QuerySysPostDto querySysPostDto) {
        return sysPostMapper.selectPage(new Page<>(baseQueryDto.getPageNum(), baseQueryDto.getPageSize()), new QueryWrapper<SysPost>()
                .like(StringUtils.isNoneEmpty(querySysPostDto.getPostName()), "post_name", querySysPostDto.getPostName())
                .eq(StringUtils.isNotEmpty(querySysPostDto.getPostStatus()), "post_status", querySysPostDto.getPostStatus())
                .ge(StringUtils.isNoneEmpty(querySysPostDto.getBeginTime()), "create_time", querySysPostDto.getBeginTime())
                .le(StringUtils.isNoneEmpty(querySysPostDto.getEndTime()), "create_time", querySysPostDto.getEndTime())
                .orderByDesc("create_time"));
    }

    @Override
    public Boolean addSysPost(AddSysPostDto addSysPostDto) {
        SysPost sysPostByName = sysPostMapper.selectOne(new QueryWrapper<SysPost>().eq("post_name", addSysPostDto.getPostName()));
        if (!ObjectUtils.isEmpty(sysPostByName)) {
            throw new BusinessException(ResultCode.POST_NAME_IS_EXIST.getCode(), ResultCode.POST_NAME_IS_EXIST.getMessage());
        }
        SysPost sysPostByCode = sysPostMapper.selectOne(new QueryWrapper<SysPost>().eq("post_code", addSysPostDto.getPostCode()));
        if (!ObjectUtils.isEmpty(sysPostByCode)) {
            throw new BusinessException(ResultCode.POST_CODE_IS_EXIST.getCode(), ResultCode.POST_CODE_IS_EXIST.getMessage());
        }
        SysPost sysPost = new SysPost()
                .setPostName(addSysPostDto.getPostName())
                .setPostCode(addSysPostDto.getPostCode())
                .setPostStatus(addSysPostDto.getPostStatus())
                .setRemark(addSysPostDto.getRemark())
                .setCreateTime(new Date());
        sysPostMapper.insert(sysPost);
        return true;
    }

    @Override
    public Boolean updateSysPost(EditSysPostDto editSysPostDto) {
        SysPost existSysPost = sysPostMapper.selectById(editSysPostDto.getId());
        SysPost sysPostByName = sysPostMapper.selectOne(new QueryWrapper<SysPost>().eq("post_name", editSysPostDto.getPostName()));
        if (!ObjectUtils.isEmpty(sysPostByName) && sysPostByName.getId() != editSysPostDto.getId()) {
            throw new BusinessException(ResultCode.POST_NAME_IS_EXIST.getCode(), ResultCode.POST_NAME_IS_EXIST.getMessage());
        }
        SysPost sysPostByCode = sysPostMapper.selectOne(new QueryWrapper<SysPost>().eq("post_name", editSysPostDto.getPostCode()));
        if (!ObjectUtils.isEmpty(sysPostByCode) && sysPostByName.getId() != editSysPostDto.getId()) {
            throw new BusinessException(ResultCode.POST_CODE_IS_EXIST.getCode(), ResultCode.POST_CODE_IS_EXIST.getMessage());
        }
        BeanUtils.copyProperties(editSysPostDto, existSysPost);
        sysPostMapper.updateById(existSysPost);
        return true;
    }

    @Override
    public List<QueryPostVo> queryPostVoList() {
        return sysPostMapper.queryPostVoList();
    }
}
