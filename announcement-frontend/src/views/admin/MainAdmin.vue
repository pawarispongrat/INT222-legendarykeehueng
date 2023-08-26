<script setup>
import Sidebar from '@/assets/components/sidebar/Sidebar.vue';
import { RouterView } from 'vue-router';
</script>
<template>
    <Sidebar>
        <Suspense>
            <router-view/>
        </Suspense>
    </Sidebar>
</template>