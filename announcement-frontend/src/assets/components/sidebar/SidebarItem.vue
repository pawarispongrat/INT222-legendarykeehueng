<script setup>
import { ref } from 'vue';
const items = ref([
    { path: "/admin/announcement", name: "Annoucement" },
    { path: "/admin/user", name: "User" }
])
</script>
<template>
    <ul
        class="h-full w-72 px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800 font-medium text-gray-900 dark:text-white ">
        <div class="text-5xl py-8 px-3">SAS MENU</div>
        <li v-for="item in  items" :key="item.name">
            <RouterLink :to="item.path" class="flex items-center p-3 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700">
                <span class="ml-3">{{ item.name }}</span>
            </RouterLink>
        </li>
    </ul>

</template>
    <style scoped></style>
