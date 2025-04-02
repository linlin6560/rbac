<template>
    <el-card>
        <el-form :model="queryParams" :inline="true" ref="queryRef" v-show="showSearch">
            <el-form-item label="部门名称" prop="deptName">
                <el-input placeholder="请输入岗位名称" clearable v-model="queryParams.deptName" />
            </el-form-item>
            <el-form-item label="部门状态" prop="deptStatus">
                <el-select placeholder="请输入岗位名称" v-model="queryParams.deptStatus">
                    <el-option v-for="item in deptStatusList" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button type="primary" icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button plain type="primary" icon="Plus" @click="handleAdd" v-hasPermission="['base:dept:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button plain type="info" icon="Sort" @click="toggleExpandAll">展开/折叠</el-button>
            </el-col>
            <TipHover v-model:showSearch="showSearch" @queryTable="getDeptList" :columns="columns" />
        </el-row>
        <el-table border stripe style="width: 100%;" :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
            v-loading="loading" :data="deptList" row-key="id" :tree-props="{ children: 'children' }" v-if="refreshTable"
            :default-expand-all="isExpandAll">
            <el-table-column label="部门名称" prop="deptName" v-if="columns[0].visible" />
            <el-table-column label="部门编码" prop="deptType" v-if="columns[1].visible">
                <template #default="scope">
                    <el-tag v-if="scope.row.deptType == 1">公司</el-tag>
                    <el-tag v-else-if="scope.row.deptType == 2" type="success">中心</el-tag>
                    <el-tag v-else-if="scope.row.deptType == 3" type="danger">部门</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="部门状态" prop="deptStatus" v-if="columns[2].visible">
                <template #default="scope">
                    <el-tag v-if="scope.row.deptStatus == 1" type="success">启用</el-tag>
                    <el-tag v-if="scope.row.deptStatus == 2" type="danger">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" v-if="columns[3].visible" />
            <el-table-column label="更多操作">
                <template #default="scope">
                    <el-button type="primary" link icon="Edit" @click="handleUpdate(scope.row.id)" v-hasPermission="['base:dept:edit']">编辑</el-button>
                    <el-button type="danger" link icon="Delete" @click="handleDelete(scope.row.id)"
                        :disabled="scope.row.deptType == '1' ? true : false" v-hasPermission="['base:dept:delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog :title="title" v-model="open" width="340px" @close="resetForm">
            <el-form ref="deptRef" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="上级部门" prop="parentId" v-if="form.parentId != 0">
                    <el-tree-select v-model="form.parentId" :data="addList" :props="{ value: 'id', label: 'deptName' }"
                        value-key='id' placeholder="选择上级部门" check-strictly />
                </el-form-item>
                <el-form-item label="部门名称" prop="deptName">
                    <el-input v-model="form.deptName" placeholder="请输入部门名称" />
                </el-form-item>
                <el-form-item label="部门类型" prop="deptType">
                    <el-radio-group v-model="form.deptType">
                        <el-radio :label="1">公司</el-radio>
                        <el-radio :label="2">中心</el-radio>
                        <el-radio :label="3">部门</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="部门状态" prop="deptStatus">
                    <el-radio-group v-model="form.deptStatus">
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="2">停用</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <template #footer>
                <div>
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button type="primary" @click="cancel">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </el-card>
</template>

<script setup>
import { ref, getCurrentInstance, nextTick } from 'vue'
const { proxy } = getCurrentInstance()
const deptStatusList = ref([
    {
        value: '1',
        label: '启用'
    },
    {
        value: '2',
        label: '停用'
    }
])
const queryParams = ref({})

const loading = ref(true)
const deptList = ref([])
const total = ref(0)
const getDeptList = async () => {
    loading.value = true
    const params = {
        deptName: queryParams.value.deptName,
        deptStatus: queryParams.value.deptStatus
    }
    const res = await proxy.$api.queryDeptList(params)
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        deptList.value = proxy.$common.handleTree(res.data, "id")
        loading.value = false
    }
}
getDeptList()

const handleQuery = () => {
    getDeptList()
}
const queryRef = ref()
const resetQuery = () => {
    queryRef.value.resetFields()
    getDeptList()
}
const showSearch = ref(true)
const columns = ref([
    { key: 0, label: `部门名称`, visible: true },
    { key: 1, label: `部门类型`, visible: true },
    { key: 2, label: `部门状态`, visible: true },
    { key: 3, label: `创建时间`, visible: true }
])
const refreshTable = ref(true)
const isExpandAll = ref(true)
const toggleExpandAll = () => {
    refreshTable.value = false
    isExpandAll.value = !isExpandAll.value
    nextTick(() => {
        refreshTable.value = true
    })
}

const rules = {
    parentId: [{ required: true, message: '上级部门不能为空', trigger: 'blur' }],
    deptName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }],
    deptType: [{ required: true, message: '部门类型不能为空', trigger: 'blur' }],
}
const title = ref("")
const form = ref({})
const open = ref(false)
const addList = ref([])
const resetForm = () => {
    form.value = {}
    proxy.$refs["deptRef"].resetFields()
}
const getTreeList = async () => {
    const res = await proxy.$api.queryDeptList()
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        addList.value = proxy.$common.handleTree(res.data, "id")
    }
}
const handleAdd = async () => {
    getTreeList()
    open.value = true
    title.value = '新增部门'
}
const handleUpdate = async (id) => {
    getTreeList()
    const res = await proxy.$api.deptInfo(id)
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        form.value = res.data
        open.value = true
        title.value = '修改部门'
    }
}
const cancel = () => {
    open.value = false
    proxy.$message.info('已取消')
}
const submitForm = () => {
    proxy.$refs['deptRef'].validate(valid => {
        if (valid) {
            if (form.value.id != undefined) {
                proxy.$api.deptUpdate(form.value).then(res => {
                    if (res.code != 200) {
                        proxy.$message.error(res.message)
                    } else {
                        proxy.$message.success('修改成功')
                        open.value = false
                        getDeptList()
                    }
                })
            } else {
                proxy.$api.addDept(form.value).then(res => {
                    if (res.code != 200) {
                        proxy.$message.error(res.message)
                    } else {
                        proxy.$message.success('新增成功')
                        open.value = false
                        getDeptList()
                    }
                })
            }
        }
    })
}

const handleDelete = async (id) => {
    const confirmResult = await proxy.$confirm('是否要删除编号为' + id + '的数据，' + '是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult != 'confirm') {
        proxy.$message.info('已取消')
        return
    }
    const res = await proxy.$api.deleteDept(id)
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        proxy.$message.success('删除成功')
        getDeptList()
    }
}
</script>

<style lang="scss" scoped></style>