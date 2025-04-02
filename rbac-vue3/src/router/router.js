import {createRouter, createWebHistory} from 'vue-router'
import storage from '../utils/storage'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', redirect: '/home'},
        {path: '/login', component: ()=> import('@/views/Login.vue')},
        {
            path: '/home',
            component: ()=> import('@/views/Home.vue'),
            redirect: '/welcome',
            children: [
                {
                    path: '/welcome',
                    component: ()=> import('@/views/Welcome.vue'),
                    meta: {tTitle: '首页'}
                },
                {
                    path: '/personal',
                    component: () => import('@/views/base/Personal.vue'),
                    meta: {sTitle: '个人中心', tTitle: '个人信息'}
                },
                {
                    path: '/base/Admin',
                    component: () => import('@/views/base/Admin.vue'),
                    meta: {sTitle: '系统管理', tTitle: '用户信息'}
                },
                {
                    path: '/base/Role',
                    component: () => import('@/views/base/Role.vue'),
                    meta: {sTitle: '系统管理', tTitle: '角色信息'}
                },
                {
                    path: '/base/Menu',
                    component: () => import('@/views/base/Menu.vue'),
                    meta: {sTitle: '系统管理', tTitle: '菜单信息'}
                },
                {
                    path: '/base/Dept',
                    component: () => import('@/views/base/Dept.vue'),
                    meta: {sTitle: '系统管理', tTitle: '部门信息'}
                },
                {
                    path: '/base/Post',
                    component: () => import('@/views/base/Post.vue'),
                    meta: {sTitle: '系统管理', tTitle: '岗位信息'}
                },
                {
                    path: '/log/Operator',
                    component: () => import('@/views/log/Operator.vue'),
                    meta: {sTitle: '系统管理', tTitle: '操作日志'}
                },
                {
                    path: '/log/LoginLog',
                    component: () => import('@/views/log/LoginLog.vue'),
                    meta: {sTitle: '系统管理', tTitle: '登录日志'}
                }
            ]
        }
    ]
})

router.beforeEach((to, form, next) => {
    if (to.path == '/login') {
        return next()
    }
    const tokenStr = storage.getItem("token")
    if (!tokenStr) {
        return next('/login')
    }
    next()
})

export default router