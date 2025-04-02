<template>
    <el-card>
        <el-form :model="queryParams" :inline="true" ref="queryRef" v-show="showSearch">
            <el-form-item label="岗位名称" prop="postName">
                <el-input placeholder="请输入岗位名称" clearable v-model="queryParams.postName" />
            </el-form-item>
            <el-form-item label="岗位状态" prop="postStatus">
                <el-select placeholder="请输入岗位名称" v-model="queryParams.postStatus">
                    <el-option v-for="item in postStatusList" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item label="开始时间" prop="beginTime">
                <el-date-picker class="imput-width" type="date" style="width: 199px;" value-format="YYYY-MM-DD"
                    placeholder="请选择开始时间" v-model="queryParams.beginTime" />
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
                <el-date-picker class="imput-width" type="date" style="width: 199px;" value-format="YYYY-MM-DD"
                    placeholder="请选择结束时间" v-model="queryParams.endTime" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                <el-button type="primary" icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button plain type="primary" icon="Plus" @click="handleAdd" v-hasPermission="['base:post:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button plain type="danger" icon="Delete" @click="handleDelete" :disabled="multiple" v-hasPermission="['base:post:delete']">删除</el-button>
            </el-col>
            <TipHover v-model:showSearch="showSearch" @queryTable="getPostList" :columns="columns" />
        </el-row>
        <el-table border stripe style="width: 100%;" :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
            v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="ID" v-if="false" prop="id" />
            <el-table-column label="岗位名称" prop="postName" v-if="columns[0].visible" />
            <el-table-column label="岗位编码" prop="postCode" v-if="columns[1].visible" />
            <el-table-column label="岗位状态" prop="postStatus" v-if="columns[2].visible">
                <template #default="scope">
                    <el-tag v-if="scope.row.postStatus == 1" type="success">启用</el-tag>
                    <el-tag v-if="scope.row.postStatus == 2" type="danger">停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" v-if="columns[3].visible" />
            <el-table-column label="岗位备注" prop="remark" v-if="columns[4].visible" />
            <el-table-column label="更多操作">
                <template #default="scope">
                    <el-button type="primary" link icon="Edit" @click="handleUpdate(scope.row.id)" v-hasPermission="['base:post:edit']">编辑</el-button>
                    <el-button type="danger" link icon="Delete" @click="handleDelete(scope.row)" v-hasPermission="['base:post:delete']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <Pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize" @pagination="getPostList" />
        <el-dialog :title="title" v-model="open" width="500px" @close="resetForm">
            <el-form ref="postRef" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="岗位名称" prop="postName">
                    <el-input v-model="form.postName" placeholder="请输入岗位名称" />
                </el-form-item>
                <el-form-item label="岗位编码" prop="postCode">
                    <el-input v-model="form.postCode" placeholder="请输入岗位编码" />
                </el-form-item>
                <el-form-item label="岗位状态" prop="postStatus">
                    <el-radio-group v-model="form.postStatus">
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="2">停用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="岗位备注" prop="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入岗位备注" />
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
import { ref, getCurrentInstance } from 'vue'
const { proxy } = getCurrentInstance()
const postStatusList = ref([
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
const postList = ref([])
const total = ref(0)
const getPostList = async () => {
    loading.value = true
    const params = {
        pageSize: queryParams.value.pageSize,
        pageNum: queryParams.value.pageNum,
        postName: queryParams.value.postName,
        postStatus: queryParams.value.postStatus,
        beginTime: queryParams.value.beginTime,
        endTime: queryParams.value.endTime
    }
    const res = await proxy.$api.queryPostList(params)
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        postList.value = res.data.list
        total.value = res.data.total
        loading.value = false
    }
}
getPostList()

const handleQuery = () => {
    getPostList()
}
const queryRef = ref()
const resetQuery = () => {
    queryRef.value.resetFields()
    getPostList()
}

const showSearch = ref(true)
const columns = ref([
    { key: 0, label: `岗位名称`, visible: true },
    { key: 1, label: `岗位编码`, visible: true },
    { key: 2, label: `岗位状态`, visible: true },
    { key: 3, label: `创建时间`, visible: true },
    { key: 4, label: `岗位备注`, visible: true }
])

const rules = {
    postName: [{ required: true, message: '岗位名称不能为空', trigger: 'blur' }],
    postCode: [{ required: true, message: '岗位编码不能为空', trigger: 'blur' }],
    postStatus: [{ required: true, message: '岗位状态不能为空', trigger: 'blur' }],
}
const title = ref('')
const form = ref({})
const open = ref(false)
const resetForm = () => {
    form.value = {}
    proxy.$refs['postRef'].resetFields()
}
const handleAdd = () => {
    open.value = true
    title.value = '新增岗位'
}
const handleUpdate = (id) => {
    proxy.$api.postInfo(id).then(res => {
        form.value = res.data
        open.value = true
        title.value = '修改岗位'
    })
}
const cancel = () => {
    open.value = false
    proxy.$message.info('已取消')
}
const submitForm = () => {
    proxy.$refs['postRef'].validate(valid => {
        if (valid) {
            if (form.value.id != undefined) {
                proxy.$api.updatePost(form.value).then(res => {
                    if (res.code != 200) {
                        proxy.$message.error(res.message)
                    } else {
                        proxy.$message.success('修改成功')
                        open.value = false
                        getPostList()
                    }
                })
            } else {
                proxy.$api.addPost(form.value).then(res => {
                    if (res.code != 200) {
                        proxy.$message.error(res.message)
                    } else {
                        proxy.$message.success('新增成功')
                        open.value = false
                        getPostList()
                    }
                })
            }
        }
    })
}

const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const handleSelectionChange = (selection) => {
    ids.value = selection.map(item => item.id)
    single.value = selection.length != 1
    multiple.value = !selection.length
}
const handleDelete = async (row) => {
    const postIds = row.id || ids.value
    const confirmResult = await proxy.$confirm('是否要删除编号为' + postIds + '的数据，' + '是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).catch(err => err)
    if (confirmResult != 'confirm') {
        proxy.$message.info('已取消')
        return
    }
    const res = await proxy.$api.batchDeleteSysPost(postIds)
    if (res.code != 200) {
        proxy.$message.error(res.message)
    } else {
        proxy.$message.success('删除成功')
        getPostList()
    }
}
</script>

<style lang="scss" scoped></style>