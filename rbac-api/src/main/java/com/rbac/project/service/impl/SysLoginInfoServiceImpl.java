package com.rbac.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.project.entity.SysLoginInfo;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.QueryLoginInfoDto;
import com.rbac.project.mapper.SysLoginInfoMapper;
import com.rbac.project.service.SysLoginInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录日志 服务层接口实现类
 */
@Slf4j
@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements SysLoginInfoService {

    @Resource
    private SysLoginInfoMapper sysLoginInfoMapper;

    @Override
    public Page<SysLoginInfo> querySysLoginInfoList(BaseQueryDto baseQueryDto, QueryLoginInfoDto queryLoginInfoDto) {
        return sysLoginInfoMapper.selectPage(new Page<>(baseQueryDto.getPageNum(), baseQueryDto.getPageSize()), new QueryWrapper<SysLoginInfo>()
        .like(StringUtils.isNotEmpty(queryLoginInfoDto.getUsername()), "username", queryLoginInfoDto.getUsername())
        .eq(StringUtils.isNotEmpty(queryLoginInfoDto.getLoginStatus()), "login_status", queryLoginInfoDto.getLoginStatus())
        .ge(StringUtils.isNotEmpty(queryLoginInfoDto.getBeginTime()), "login_time", queryLoginInfoDto.getBeginTime())
        .le(StringUtils.isNotEmpty(queryLoginInfoDto.getEndTime()), "login_time", queryLoginInfoDto.getEndTime())
        .orderByDesc("login_time"));
    }

    @Override
    public int cleanLoginInfo() {
        return sysLoginInfoMapper.cleanLoginInfo();
    }
}
