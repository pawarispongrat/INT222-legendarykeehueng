<script setup>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiFile } from '@mdi/js';
import { computed } from 'vue';

const props = defineProps({
  file: { type: Object, default: false },
});

const fileType = computed(() => {
  const segments = props.file?.fileType.split('/');
  const extension = segments[segments.length - 1];
  return extension.toUpperCase();
});

</script>
 
<template>
<div  class="pdf-container mt-3 relative flex  rounded-lg bg-slate-300">
        <p class="absolute mt-8 ml-8 transform -translate-x-1/2 -translate-y-1/2 z-10 text-slate-50 text-xs">{{ fileType }}</p>
        <svg-icon class=" flex-col pdf-icon my-1 ml-2" type="mdi" :size="44" :path="mdiFile"></svg-icon>
        <a class="mt-3 ml-4 flex-col" :href="file?.fileUrl">{{ file?.fileName }}</a>
      </div>
</template>
 
<style scoped>

</style>