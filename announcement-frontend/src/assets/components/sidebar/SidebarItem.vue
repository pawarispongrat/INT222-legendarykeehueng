<script setup>
import { onMounted, ref } from 'vue';
import router from '@/router';
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiFolderAccount ,mdiListBox,mdiAlertCircleOutline,mdiLogout  } from '@mdi/js'
import ModalButton from '@/assets/components/modal/ModalButton.vue';
import Modal from '@/assets/components/modal/Modal.vue';
const logOut = () =>{
    localStorage.clear();
    router.push({ name: 'Login' })
}
let icon = ref()
icon = mdiLogout
const HOVER_CLASS = "hover:bg-slate-900"
const HOVER_LOGOUT = "hover:bg-red-900"
const items = ref([
    { path: "/admin/announcement", name: "Annoucement",icon: mdiListBox },
    { path: "/admin/user", name: "User",icon: mdiFolderAccount }
])
</script>
<template>
    <ul class="h-full w-80 px-6 py-6 overflow-y-auto bg-[#D4A276] text-white">
        <div class="ann-app-title text-4xl p-6 inline-flex gap-x-4">SAS</div>
        <li v-for="(item,index) in items" :key="index">
            <router-link :to="item.path" 
                class="ann-menu flex items-center py-4 px-5 rounded-lg transition ease-in-out  text-white"
                :class="HOVER_CLASS"
                :active-class="'pointer-events-none'">
                <svg-icon type="mdi" :size="24" :path="item.icon"/>
                <span class="ml-3">{{ item.name }}</span>
            </router-link>
        </li>
        <li>
         <ModalButton :modal-id="`ann`" 
         type="mdi" :size="24" :path="mdiLogout" name="logout" 
         :class="HOVER_LOGOUT" class="px-6 bg-red-500 w-full h-full py-4">
         </ModalButton>
         <Modal :modal-id="`ann`" 
            @confirm="logOut()" :icon="mdiAlertCircleOutline" 
            :title="`Do you want to Logout?`"
         />
        </li>
    </ul>
</template>
<style scoped>
.active  { color: rgb(37 99 235); }
</style>
