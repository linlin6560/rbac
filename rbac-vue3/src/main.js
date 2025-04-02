import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import request from './utils/request'
import storage from './utils/storage'
import store from './store'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './assets/css/global.css'
import api from './api'
import Pagination from '@/components/Pagination.vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import TipHover from '@/components/TipHover.vue'
import common from "@/utils/common"
import { hasPermission } from '@/permission/permission'

const app = createApp(App)
app.config.globalProperties.$request = request
app.config.globalProperties.$storage = storage
app.config.globalProperties.$api = api
app.config.globalProperties.$common = common
app.use(router)
app.use(store)
app.use(ElementPlus, {
  locale: zhCn
})
app.use(hasPermission)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.component('Pagination', Pagination)
app.component('TipHover', TipHover)
app.mount('#app')
