package com.rbac.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 上传 服务层接口
 */
public interface CommonIoService {

    Map<String, Object> upload(MultipartFile file);
}
