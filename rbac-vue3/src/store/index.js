import { createStore } from "vuex"
import mutations from "./mutations"
import storage from "../utils/storage"

const state = {
    sysAdmin: "" || storage.getItem("sysAdmin"),
    token: "" || storage.getItem("token"),
    leftMenuList: "" || storage.getItem("leftMenuList"),
    permissionList: "" || storage.getItem("permissionList"),
    activePath: "" || storage.getItem("activePath")
}

export default createStore({
    state,
    mutations
})