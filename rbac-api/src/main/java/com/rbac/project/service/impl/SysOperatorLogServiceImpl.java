package com.rbac.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.project.entity.SysOperatorLog;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.QueryOperatorLogDto;
import com.rbac.project.mapper.SysOperatorLogMapper;
import com.rbac.project.service.SysOperatorLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 操作日志 服务层接口实现
 */
@Slf4j
@Service
public class SysOperatorLogServiceImpl extends ServiceImpl<SysOperatorLogMapper, SysOperatorLog> implements SysOperatorLogService {

    @Resource
    private SysOperatorLogMapper sysOperatorLogMapper;

    @Override
    public Page<SysOperatorLog> querySysOperatorLogPageList(BaseQueryDto baseQueryDto, QueryOperatorLogDto queryOperatorLogDto) {
        return sysOperatorLogMapper.selectPage(new Page<>(baseQueryDto.getPageNum(), baseQueryDto.getPageSize()), new QueryWrapper<SysOperatorLog>()
                        .like(StringUtils.isNotEmpty(queryOperatorLogDto.getOperatorName()), "operator_name", queryOperatorLogDto.getOperatorName())
                        .eq(StringUtils.isNotEmpty(queryOperatorLogDto.getOperatorStatus()), "operator_status", queryOperatorLogDto.getOperatorStatus())
                        .ge(StringUtils.isNotEmpty(queryOperatorLogDto.getBeginTime()), "operator_time", queryOperatorLogDto.getBeginTime())
                        .le(StringUtils.isNotEmpty(queryOperatorLogDto.getEndTime()), "operator_time", queryOperatorLogDto.getEndTime())
                        .orderByDesc("operator_time"));
    }

    @Override
    public int cleanOperatorLog() {
        return sysOperatorLogMapper.cleanOperatorLog();
    }

}
