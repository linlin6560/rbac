package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 编辑岗位参数对象
 */
@Data
public class EditSysPostDto {
    @NotNull(message = "Id不能为空")
    private Integer id;
    @NotEmpty(message = "岗位编码不能为空")
    private String postCode;
    @NotEmpty(message = "岗位名称不能为空")
    private String postName;
    @NotNull(message = "状态不能为空")
    private Integer postStatus;
    @NotEmpty(message = "备注不能为空")
    private String remark;
}
