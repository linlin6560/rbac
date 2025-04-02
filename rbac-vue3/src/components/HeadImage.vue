<template>
    <div>
        <el-dropdown trigger="click">
            <span>
                <img v-if="!sysAdminInfo.icon" src="./../assets/image/logo.jpg" class="admin_avatar" />
                <img v-else :src="sysAdminInfo.icon" class="admin_avatar" />
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item><span @click="goToPersonal">个人信息</span></el-dropdown-item>
                    <el-dropdown-item><span @click="logout">退出登录</span></el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const { proxy } = getCurrentInstance()

const sysAdminInfo = ref()
const getSysAdmin = () => {
    sysAdminInfo.value = proxy.$store.state.sysAdmin
}
getSysAdmin()

const goToPersonal = () => {
    router.push('/personal')
}

const logout = async () => {
    const confirmResult = await proxy.$confirm('确定要退出登录码，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult != 'confirm') {
        proxy.$message.info('已取消')
        return
    }
    const res = await proxy.$api.logout()
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        proxy.$storage.clearAll()
        proxy.$message.success('退出成功')
        router.push('/login')
    }
}

</script>

<style lang="scss" scoped>
.el-dropdown {
    .admin_avatar {
        cursor: pointer;
        width: 40px;
        height: 40px;
        border-radius: 20px;
    }
}
</style>