package com.rbac.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.project.entity.SysOperatorLog;

/**
 * 操作日志 数据层
 */
public interface SysOperatorLogMapper extends BaseMapper<SysOperatorLog> {
    int cleanOperatorLog();
}
