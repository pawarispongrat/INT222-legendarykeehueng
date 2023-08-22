<script setup>
import { RouterLink } from 'vue-router';
import Sidebaricon from '../icons/menu.vue';
</script>
 
<template>
<div class="drawer">
  <input id="my-drawer" type="checkbox" class="drawer-toggle" />
  <div class="drawer-content flex flex-row">
    <!-- Page content here -->
    <label for="my-drawer" class="drawer-button pl-4 pr-4 pt-8 bg-[#FAA497]"><Sidebaricon class="justify-center"></Sidebaricon></label>
  </div>  
 
  <div class="drawer-side"> 
    <label for="my-drawer" class="drawer-overlay"></label>
    <ul class="menu p-4 w-96 h-full bg-base-200 text-base-content">
      <!-- Sidebar content here -->
      <li class="bg-[#FAA497] bg-opacity-20 ann-app-title"><h1>SAS MENU</h1></li>
      <li class="ann-menu"><router-link :to="{ name: 'Announcement'}"><h3>Announcement</h3></router-link></li>
      <li class="ann-menu"><router-link :to="{ name: 'User'}"><h3>User</h3></router-link></li>
    </ul>
  </div>
</div>

</template>
 
<style scoped>

</style>