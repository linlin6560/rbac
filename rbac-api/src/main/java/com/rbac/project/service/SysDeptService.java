package com.rbac.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.project.entity.SysDept;
import com.rbac.project.entity.dto.AddSysDeptDto;
import com.rbac.project.entity.dto.EditSysDeptDto;
import com.rbac.project.entity.dto.QuerySysDeptDto;

import java.util.List;

/**
 * 部门 服务层接口
 */
public interface SysDeptService extends IService<SysDept> {

    List<SysDept> querySysDeptList(QuerySysDeptDto querySysDeptDto);

    Boolean addSysDept(AddSysDeptDto addSysDeptDto);

    Boolean updateSysDept(EditSysDeptDto editSysDeptDto);

    Boolean deleteSysDept(Integer id);
}
