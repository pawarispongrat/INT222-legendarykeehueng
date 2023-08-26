<script setup>
import SidebarItem from './SidebarItem.vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMenu } from '@mdi/js';

</script>

<template>
  <div class="drawer">
      <input id="sidebar" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content p-12">
        <label htmlFor="sidebar"
          class="drawer-button inline-flex items-center p-3 cursor-pointer text-sm rounded-lg lg:hidden focus:outline-none hover:bg-slate-200">
          <svg-icon type="mdi" :path="mdiMenu" :size="24"/>
        </label>
        <div class="lg:ml-80">
          <slot></slot>
        </div>
      </div>
      <div class="drawer-side">
        <label htmlFor="sidebar" class="drawer-overlay"></label>
        <SidebarItem />
      </div>
      <aside class="fixed top-0 left-0 w-80 h-screen transition-transform -translate-x-full lg:translate-x-0 ">
        <SidebarItem/>
      </aside>
  </div>


</template>
<style scoped></style>