<script setup>
import Sidebar from '@/assets/components/sidebar/Sidebar.vue';
import { RouterView } from 'vue-router';
</script>
<template>
    <div id="modals"></div>
    <Sidebar>
        <router-view/>
    </Sidebar>
    
</template>