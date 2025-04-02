<template>
    <el-card>
        <el-form :model="queryParams" :inline="true" ref="queryRef" v-show="showSearch">
            <el-form-item label="用户名称" prop="operatorName">
                <el-input v-model="queryParams.operatorName" placeholder="请输入用户名称" />
            </el-form-item>
            <el-form-item label="操作状态" prop="operatorStatus">
                <el-select placeholder="请选择操作状态" v-model="queryParams.operatorStatus">
                    <el-option v-for="item in operatorStatusList" :key="item.value" :label="item.label"
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
                <el-button type="danger" plain icon="Delete" @click="handleDelete" :disabled="multiple" v-hasPermission="['log:operator:delete']">删除
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" @click="handleClean" v-hasPermission="['log:operator:clean']">清空</el-button>
            </el-col>
            <TipHover v-model:showSearch="showSearch" @queryTable="getOperatorList" :columns="columns" />
        </el-row>
        <el-table border stripe style="width: 100%;" :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
            v-loading="loading" :data="operatorList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="ID" v-if="false" prop="id" />
            <el-table-column label="模块标题" prop="title" v-if="columns[0].visible" />
            <el-table-column label="业务类型" prop="businessType" v-if="columns[1].visible">
                <template #default="scope">
                    <el-tag v-if="scope.row.businessType === 1">增加</el-tag>
                    <el-tag v-else-if="scope.row.businessType === 2">修改</el-tag>
                    <el-tag v-else-if="scope.row.businessType === 3">删除</el-tag>
                    <el-tag v-else-if="scope.row.businessType === 4">授权</el-tag>
                    <el-tag v-else-if="scope.row.businessType === 9">清空数据</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="方法名称" prop="method" v-if="columns[2].visible" :show-overflow-tooltip="true" />
            <el-table-column label="请求方式" prop="requestMethod" v-if="columns[3].visible" />
            <el-table-column label="操作人员" prop="operatorName" v-if="columns[4].visible" />
            <el-table-column label="请求URL" prop="operatorUrl" v-if="columns[5].visible" :show-overflow-tooltip="true" />
            <el-table-column label="主机地址" prop="operatorIp" v-if="columns[6].visible" />
            <el-table-column label="操作地点" prop="operatorLocation" v-if="columns[7].visible" />
            <el-table-column label="请求参数" prop="operatorParam" v-if="columns[8].visible" :show-overflow-tooltip="true" />
            <el-table-column label="返回参数" prop="jsonResult" v-if="columns[9].visible" :show-overflow-tooltip="true" />
            <el-table-column label="操作状态" prop="operatorStatus" v-if="columns[10].visible">
                <template #default="scope">
                    <el-tag v-if="scope.row.operatorStatus === 0" type="success">成功</el-tag>
                    <el-tag v-else-if="scope.row.operatorStatus === 1" type="danger">失败</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="错误消息" prop="errorMsg" v-if="columns[11].visible" :show-overflow-tooltip="true" />
            <el-table-column label="操作时间" prop="operatorTime" v-if="columns[12].visible" />
            <el-table-column label="更多操作">
                <template #default="scope">
                    <el-button type="danger" link icon="Delete" @click="handleDelete(scope.row)" v-hasPermission="['log:operator:delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <Pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize" @pagination="getOperatorList" />
    </el-card>
</template>
  
<script setup>
import { ref, getCurrentInstance } from 'vue'
const { proxy } = getCurrentInstance()
const operatorStatusList = ref([
    {
        value: '0',
        label: '正常'
    },
    {
        value: '1',
        label: '异常'
    }
])
const queryParams = ref({})

const loading = ref(true)
const operatorList = ref([])
const total = ref(0)
const getOperatorList = async () => {
    loading.value = true
    const params = {
        pageSize: queryParams.value.pageSize,
        pageNum: queryParams.value.pageNum,
        operatorName: queryParams.value.operatorName,
        operatorStatus: queryParams.value.operatorStatus,
        beginTime: queryParams.value.beginTime,
        endTime: queryParams.value.endTime,
    }
    const res = await proxy.$api.querySysOperationLogList(params)
    if (res.code !== 200) {
        proxy.$message.error(res.message)
    } else {
        operatorList.value = res.data.list
        total.value = res.data.total
        loading.value = false
    }
}
getOperatorList()

const handleQuery = () => {
    getOperatorList()
}
const queryRef = ref()
const resetQuery = () => {
    queryRef.value.resetFields()
    getOperatorList()
}

const showSearch = ref(true)
const columns = ref([
    { key: 0, label: `模块标题`, visible: true },
    { key: 1, label: `业务类型`, visible: true },
    { key: 2, label: `方法名称`, visible: true },
    { key: 3, label: `请求方式`, visible: true },
    { key: 4, label: `操作人员`, visible: true },
    { key: 5, label: `请求url`, visible: true },
    { key: 6, label: `主机地址`, visible: true },
    { key: 7, label: `操作地点`, visible: true },
    { key: 8, label: `请求参数`, visible: true },
    { key: 9, label: `返回参数`, visible: true },
    { key: 10, label: `操作状态`, visible: true },
    { key: 11, label: `错误消息`, visible: true },
    { key: 12, label: `操作时间`, visible: true },
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
    const operatorIds = row.id || ids.value
    const confirmResult = await proxy.$confirm('是否要删除编号为' + operatorIds + '的数据，' + '是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult != 'confirm') {
        proxy.$message.info('已取消')
        return
    }
    const res = await proxy.$api.deleteSysOperationLog(operatorIds)
    if (res.code != 200) {
        proxy.$message.error(res.message)
        return
    } else {
        proxy.$message.success('删除成功')
        await getOperatorList()
    }
}

const handleClean = async () => {
    const confirmResult = await proxy.$confirm('是否清空操作日志？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult !== 'confirm') {
        return proxy.$message.info('已取消')
    }
    const res = await proxy.$api.cleanSysOperationLog()
    if (res.code !== 200) {
        proxy.$message.error(res.message)
    } else {
        proxy.$message.success('清空成功')
        await getOperatorList()
    }
}
</script>
  
<style scoped></style>