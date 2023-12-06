<script setup>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiFile } from '@mdi/js';
import { computed } from 'vue';

const props = defineProps({
  file: { type: Object, default: false },
});

const fileType = computed(() => {
  // console.log(props.file)
  // const segments = props.file?.fileType.split('/');
  // const extension = segments[segments.length - 1];
  const filename = props.file?.fileName
  const extension = ( filename.indexOf('.') > 0 ) ? filename.split('.').pop().toLowerCase() : ''
  return extension.toUpperCase();
});

function humanFileSize(bytes, si=true, dp=1) {
  const thresh = si ? 1000 : 1024;

  if (Math.abs(bytes) < thresh) {
    return bytes + ' B';
  }

  const units = si 
    ? ['kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'] 
    : ['KiB', 'MiB', 'GiB', 'TiB', 'PiB', 'EiB', 'ZiB', 'YiB'];
  let u = -1;
  const r = 10*dp;

  do {
    bytes /= thresh;
    ++u;
  } while (Math.round(Math.abs(bytes)*r) / r >= thresh && u < units.length - 1);


  return bytes.toFixed(dp) + ' ' + units[u];
}

</script>
 
<template>
<div  class=" relative flex  rounded-lg bg-slate-300">
        <p class="absolute mt-8 ml-8 transform -translate-x-1/2 -translate-y-1/2 z-10 text-slate-50 text-xs">{{ fileType }}</p>
        <svg-icon class=" flex-col pdf-icon m-2" type="mdi" :size="44" :path="mdiFile"></svg-icon>
        <a class=" flex-col flex justify-center" :href="file?.fileUrl">{{ file?.fileName }} <p>{{ humanFileSize(file.fileSize) }}</p></a>
      </div>
</template>
 
<style scoped>

</style>