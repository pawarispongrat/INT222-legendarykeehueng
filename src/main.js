import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import './assets/main.css'
import { createPinia } from 'pinia'
import { LoadingPlugin } from "vue-loading-overlay";
import 'vue3-toastify/dist/index.css';
import 'vue-loading-overlay/dist/css/index.css';

const app = createApp(App)

app.use(LoadingPlugin, {
    isFullPage: false
})
app.use(Vue3Toastify, { autoClose: 3000, dangerouslyHTMLString: false })
app.use(createPinia())
app.use(router)
app.mount('#app')
