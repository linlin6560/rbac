<template>
    <el-container class="home_container">
        <el-aside :width="isCollapse ? '64px' : '200px'">
            <div class="logo">
                <img src="./../assets/image/logo.jpg" class="siderbar_logo" />
                <h4 v-show="!isCollapse">RBAC权限系统</h4>
            </div>
            <el-menu background-color="#304156" text-color="#c1c1c1" unique-opened router :default-active="defaultActive"
                :collapse="isCollapse" :collapse-transition="false">
                <el-menu-item :index="'/welcome'" @click="saveNavState('/welcome')">
                    <el-icon>
                        <component is="HomeFilled"></component>
                    </el-icon>
                    <template #title>
                        <span>首页</span>
                    </template>
                </el-menu-item>
                <el-sub-menu :index="item.id + ''" v-for="item in leftMenuList" :key="item.id">
                    <template #title>
                        <el-icon>
                            <component :is="item.icon"></component>
                        </el-icon>
                        <span>{{ item.menuName }}</span>
                    </template>
                    <el-menu-item :index="'/' + subItem.url" v-for="subItem in item.children" :key="subItem.id"
                        @click="saveNavState('/' + subItem.url)">
                        <template #title>
                            <el-icon>
                                <component :is="subItem.icon"></component>
                            </el-icon>
                            <span>{{ subItem.menuName }}</span>
                        </template>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header>
                <div class="fold_btn">
                    <el-icon @click="toggleCollapse">
                        <component :is="collapseBtnClass"></component>
                    </el-icon>
                </div>
                <div class="bread_btn">
                    <el-breadcrumb separator="/" v-if="router.currentRoute.value.path != '/welcome'">
                        <el-breadcrumb-item :to="{path: '/welcome'}">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{ route.meta.sTitle }}</el-breadcrumb-item>
                        <el-breadcrumb-item>{{ route.meta.tTitle }}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <el-breadcrumb separator="/" v-else>
                        <el-breadcrumb-item :to="{path: '/welcome'}">首页</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <HeadImage/>
            </el-header>
            <Tags/>
            <el-main>
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router'
import HeadImage from '@/components/HeadImage.vue'
import Tags from '@/components/Tags.vue'
const store = useStore()
const router = useRouter()
const route = useRoute()
const { proxy } = getCurrentInstance()

// console.log(proxy.$store.state.leftMenuList)
const leftMenuList = ref({})
const getLeftMenuList = async () => {
    leftMenuList.value = proxy.$store.state.leftMenuList
}
getLeftMenuList()

const activePath = ref()
const defaultActive = ref(router.currentRoute.value.path)
const saveNavState = (activePath) => {
    store.commit('saveActivePath', activePath)
}

const collapseBtnClass = ref("Expand")
const isCollapse = ref(false)
const toggleCollapse = () => {
    isCollapse.value = !isCollapse.value
    if (isCollapse.value) {
        collapseBtnClass.value = 'Fold'
    } else {
        collapseBtnClass.value = 'Expand'
    }
}

onBeforeRouteUpdate((to, from)=>{
    defaultActive.value = to.path
    store.commit('saveActivePath', to.path)
})
</script>

<style lang="scss" scoped>
.home_container {
    height: 100%;

    .el-aside {
        background-color: #304156;

        .logo {
            margin-top: 5px;
            display: flex;
            align-items: center;
            font-size: 13px;
            height: 50px;
            color: #fff;

            .siderbar_logo {
                width: 32px;
                height: 32px;
                margin: 0 16px;
                border-radius: 5px;
            }
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #f9fafc;
        align-items: center;
        justify-content: space-between;
        display: flex;

        .fold_btn {
            padding-top: 6px;
            font-size: 23px;
            cursor: pointer;
        }
        .bread_btn{
            position: fixed;
            margin-left: 40px;
        }
    }

    .el-main {
        background-color: #eaedf1;
    }
}
</style>