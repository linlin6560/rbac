package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.dto.AddSysDeptDto;
import com.rbac.project.entity.dto.EditSysDeptDto;
import com.rbac.project.entity.dto.QuerySysDeptDto;
import com.rbac.project.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 部门 控制层
 */
@RestController
@RequestMapping("/sysDept")
@Api(tags = "SysDeptController", description = "部门相关接口")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @GetMapping("/list")
    @ApiOperation("查询部门列表")
    public JsonResult querySysDeptList(QuerySysDeptDto querySysDeptDto) {
        return JsonResult.success(sysDeptService.querySysDeptList(querySysDeptDto));
    }

    @PostMapping("/add")
    @ApiOperation("新增部门")
    @SaCheckPermission("base:dept:add")
    @Log(title = "新增部门", businessType = BusinessType.INSERT)
    public JsonResult addSysDept(@Validated @RequestBody AddSysDeptDto addSysDeptDto) {
        return JsonResult.success(sysDeptService.addSysDept(addSysDeptDto));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据id查询部门")
    public JsonResult getSysDeptById(@PathVariable Integer id) {
        return JsonResult.success(sysDeptService.getById(id));
    }

    @PutMapping("/update")
    @ApiOperation("修改部门")
    @SaCheckPermission("base:dept:edit")
    @Log(title = "修改部门", businessType = BusinessType.UPDATE)
    public JsonResult updateSysDept(@Validated @RequestBody EditSysDeptDto editSysDeptDto) {
        return JsonResult.success(sysDeptService.updateSysDept(editSysDeptDto));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("根据id删除部门")
    @SaCheckPermission("base:dept:delete")
    @Log(title = "根据id删除部门", businessType = BusinessType.DELETE)
    public JsonResult deleteSysDept(@PathVariable Integer id) {
        return JsonResult.success(sysDeptService.deleteSysDept(id));
    }
}
