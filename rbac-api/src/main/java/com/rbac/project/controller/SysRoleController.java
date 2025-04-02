package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonPage;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.SysRole;
import com.rbac.project.entity.dto.*;
import com.rbac.project.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色 控制层
 */
@RestController
@RequestMapping("/sysRole")
@Api(tags = "SysRoleController", description = "角色相关接口")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/list")
    @ApiOperation("分页查询角色列表")
    public JsonResult<JsonPage<SysRole>> querySysRoleList(BaseQueryDto baseQueryDto, QueryRoleDto queryRoleDto) {
        return JsonResult.success(JsonPage.restPage(sysRoleService.querySysRoleList(baseQueryDto, queryRoleDto)));
    }

    @PostMapping("/add")
    @ApiOperation("新增角色")
    @SaCheckPermission("base:role:add")
    @Log(title = "新增角色", businessType = BusinessType.INSERT)
    public JsonResult addSysRole(@Validated @RequestBody AddSysRoleDto addSysRoleDto) {
        return JsonResult.success(sysRoleService.addSysRole(addSysRoleDto));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据角色id查询角色")
    public JsonResult getSysRoleById(@PathVariable Integer id) {
        return JsonResult.success(sysRoleService.getById(id));
    }

    @PutMapping("/update")
    @ApiOperation("修改角色")
    @SaCheckPermission("base:role:edit")
    @Log(title = "修改角色", businessType = BusinessType.UPDATE)
    public JsonResult updateSysRole(@Validated @RequestBody EditSysRoleDto editSysRoleDto) {
        return JsonResult.success(sysRoleService.updateSysRole(editSysRoleDto));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("根据id删除角色")
    @SaCheckPermission("base:role:delete")
    @Log(title = "删除角色", businessType = BusinessType.DELETE)
    public JsonResult deleteSysRole(@PathVariable Integer id) {
        return JsonResult.success(sysRoleService.deleteSysRole(id));
    }

    @PutMapping("/status")
    @ApiOperation("修改角色状态")
    @Log(title = "修改角色状态", businessType = BusinessType.UPDATE)
    public JsonResult updateSysRoleStatus(@Validated @RequestBody UpdateStatusDto updateStatusDto) {
        return JsonResult.success(sysRoleService.updateSysRoleStatus(updateStatusDto));
    }

    @GetMapping("/vo/list")
    @ApiOperation("角色下拉列表")
    public JsonResult queryRoleVoList() {
        return JsonResult.success(sysRoleService.queryRoleVoList());
    }

    @GetMapping("/menu/list/{id}")
    @ApiOperation("根据角色的id查询菜单列表")
    public JsonResult queryRoleMenuList(@PathVariable Integer id) {
        return JsonResult.success(sysRoleService.queryRoleMenuList(id));
    }

    @PutMapping("/menu/assign")
    @ApiOperation("分配权限")
    @SaCheckPermission("base:role:assign")
    @Log(title = "分配权限", businessType = BusinessType.GRANT)
    public JsonResult assignPermission(@Validated @RequestBody AssPermissionsDto assPermissionsDto) {
        return JsonResult.success(sysRoleService.assignPermission(assPermissionsDto));
    }

}
