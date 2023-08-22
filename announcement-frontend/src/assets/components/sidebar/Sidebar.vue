<script setup>
import { ref } from 'vue';
import SidebarItem from './SidebarItem.vue';
// import { MdInsertChart, MdNoteAlt, MdLibraryBooks, MdNotes } from 'your-icon-lib'; // Import your icon components here

// const items = ref([
//   { icon: MdInsertChart, path: "/", name: "แดชบอร์ด" },
//   { icon: MdNoteAlt, path: "/", name: "ใบคำขอกรมธรรม์" },
//   { icon: MdLibraryBooks, path: "/", name: "กรมธรรม์" },
// ]);

// const SideItems = () => {
//   return (
//     <ul class="h-full w-72 px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800 font-medium text-gray-900 dark:text-white ">
//       <div class="text-5xl py-8 px-3">JAO BROKER</div>
//       {items.value.map((item) => {
//         const Icon = item.icon;
//         return (
//           <li>
//             <a
//               href="/"
//               class="flex items-center p-3 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700"
//             >
//               <Icon size={24} />
//               <span class="ml-3">{item.name}</span>
//             </a>
//           </li>
//         );
//       })}
//     </ul>
//   );
// };
</script>

<template>
  <div class="w-72">
    <input id="my-drawer" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content">
      <label for="my-drawer"
        class="inline-flex items-center cursor-pointer p-3 mt-2 ml-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600">
      </label>
    </div>
    <div class="drawer-side">
      <label for="my-drawer" class="drawer-overlay"></label>
      <SidebarItem />
    </div>
    <aside class="fixed top-0 left-0 w-72 h-screen transition-transform -translate-x-full sm:translate-x-0 ">
      <SidebarItem />
    </aside>
    <div>
        <slot></slot>
    </div>
  </div>
</template>
<style scoped></style>