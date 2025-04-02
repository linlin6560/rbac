package com.rbac.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.common.exception.BusinessException;
import com.rbac.common.result.ResultCode;
import com.rbac.project.entity.SysDept;
import com.rbac.project.entity.dto.AddSysDeptDto;
import com.rbac.project.entity.dto.EditSysDeptDto;
import com.rbac.project.entity.dto.QuerySysDeptDto;
import com.rbac.project.mapper.SysDeptMapper;
import com.rbac.project.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 部门 服务层接口实现类
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> querySysDeptList(QuerySysDeptDto querySysDeptDto) {
        return sysDeptMapper.selectList(new QueryWrapper<SysDept>()
                .like(StringUtils.isNotEmpty(querySysDeptDto.getDeptName()), "dept_name", querySysDeptDto.getDeptName())
                .eq(StringUtils.isNotEmpty(querySysDeptDto.getDeptStatus()), "dept_status", querySysDeptDto.getDeptStatus()));
    }

    @Override
    public Boolean addSysDept(AddSysDeptDto addSysDeptDto) {
        SysDept sysDept = new SysDept();
        SysDept sysDeptByDeptName = sysDeptMapper.selectOne(new QueryWrapper<SysDept>().eq("dept_name", addSysDeptDto.getDeptName()));
        if (!ObjectUtils.isEmpty(sysDeptByDeptName)) {
            throw new BusinessException(ResultCode.DEPT_NAME_IS_EXIST.getCode(), ResultCode.DEPT_NAME_IS_EXIST.getMessage());
        }
        if (addSysDeptDto.getDeptType() == 1) {
            SysDept sysDeptByDeptType = sysDeptMapper.selectOne(new QueryWrapper<SysDept>().eq("dept_type", 0));
            if (sysDeptByDeptType != null) {
                throw new BusinessException(ResultCode.COMPANY_IS_EXIST.getCode(), ResultCode.COMPANY_IS_EXIST.getMessage());
            }
            sysDept.setParentId(0).setDeptName(addSysDeptDto.getDeptName()).setDeptType(1).setDeptStatus(addSysDeptDto.getDeptStatus()).setCreateTime(new Date());
        } else if (addSysDeptDto.getDeptType() == 2) {
            sysDept.setParentId(addSysDeptDto.getParentId()).setDeptName(addSysDeptDto.getDeptName()).setDeptType(2).setDeptStatus(addSysDeptDto.getDeptStatus()).setCreateTime(new Date());
        } else if (addSysDeptDto.getDeptType() == 3) {
            sysDept.setParentId(addSysDeptDto.getParentId()).setDeptName(addSysDeptDto.getDeptName()).setDeptType(3).setDeptStatus(addSysDeptDto.getDeptStatus()).setCreateTime(new Date());
        }
        sysDeptMapper.insert(sysDept);
        return true;
    }

    @Override
    public Boolean updateSysDept(EditSysDeptDto editSysDeptDto) {
        SysDept sysDeptByDeptName = sysDeptMapper.selectOne(new QueryWrapper<SysDept>().eq("dept_name", editSysDeptDto.getDeptName()));
        if (!ObjectUtils.isEmpty(sysDeptByDeptName)) {
            throw new BusinessException(ResultCode.DEPT_NAME_IS_EXIST.getCode(), ResultCode.DEPT_NAME_IS_EXIST.getMessage());
        }
        SysDept sysDept = sysDeptMapper.selectById(editSysDeptDto.getId());
        BeanUtils.copyProperties(editSysDeptDto, sysDept);
        sysDeptMapper.updateById(sysDept);
        return true;
    }

    @Override
    public Boolean deleteSysDept(Integer id) {
        List<SysDept> list = sysDeptMapper.selectList(new QueryWrapper<SysDept>().eq("parent_id", id));
        if (list.size() != 0) {
            throw new BusinessException(ResultCode.DEPT_HAS_CHILD.getCode(), ResultCode.DEPT_HAS_CHILD.getMessage());
        }
        // todo 判断是否有用户分配部门，有就不能删除
        sysDeptMapper.deleteById(id);
        return true;
    }
}
