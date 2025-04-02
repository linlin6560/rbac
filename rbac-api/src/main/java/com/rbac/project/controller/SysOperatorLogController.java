package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonPage;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.SysOperatorLog;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.QueryOperatorLogDto;
import com.rbac.project.service.SysOperatorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 操作日志 控制层
 */
@RestController
@RequestMapping("/sysOperatorLog")
@Api(tags = "SysOperatorLogController", description = "操作日志相关接口")
public class SysOperatorLogController {

    @Resource
    private SysOperatorLogService sysOperatorLogService;

    @GetMapping("/list")
    @ApiOperation("分页查询操作日志列表")
    public JsonResult<JsonPage<SysOperatorLog>> querySysOperatorLogPageList(BaseQueryDto baseQueryDto, QueryOperatorLogDto queryOperatorLogDto) {
        return JsonResult.success(JsonPage.restPage(sysOperatorLogService.querySysOperatorLogPageList(baseQueryDto, queryOperatorLogDto)));
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空操作日志")
    @Log(title = "清空操作日志", businessType = BusinessType.CLEAN)
    @SaCheckPermission("log:operator:clean")
    public JsonResult cleanSysOperatorLog() {
        return JsonResult.success(sysOperatorLogService.cleanOperatorLog());
    }

    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除操作日志")
    @Log(title = "批量删除操作日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("log:operator:delete")
    public JsonResult deleteBatchSysOperatorLog(@PathVariable Integer[] ids) {
        return JsonResult.success(sysOperatorLogService.removeByIds(Arrays.asList(ids)));
    }

}
