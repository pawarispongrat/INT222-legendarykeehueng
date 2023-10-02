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
const items = ref([
    { path: "/admin/announcement", name: "Annoucement",icon: mdiListBox },
    { path: "/admin/user", name: "User",icon: mdiFolderAccount }
])
</script>
<template>
    <ul class="h-full w-80 py-6 overflow-y-auto bg-[#D4A276] text-white relative">
        <div class="ann-app-title text-4xl p-6 px-10  inline-flex gap-x-4">SAS</div>
        <li v-for="(item,index) in items" :key="index" class="px-5">
            <router-link :to="item.path" 
                class="ann-menu flex items-center p-4 rounded-lg transition ease-in-out hover:bg-[#bb8556]  text-white "
                active-class="pointer-events-none text-[#b46520]">
                <svg-icon type="mdi" :size="24" :path="item.icon"/>
                <span class="ml-3">{{ item.name }}</span>
            </router-link>
        </li>
        <div class="absolute bottom-6 w-full px-6">
        <ModalButton :modal-id="`ann`" type="mdi" 
            :size="24" :path="mdiLogout" name="Sign out" 
            class=" bg-[#b43e20] w-full hover:bg-[#b42020]"/>
         <Modal :modal-id="`ann`" 
            @confirm="logOut()" :icon="mdiAlertCircleOutline" 
            :title="`Do you want to Sign out?`"
         />
        </div>
    </ul>
</template>
<style scoped></style>
