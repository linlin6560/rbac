package com.rbac.project.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改状态参数对象
 */
@Data
public class UpdateStatusDto {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotNull(message = "状态不能为空")
    private Integer status;
}
