<template>
    <div class="login_container">
        <div class="login_box">
            <el-form class="login_form" ref="loginFormRef" :rules="rules" :model="loginForm">
                <div class="title">RBAC权限系统</div>
                <el-form-item prop="username">
                    <el-input size="large" placeholder="账号" prefix-icon="User" v-model="loginForm.username" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input size="large" placeholder="密码" prefix-icon="Key" v-model="loginForm.password" />
                </el-form-item>
                <el-form-item prop="image">
                    <el-input size="large" placeholder="验证码" prefix-icon="View" style="width: 200px; float: left;"
                        v-model="loginForm.image" maxlength="4" />
                    <el-image class="captchaImg" style="width: 150px; float: left;" @click="getCaptcha" :src="image" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="large" style="width: 174px; font-size: large;"
                        @click="loginBtn">登录</el-button>
                    <el-button type="info" size="large" style="width: 174px; font-size: large;"
                        @click="resetLoginForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance, nextTick } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
const { proxy } = getCurrentInstance()
const loginFormRef = ref()
const rules = {
    username: [{ requried: true, message: "请输入账号", trigger: "blur" }],
    password: [{ requried: true, message: "请输入密码", trigger: "blur" }],
    image: [{ requried: true, message: "请输入验证码", trigger: "blur" }],
}
const loginForm = ref({})
const store = useStore()
const router = useRouter()


const image = ref()
const getCaptcha = async () => {
    const res = await proxy.$api.captcha()
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        image.value = res.data.image
        loginForm.value.uuid = res.data.uuid
    }
}
getCaptcha()

const resetLoginForm = () => {
    nextTick(() => {
        getCaptcha()
        loginFormRef.value.resetFields()
    })
}

function loginBtn() {
    proxy.$refs.loginFormRef.validate(valid => {
        if (valid) {
            proxy.$api.login(loginForm.value).then(res => {
                if (res.code != 200) {
                    proxy.$message.error(res.message)
                } else {
                    proxy.$message.success("登录成功")
                    store.commit('saveSysAdmin', res.data.sysAdmin)
                    store.commit('saveToken', res.data.token)
                    store.commit('saveLeftMenuList', res.data.list)
                    store.commit('savePermissionList', res.data.valuesList)
                    router.push("/home")
                }
            })
        }
    })
}
</script>

<style lang="scss" scoped>
.login_container {
    background-image: url("../assets/image/login-background.jpg");
    height: 100%;
    background-size: cover;

    .login_box {
        width: 400px;
        height: 330px;
        background-color: #fff;
        border-radius: 1px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);

        .login_form {
            position: absolute;
            bottom: 0;
            width: 100%;
            padding: 0 20px;
            box-sizing: border-box;

            .title {
                font-size: 30px;
                line-height: 1.5;
                text-align: center;
                margin-bottom: 30px;
                font-weight: bold;
            }

            .captchaImg {
                height: 38px;
                width: 100%;
                border: 1px solid #e6e6e6;
                margin-left: 8px;
            }
        }
    }
}
</style>