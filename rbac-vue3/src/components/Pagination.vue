<template>
    <div class="pagination_container">
        <el-pagination :background="background" v-model:current-page="currentPage" v-model:page-size="pageSize"
            :layout="layout" :page-sizes="pageSizes" :pager-count="pagerCount" :total="total"
            @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>
</template>

<script setup>
import { computed } from 'vue'
const props = defineProps({
    total: {
        required: true,
        type: Number
    },
    page: {
        type: Number,
        default: 1
    },
    limit: {
        type: Number,
        default: 10
    },
    pageSizes: {
        type: Array,
        default() {
            return [10, 50, 100, 200, 500, 1000]
        }
    },
    pagerCount: {
        type: Number,
        default: document.body.clientWidth < 992 ? 5 : 7
    },
    layout: {
        type: String,
        default: 'total, size, prev, pager, next, jumper'
    },
    background: {
        type: Boolean,
        default: true
    }
})
const emit = defineEmits()
const currentPage = computed({
    get() {
        return props.page
    },
    set(val) {
        emit('update:page', val)
    }
})
const pageSize = computed({
    get() {
        return props.limit
    },
    set(val) {
        emit('update:limit', val)
    }
})
function handleSizeChange(val) {
    if (currentPage.value*val > props.total) {
        currentPage.value = 1
    }
    emit('pagination', {page: currentPage.value, limit, val})
}
function handleCurrentChange(val) {
    emit('pagination',{page: val, limit: pageSize.value})
}
</script>

<style lang="scss" scoped>
.pagination_container {
    background-color: #fff;
    padding: 5px 0px;
}
</style>