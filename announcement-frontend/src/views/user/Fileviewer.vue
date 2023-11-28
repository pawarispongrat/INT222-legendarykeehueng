<script setup>
import { computed } from 'vue';
import icon from '@/assets/components/icon/icon.vue'

const props = defineProps({
  file: { type: Object, default: false },
});

const isImageFile = (file) => {
  return file?.fileUrl.toLowerCase().match(/\.(jpeg|jpg|png|gif)$/i) !== null;
};

const isImage = computed(() => {
  return isImageFile(props.file);
});

</script>

<template>
    <div class="flex justify-center">
      <div v-if="isImage" class="image-container w-4/6 h-4/6 mt-10">
          <img :src="file?.fileUrl" alt="Image Preview" class="w-full h-300">
      </div>
      <div v-else class="w-4/6">
      <a :href="file?.fileUrl">
      <icon :file="file"/>
   
    </a>
    </div>
    </div>
  </template>
  
  <style scoped>
  
  </style>
  
  
