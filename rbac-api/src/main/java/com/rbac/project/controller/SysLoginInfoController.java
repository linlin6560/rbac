package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonPage;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.SysLoginInfo;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.QueryLoginInfoDto;
import com.rbac.project.service.SysLoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 登录日志 控制层
 */
@RestController
@RequestMapping("/sysLoginInfo")
@Api(tags = "SysLoginInfoController", description = "登录日志相关接口")
public class SysLoginInfoController {

    @Resource
    private SysLoginInfoService sysLoginInfoService;

    @GetMapping("/list")
    @ApiOperation("分页查询登录日志")
    public JsonResult<JsonPage<SysLoginInfo>> querySysLoginInfoList(BaseQueryDto baseQueryDto, QueryLoginInfoDto queryLoginInfoDto) {
        return JsonResult.success(JsonPage.restPage(sysLoginInfoService.querySysLoginInfoList(baseQueryDto, queryLoginInfoDto)));
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空登录日志")
    @SaCheckPermission("log:loginLog:clean")
    @Log(title = "清空登录日志", businessType = BusinessType.CLEAN)
    public JsonResult cleanSysLoginInfo() {
        return JsonResult.success(sysLoginInfoService.cleanLoginInfo());
    }

    @DeleteMapping("/delete/{ids}")
    @ApiOperation("单/批量删除登录日志")
    @SaCheckPermission("log:loginLog:delete")
    @Log(title = "单删/批量删除登录日志", businessType = BusinessType.DELETE)
    public JsonResult deleteSysLoginInfo(@PathVariable Integer[] ids) {
        return JsonResult.success(sysLoginInfoService.removeByIds(Arrays.asList(ids)));
    }

}
