package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysOperatorLog;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.QueryOperatorLogDto;

/**
 * 操作日志 服务层接口
 */
public interface SysOperatorLogService extends IService<SysOperatorLog> {

    Page<SysOperatorLog> querySysOperatorLogPageList(BaseQueryDto baseQueryDto, QueryOperatorLogDto queryOperatorLogDto);

    int cleanOperatorLog();

}
