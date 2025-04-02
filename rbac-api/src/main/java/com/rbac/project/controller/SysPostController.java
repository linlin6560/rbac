package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonPage;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.SysPost;
import com.rbac.project.entity.dto.AddSysPostDto;
import com.rbac.project.entity.dto.BaseQueryDto;
import com.rbac.project.entity.dto.EditSysPostDto;
import com.rbac.project.entity.dto.QuerySysPostDto;
import com.rbac.project.service.SysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 岗位 控制层
 */
@RestController
@RequestMapping("/sysPost")
@Api(tags = "SysPostController", description = "岗位相关接口")
public class SysPostController {

    @Resource
    private SysPostService sysPostService;

    @GetMapping("/list")
    @ApiOperation("分页查询岗位列表")
    public JsonResult<JsonPage<SysPost>> querySysPostList(BaseQueryDto baseQueryDto, QuerySysPostDto querySysPostDto) {
        return JsonResult.success(JsonPage.restPage(sysPostService.querySysPostList(baseQueryDto, querySysPostDto)));
    }

    @PostMapping("/add")
    @ApiOperation("新增岗位")
    @SaCheckPermission("base:post:add")
    @Log(title = "新增岗位", businessType = BusinessType.INSERT)
    public JsonResult addSysPost(@Validated @RequestBody AddSysPostDto addSysPostDto) {
        return JsonResult.success(sysPostService.addSysPost(addSysPostDto));
    }

    @PutMapping("/update")
    @ApiOperation("修改岗位")
    @SaCheckPermission("base:post:edit")
    @Log(title = "修改岗位", businessType = BusinessType.UPDATE)
    public JsonResult updateSysPost(@Validated @RequestBody EditSysPostDto editSysPostDto) {
        return JsonResult.success(sysPostService.updateSysPost(editSysPostDto));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据id查询岗位")
    public JsonResult getSysPostById(@PathVariable Integer id) {
        return JsonResult.success(sysPostService.getById(id));
    }

    @DeleteMapping("/batch/delete/{ids}")
    @ApiOperation("单/批量删除岗位")
    @SaCheckPermission("base:post:delete")
    @Log(title = "批量删除岗位", businessType = BusinessType.DELETE)
    public JsonResult deleteSysPost(@PathVariable Integer[] ids) {
        return JsonResult.success(sysPostService.removeByIds(Arrays.asList(ids)));
    }

    @GetMapping("/vo/list")
    @ApiOperation("岗位下拉列表")
    public JsonResult queryPostVoList() {
        return JsonResult.success(sysPostService.queryPostVoList());
    }
}
