<script setup>
import { ref } from 'vue';
import router from '@/router';
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiFolderAccount ,mdiListBox,mdiAlertCircleOutline,mdiLogout,mdiAccountCircle,mdiBullhorn,mdiTextBoxCheck  } from '@mdi/js'
import ModalButton from '@/assets/components/modal/ModalButton.vue';
import Modal from '@/assets/components/modal/Modal.vue';
import {clearToken, getJwtName, isAdmin} from '@/assets/data/tokenStorage.js'
import {Auth} from "@/assets/data/msalAuthenticate";
import {useMsal} from "@/assets/stores/useMsal";

const users = ref([])
const { logout } = useMsal()
const logOut = async () =>{
    clearToken()
    if (Auth.isLoggedIn()) await logout()
    await router.push({ name: 'Login' })
}
const items = ref([
    { path: "/announcement", name: "Announcement Viewer",icon: mdiBullhorn},
    { path: "/admin/announcement", name: "Announcement",icon: mdiListBox },
    { path: "/admin/user", name: "User",icon: mdiFolderAccount, condition: isAdmin() },
    { path: "/admin/user/match", name: "Match Password",icon: mdiTextBoxCheck, condition: isAdmin() }
])
</script>
<template>
    <ul class="h-full w-80 py-6 overflow-y-auto bg-[#D4A276] text-white relative">
        <div class="ann-app-title text-4xl font-bold p-6 px-9 inline-flex gap-x-4">SAS APP</div>
        <div class="ann-app-title flex items-center pb-8 px-8 gap-x-3">
          <svg-icon class="flex" type="mdi" :size="32" :path="mdiAccountCircle"/>
          <span class="text-lg">{{ getJwtName() }}</span>
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
