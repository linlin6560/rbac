package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.dto.AddSysMenuDto;
import com.rbac.project.entity.dto.EditSysMenuDto;
import com.rbac.project.entity.dto.QuerySysMenuDto;
import com.rbac.project.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单 控制层
 */
@RestController
@RequestMapping("/sysMenu")
@Api(tags = "SysMenuController", description = "菜单相关接口")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    @ApiOperation("查询菜单列表")
    public JsonResult querySysMenuList(QuerySysMenuDto querySysMenuDto) {
        return JsonResult.success(sysMenuService.querySysMenuList(querySysMenuDto));
    }

    @PostMapping("/add")
    @ApiOperation("新增菜单")
    @SaCheckPermission("base:menu:add")
    @Log(title = "新增菜单", businessType = BusinessType.INSERT)
    public JsonResult addSysMenu(@Validated @RequestBody AddSysMenuDto addSysMenuDto) {
        return JsonResult.success(sysMenuService.addSysMenu(addSysMenuDto));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据id查询菜单")
    public JsonResult getSysMenuById(@PathVariable Integer id) {
        return JsonResult.success(sysMenuService.getById(id));
    }

    @PutMapping("/update")
    @ApiOperation("修改菜单")
    @SaCheckPermission("base:menu:edit")
    @Log(title = "修改菜单", businessType = BusinessType.UPDATE)
    public JsonResult updateSysMenu(@Validated @RequestBody EditSysMenuDto editSysMenuDto) {
        return JsonResult.success(sysMenuService.updateSysMenu(editSysMenuDto));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("根据id删除菜单")
    @SaCheckPermission("base:menu:delete")
    @Log(title = "删除菜单", businessType = BusinessType.DELETE)
    public JsonResult deleteSysMenu(@PathVariable Integer id) {
        return JsonResult.success(sysMenuService.deleteSysMenu(id));
    }
}
