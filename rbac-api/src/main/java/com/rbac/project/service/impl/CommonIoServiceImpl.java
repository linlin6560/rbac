package com.rbac.project.service.impl;

import com.rbac.common.util.FileTool;
import com.rbac.project.service.CommonIoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传 服务层接口实现类
 */
@Slf4j
@Service
public class CommonIoServiceImpl implements CommonIoService {

    @Value("${cbs.uploadIp}")
    private String uploadIp;
    @Value("${cbs.imagesPath}")
    private String imagesPath;
    @Value("${server.servlet.context-path}")
    private String api;

    @Override
    public Map<String, Object> upload(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            String filename = file.getOriginalFilename();
            filename = FileTool.renameToUUID(filename);
            try {
                FileTool.uploadFiles(file.getBytes(), imagesPath, filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = uploadIp + api + "/static/" + filename;
            map.put("url", url);
        } catch (Exception e) {
            log.error("上传异常：{}", e.getMessage());
        }
        return map;
    }
}
