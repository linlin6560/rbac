import request from '../utils/request'

export default {
    // 验证码接口
    captcha() {
        return request({
            url: "/sysAdmin/captcha",
            method: 'get'
        })
    },
    // 登录接口
    login(params) {
        return request({
            url: '/sysAdmin/login',
            method: 'post',
            data: params
        })
    },
    // 退出登录
    logout() {
        return request({
            url: '/sysAdmin/logout',
            method: 'post'
        })
    },
     // post岗位
    queryPostList(params) {
        return request({
            url: "/sysPost/list",
            method: 'get',
            data: params
        })
    },
    batchDeleteSysPost(id) {
        return request({
            url: '/sysPost/batch/delete/' + id,
            method: 'delete',
        })
    },
    querySysPostVoList() {
        return request({
            url: '/sysPost/vo/list',
            method: 'get'
        })
    },
    addPost(data) {
        return request({
            url: '/sysPost/add',
            method: 'post',
            data: data
        })
    },
    postInfo(id) {
        return request({
            url: '/sysPost/detail/' + id,
            method: 'get'
        })
    },
    updatePost(data) {
        return request({
            url: '/sysPost/update',
            method: 'put',
            data: data
        })
    },
    // dept部门
    queryDeptList(params) {
        return request({
            url: "/sysDept/list",
            method: 'get',
            data: params
        })
    },
    addDept(data) {
        return request({
            url: '/sysDept/add',
            method: 'post',
            data: data
        })
    },
    deleteDept(id) {
    return request({
        url: '/sysDept/delete/' + id,
        method: 'delete',
    })
    },
    deptInfo(id) {
        return request({
            url: '/sysDept/detail/' + id,
            method: 'get'
        })
    },
    deptUpdate(data) {
        return request({
            url: '/sysDept/update',
            method: 'put',
            data: data
        })
    },
    // menu菜单
    queryMenuList(params) {
        return request({
            url: "/sysMenu/list",
            method: 'get',
            data: params
        })
    },
    addMenu(data) {
        return request({
            url: '/sysMenu/add',
            method: 'post',
            data: data
        })
    },
    menuInfo(id) {
        return request({
            url: '/sysMenu/detail/' + id,
            method: 'get'
        })
    },
    menuUpdate(data) {
        return request({
            url: '/sysMenu/update',
            method: 'put',
            data: data
        })
    },
    menuDelete(id) {
        return request({
            url: '/sysMenu/delete/' + id,
            method: 'delete',
        })
    },
    // role角色
    queryRoleList(params) {
        return request({
            url: "/sysRole/list",
            method: 'get',
            data: params
        })
    },
    querySysRoleVoList() {
        return request({
            url: "/sysRole/vo/list",
            method: 'get'
        })
    },
    addRole(data) {
        return request({
            url: '/sysRole/add',
            method: 'post',
            data: data
        })
    },
    roleInfo(id) {
        return request({
            url: '/sysRole/detail/' + id,
            method: 'get'
        })
    },
    roleUpdate(data) {
        return request({
            url: '/sysRole/update',
            method: 'put',
            data: data
        })
    },
    deleteRole(id) {
        return request({
            url: '/sysRole/delete/' + id,
            method: 'delete',
        })
    },
    updateRoleStatus(id, status) {
        const data = {
            id,
            status
        }
        return request({
            url: "/sysRole/status",
            method: 'put',
            data: data
        })
    },
    queryRoleMenuIdList(id) {
        return request({
            url: "/sysRole/menu/list/" + id,
            method: 'get'
        })
    },
    assignPermissions(id, menuIds) {
        const data = {
            id,
            menuIds
        }
        return request({
            url: "/sysRole/menu/assign",
            method: 'put',
            data: data
        })
    },
    // admin用户
    queryAdminList(params) {
        return request({
            url: "/sysAdmin/list",
            method: 'get',
            data: params
        })
    },
    updateAdminStatus(id, status) {
        const data = {
            id,
            status
        }
        return request({
            url: "/sysAdmin/status",
            method: 'put',
            data: data
        })
    },
    addAdmin(data) {
        return request({
            url: '/sysAdmin/add',
            method: 'post',
            data: data
        })
    },
    adminInfo(id) {
        return request({
            url: '/sysAdmin/detail/' + id,
            method: 'get'
        })
    },
    adminUpdate(data) {
        return request({
            url: '/sysAdmin/update',
            method: 'put',
            data: data
        })
    },
    resetPassword(id, password) {
        const data = {
            id,
            password
        }
        return request({
            url: '/sysAdmin/resetPassword',
            method: 'put',
            data: data
        })
    },
    deleteAdmin(id) {
        return request({
            url: '/sysAdmin/delete/' + id,
            method: 'delete'
        })
    },
    adminUpdatePersonal(data) {
        return request({
            url: '/sysAdmin/updateCurrentSysAdmin',
            method: 'post',
            data: data
        })
    },
    adminUpdatePersonalPassword(data) {
        return request({
            url: '/sysAdmin/updateCurrentAdminPassword',
            method: 'post',
            data: data
        })
    },
    // SysOperationLog操作日志
    querySysOperationLogList(params) {
        return request({
            url: "/sysOperatorLog/list",
            method: 'get',
            data: params
        })
    },
    deleteSysOperationLog(id) {
        return request({
            url: '/sysOperatorLog/delete/' + id,
            method: 'delete'
        })
    },
    cleanSysOperationLog() {
        return request({
            url: '/sysOperatorLog/clean',
            method: 'delete'
        })
    },
        // sysLoginInfo登录日志
        querySysLoginInfoList(params) {
            return request({
                url: "/sysLoginInfo/list",
                method: 'get',
                data: params
            })
        },
        deleteSysLoginInfo(id) {
            return request({
                url: '/sysLoginInfo/delete/' + id,
                method: 'delete'
            })
        },
        cleanSysLoginInfo() {
            return request({
                url: '/sysLoginInfo/clean',
                method: 'delete'
            })
        },
}