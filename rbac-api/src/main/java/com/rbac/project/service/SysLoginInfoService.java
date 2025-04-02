package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysLoginInfo;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.QueryLoginInfoDto;

/**
 * 登录日志 服务层接口
 */
public interface SysLoginInfoService extends IService<SysLoginInfo> {

    Page<SysLoginInfo> querySysLoginInfoList(BaseQueryDto baseQueryDto, QueryLoginInfoDto queryLoginInfoDto);

    int cleanLoginInfo();

}
