import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index.js'
import './assets/main.css'
import {createPinia} from 'pinia'
import {LoadingPlugin} from "vue-loading-overlay";
import 'vue3-toastify/dist/index.css';
import 'vue-loading-overlay/dist/css/index.css';

const app = createApp(App)
const pinia = createPinia()

import "@/assets/data/msalAuthenticate.js"
import {msal} from "@/assets/data/msalAuthenticate";

msal.initialize()
app.use(router)
app.use(LoadingPlugin, {
    isFullPage: false
})
app.use(Vue3Toastify, { autoClose: 3000, dangerouslyHTMLString: false, toastClassName: "font font-semibold" })
app.use(pinia)
app.mount('#app')
