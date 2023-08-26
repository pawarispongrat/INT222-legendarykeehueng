<script setup>
import { ref } from 'vue';
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiFolderAccount ,mdiListBox } from '@mdi/js'

const HOVER_CLASS = "hover:bg-slate-900"
const items = ref([
    { path: "/admin/test", name: "Annoucement",icon: mdiListBox },
    { path: "/admin/user", name: "User",icon: mdiFolderAccount }
])
</script>
<template>
    <ul class="h-full w-80 px-6 py-6 overflow-y-auto bg-slate-950 text-white">
        <div class="text-4xl p-6 inline-flex gap-x-4">SAS APP</div>
        <li v-for="(item,index) in items" :key="index">
            <router-link :to="item.path" 
                class="flex items-center py-4 px-5 rounded-lg transition ease-in-out  text-white"
                :class="HOVER_CLASS"
                :active-class="'pointer-events-none'">
                <svg-icon type="mdi" :size="24" :path="item.icon"/>
                <span class="ml-3">{{ item.name }}</span>
            </router-link>
        </li>
    </ul>
</template>
<style scoped>
.active  { color: rgb(37 99 235); }
</style>
