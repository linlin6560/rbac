package com.rbac.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.project.entity.SysLoginInfo;

/**
 * 登录日志 数据层
 */
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfo> {

    int cleanLoginInfo();

}
