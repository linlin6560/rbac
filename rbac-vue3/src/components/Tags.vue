<template>
    <div class="tags">
        <el-tag class="tag" size="large" v-for="item, index in tags" :key="item.path"
            :effect="item.title == route.meta.tTitle ? 'dark' : 'plain'" @click=goTo(item.path) :closable="index > 0"
            @close="close(index)" :disable-transitions="true">
            <i class="circular" v-show="item.title == route.meta.tTitle"></i>
            {{ item.title }}
        </el-tag>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()
const { proxy } = getCurrentInstance()

const tags = ref([{
    title: '首页',
    path: '/welcome'
}])

watch(
    () => route,
    (newVal, oldVal) => {
        if (tags.value.length != undefined && tags.value.length != null && tags.value.length > 18) {
            return proxy.$message.error('最大添加18个标签')
        }
        const boolean = tags.value.find(item => {
            return newVal.path == item.path
        })
        if (!boolean) {
            tags.value.push({
                title: newVal.meta.tTitle,
                path: newVal.path
            })
        }
    },
    { immediate: true, deep: true }
)

const goTo = (path) => {
    router.push(path)
}

const close = (i) => {
    if (tags.value[i].path == route.path && i != tags.value.length - 1) {
        router.push(tags.value[tags.value.length - 1].path)
    } else if (i == tags.value.length - 1) {
        router.push(tags.value[tags.value.length - 2].path)
    }
    tags.value.splice(i, 1)
}
</script>

<style lang="scss" scoped>
.tags {
    padding-left: 20px;
    padding-top: 2px;
    padding-bottom: 2px;

    .tag {
        margin-right: 3px;
        cursor: pointer;

        .circular {
            width: 8px;
            height: 8px;
            margin-right: 4px;
            background-color: #fff;
            border-radius: 50%;
            display: inline-block;
        }
    }
}
</style>