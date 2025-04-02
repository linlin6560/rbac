package com.rbac.project.controller;

import com.rbac.common.result.JsonResult;
import com.rbac.project.service.CommonIoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 上传 控制层
 */
@RestController
@RequestMapping("/common")
@Api(tags = "CommonIoController", description = "上传相关接口")
public class CommonIoController {

    @Resource
    private CommonIoService commonIoService;

    @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("图片上传")
    public JsonResult upload(@RequestPart(value = "file") MultipartFile file) {
        return JsonResult.success(commonIoService.upload(file));
    }
}
