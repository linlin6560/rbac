package com.rbac.project.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 验证码对象
 */
@Data
@Accessors(chain = true)
public class CaptchaVo {
    private String uuid;
    private String image;
}
