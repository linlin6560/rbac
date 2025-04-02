<template>
    <el-card>
        <el-form :model="queryParams" :inline="true" ref="queryRef" v-show="showSearch">
            <el-form-item label="用户名称" prop="username">
                <el-input v-model="queryParams.username" placeholder="请输入用户名称" />
            </el-form-item>
            <el-form-item label="操作状态" prop="loginStatus">
                <el-select placeholder="请选择操作状态" v-model="queryParams.loginStatus">
                    <el-option v-for="item in loginInfoStatusList" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item label="开始时间" prop="beginTime">
                <el-date-picker class="input-width" type="date" style="width: 199px;" value-format="YYYY-MM-DD"
                    placeholder="请选择开始时间" v-model="queryParams.beginTime" />
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
                <el-date-picker class="input-width" type="date" style="width: 199px;" value-format="YYYY-MM-DD"
                    placeholder="请选择结束时间" v-model="queryParams.endTime" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button type="primary" icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" @click="handleDelete" :disabled="multiple" v-hasPermission="['log:loginLog:delete']">删除
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" @click="handleClean" v-hasPermission="['log:loginLog:clean']">清空</el-button>
            </el-col>
            <TipHover v-model:showSearch="showSearch" @queryTable="getLoginInfoList" :columns="columns" />
        </el-row>
        <el-table border stripe style="width: 100%;" :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
            v-loading="loading" :data="loginInfoList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="ID" v-if="false" prop="id" />
            <el-table-column label="用户账号" prop="username" v-if="columns[0].visible" />
            <el-table-column label="登录IP地址" prop="ipAddress" v-if="columns[1].visible" />
            <el-table-column label="登录地点" prop="loginLocation" v-if="columns[2].visible" />
            <el-table-column label="浏览器类型" prop="browser" v-if="columns[3].visible" />
            <el-table-column label="登录状态" prop="loginStatus" v-if="columns[4].visible">
                <template #default="scope">
                    <el-tag v-if="scope.row.loginStatus === 1" type="success">成功</el-tag>
                    <el-tag v-else-if="scope.row.loginStatus === 2" type="danger">失败</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="提示消息" prop="message" v-if="columns[5].visible" />
            <el-table-column label="登录时间" prop="loginTime" v-if="columns[6].visible" />
            <el-table-column label="更多操作">
                <template #default="scope">
                    <el-button type="danger" link icon="Delete" @click="handleDelete(scope.row)" v-hasPermission="['log:loginLog:delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <Pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize" @pagination="getLoginInfoList" />
    </el-card>
</template>
  
<script setup>
import { ref, getCurrentInstance } from 'vue'
const { proxy } = getCurrentInstance()
const loginInfoStatusList = ref([
    {
        value: '1',
        label: '成功'
    },
    {
        value: '2',
        label: '失败'
    }
])
const queryParams = ref({})

const loading = ref(true)
const loginInfoList = ref([])
const total = ref(0)
const getLoginInfoList = async () => {
    loading.value = true
    const params = {
        pageSize: queryParams.value.pageSize,
        pageNum: queryParams.value.pageNum,
        username: queryParams.value.username,
        loginStatus: queryParams.value.loginStatus,
        beginTime: queryParams.value.beginTime,
        endTime: queryParams.value.endTime,
    }
    const res = await proxy.$api.querySysLoginInfoList(params)
    if (res.code !== 200) {
        proxy.$message.error(res.message)
    } else {
        loginInfoList.value = res.data.list
        total.value = res.data.total
        loading.value = false
    }
}
getLoginInfoList()
const handleQuery = () => {
    getLoginInfoList()
}
const queryRef = ref()
const resetQuery = () => {
    queryRef.value.resetFields()
    getLoginInfoList()
}

const showSearch = ref(true)
const columns = ref([
    { key: 0, label: `用户账号`, visible: true },
    { key: 1, label: `登录IP地址`, visible: true },
    { key: 2, label: `登录地点`, visible: true },
    { key: 3, label: `浏览器类型`, visible: true },
    { key: 4, label: `操作系统`, visible: true },
    { key: 5, label: `登录状态`, visible: true },
    { key: 6, label: `提示消息`, visible: true },
    { key: 7, label: `登录时间`, visible: true },
])

const ids = ref([])
const single = ref(true);
const multiple = ref(true);
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.id);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
}
const handleDelete = async (row) => {
    const loginInfoIds = row.id || ids.value
    const confirmResult = await proxy.$confirm('是否要删除编号为' + loginInfoIds + '的数据，' + '是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult != 'confirm') {
        proxy.$message.info('已取消')
        return
    }
    const res = await proxy.$api.deleteSysLoginInfo(loginInfoIds)
    if (res.code != 200) {
        proxy.$message.error(res.message)
        return
    } else {
        proxy.$message.success('删除成功')
        await getLoginInfoList()
    }
}

const handleClean = async () => {
    const confirmResult = await proxy.$confirm('是否清空登录日志？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult !== 'confirm') {
        return proxy.$message.info('已取消')
    }
    const res = await proxy.$api.cleanSysLoginInfo()
    if (res.code !== 200) {
        proxy.$message.error(res.message)
    } else {
        proxy.$message.success('清空成功')
        await getLoginInfoList()
    }
}
</script>
  
<style scoped></style>