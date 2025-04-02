package com.rbac.project.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.rbac.project.mapper.SysAdminMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SysAdminMapper sysAdminMapper;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        int adminId = Integer.parseInt(o.toString());
        List<String> list = sysAdminMapper.queryValuesList(adminId);
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
