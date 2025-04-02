package com.rbac.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.rbac.common.aspectj.BusinessType;
import com.rbac.common.aspectj.Log;
import com.rbac.common.result.JsonPage;
import com.rbac.common.result.JsonResult;
import com.rbac.project.entity.dto.*;
import com.rbac.project.entity.vo.QuerySysAdminVo;
import com.rbac.project.service.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户 控制层
 */
@RestController
@RequestMapping("/sysAdmin")
@Api(tags = "SysAdminController", description = "用户相关接口")
public class SysAdminController {

    @Resource
    private SysAdminService sysAdminService;

    @GetMapping("/list")
    @ApiOperation("分页查询用户列表")
    public JsonResult<JsonPage<QuerySysAdminVo>> querySysAdminPageList(BaseQueryDto baseQueryDto, QuerySysAdminDto querySysAdminDto) {
        return JsonResult.success(JsonPage.restPage(sysAdminService.querySysAdminPageList(baseQueryDto, querySysAdminDto)));
    }

    @PostMapping("/add")
    @ApiOperation("新增用户")
    @SaCheckPermission("base:admin:add")
    @Log(title = "新增用户", businessType = BusinessType.INSERT)
    public JsonResult addSysAdmin(@Validated @RequestBody AddSysAdminDto addSysAdminDto) {
        return JsonResult.success(sysAdminService.addSysAdmin(addSysAdminDto));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据id查询用户")
    public JsonResult querySysAdminVoById(@PathVariable Integer id) {
        return JsonResult.success(sysAdminService.querySysAdminVoById(id));
    }

    @PutMapping("/update")
    @ApiOperation("修改用户")
    @SaCheckPermission("base:admin:edit")
    @Log(title = "修改用户", businessType = BusinessType.UPDATE)
    public JsonResult updateSysAdmin(@Validated @RequestBody EditSysAdminDto editSysAdminDto) {
        return JsonResult.success(sysAdminService.updateSysAdmin(editSysAdminDto));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("根据id删除")
    @SaCheckPermission("base:admin:delete")
    @Log(title = "删除用户", businessType = BusinessType.DELETE)
    public JsonResult deleteSysAdmin(@PathVariable Integer id) {
        return JsonResult.success(sysAdminService.deleteSysAdmin(id));
    }

    @PutMapping("/status")
    @ApiOperation("修改状态")
    @Log(title = "修改用户状态", businessType = BusinessType.UPDATE)
    public JsonResult updateSysAdminStatus(@Validated @RequestBody UpdateStatusDto updateStatusDto) {
        return JsonResult.success(sysAdminService.updateSysAdminStatus(updateStatusDto));
    }

    @PutMapping("/resetPassword")
    @ApiOperation("重置密码")
    @SaCheckPermission("base:admin:reset")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    public JsonResult resetPassword(@Validated @RequestBody ResetPasswordDto resetPasswordDto) {
        return JsonResult.success(sysAdminService.resetPassword(resetPasswordDto));
    }

    @PostMapping("/updateCurrentSysAdmin")
    @ApiOperation("修改当前登录的用户信息")
    public JsonResult updateCurrentSysAdmin(@Validated @RequestBody SysAdminEditDto sysAdminEditDto) {
        return JsonResult.success(sysAdminService.updateCurrentSysAdmin(sysAdminEditDto));
    }

    @PostMapping("/updateCurrentAdminPassword")
    @ApiOperation("修改当前登录用户密码")
    public JsonResult updateCurrentAdminPassword(@Validated @RequestBody AdminResetPwdDto adminResetPwdDto) {
        return JsonResult.success(sysAdminService.updateCurrentAdminPassword(adminResetPwdDto));
    }

    @GetMapping("/captcha")
    @ApiOperation("生成验证码")
    public JsonResult captcha(HttpServletResponse response) throws IOException {
        return JsonResult.success(sysAdminService.captcha(response));
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public JsonResult login(@Validated @RequestBody SysAdminLoginDto sysAdminLoginDto) {
        return JsonResult.success(sysAdminService.login(sysAdminLoginDto));
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public JsonResult logout() {
        return JsonResult.success(sysAdminService.logout());
    }
}
