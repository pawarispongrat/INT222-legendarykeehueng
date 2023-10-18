<script setup>
import { onMounted, ref } from 'vue';
import { getUser } from '@/assets/data/dataHandler.js';
import router from '@/router';
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiFolderAccount ,mdiListBox,mdiAlertCircleOutline,mdiLogout,mdiAccountCircle,mdiBullhorn,mdiTextBoxCheck  } from '@mdi/js'
import ModalButton from '@/assets/components/modal/ModalButton.vue';
import Modal from '@/assets/components/modal/Modal.vue';
import { getAccessToken, getJwtSubject, isAdmin } from '@/assets/data/tokenStorage.js'

const users = ref([])
const logOut = () =>{
    localStorage.clear();
    router.push({ name: 'Login' })
}
const items = ref([
    { path: "/announcement", name: "Announcement Viewer",icon: mdiBullhorn},
    { path: "/admin/announcement", name: "Annoucement",icon: mdiListBox },
    { path: "/admin/user", name: "User",icon: mdiFolderAccount, condition: isAdmin() },
    { path: "/admin/user/match", name: "Match Password",icon: mdiTextBoxCheck, condition: isAdmin() }
])
</script>
<template>
    <ul class="h-full w-80 py-6 overflow-y-auto bg-[#D4A276] text-white relative">
        <div class="ann-app-title text-4xl p-6 px-10  inline-flex gap-x-4">SAS</div>
        <div class="ann-app-title text-4xl p-6 px-10  flex gap-x-4">
        <svg-icon class="flex" type="mdi" :size="50" :path="mdiAccountCircle"/>
        <span class="ml-3">{{ getJwtSubject() }}</span>
        </div>     
        <li v-for="(item,index) in items" :key="index" class="px-5">
            <router-link :to="item.path" v-if="item?.condition !== undefined ? item.condition : true"
                class="text-white ann-menu flex items-center p-4 rounded-lg transition ease-in-out hover:bg-[#bb8556]">
                <svg-icon type="mdi" :size="24" :path="item.icon"/>
                <span class="ml-3">{{ item.name }}</span>
            </router-link>
        </li>
        <div class="absolute bottom-5 w-full px-6">
        <ModalButton :modal-id="`ann`" type="mdi" 
            size="24" :path="mdiLogout" name="Sign out" 
            class=" bg-[#883728] w-full hover:bg-[#b42020]"/>
         <Modal :modal-id="`ann`" 
            @confirm="logOut()" :icon="mdiAlertCircleOutline" 
            :title="`Do you want to Sign out?`"
         />
        </div>
    </ul>
</template>
<style scoped>
.active {
    color: #8b4d16;
    cursor: none;
    pointer-events: none;
}
</style>
