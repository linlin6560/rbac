import axios from 'axios'
import router from '../router/router'
import { ElMessage } from 'element-plus'
import storage from './storage'

const service = axios.create({
    baseURL: import.meta.env.VUE_APP_BASE_API,
    timeout: 8000
})

service.interceptors.request.use((req)=>{
    const headers = req.headers
    const token = storage.getItem("token") || {}
    if (!headers.Authorization) {
        headers.Authorization = token
    }
    return req
})

service.interceptors.response.use((res)=>{
    const {code, data, message} = res.data
    if (code == 401) {
        ElMessage.error(message)
        storage.clearAll()
        setTimeout(() => {
            router.push('/login')
        }, 1500);
    } else if (code == 200) {
        return res.data
    } else {
        ElMessage.error(message)
    }
})

function request(options) {
    options.method = options.method || 'get'
    if (options.method.toLowerCase() == 'get') {
        options.params = options.data
    }
    service.defaults.baseURL = import.meta.env.VITE_APP_BASE_API
    return service(options)
}

export default request