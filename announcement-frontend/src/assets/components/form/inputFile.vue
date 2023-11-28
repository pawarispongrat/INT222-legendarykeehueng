<script setup>
import { ref,watch } from 'vue';
import {toast} from "vue3-toastify";

const props = defineProps({
  files: Array
})
const previewImageFiles = ref(new Map());
watch([props.files], (old,files) => {
  files[0].forEach(file => previewImage(file))
}, { deep: true })


const chooseBinaryFile = (event) => {
  const file = event.target.files[0];
  if(props.files.some(f=>f.name === file.name)) toast.error("File already exist",{ hideProgressBar: true })
  else props.files.push(file);
};
const previewImage = (file) => {
  if (!isImage(file.name)) return
  const reader = new FileReader()
  reader.onload = () => {
    previewImageFiles.value.set(file.name, reader.result);
  }
  reader.readAsDataURL(file)
}  


const deleteFile = (fileName, index) => {
  props.files.splice(index, 1);
  previewImageFiles.value.delete(fileName);
};
const isImage = (fileName) => {
  const lowerCaseFileName = fileName.toLowerCase();
  const allowedExtensions = ['.png', '.jpeg', '.gif', '.jpg'];
  return allowedExtensions.some((extension) => lowerCaseFileName.endsWith(extension));
}
const getImage = (fileName) => previewImageFiles.value.get(fileName)
</script>

<template>
  <form id="formEle">
    File upload: <input :disabled="files.length === 5" type="file" accept=".jpg, .jpeg,.png,.pdf" @change="chooseBinaryFile" multiple />
    <div v-for="(file, index) in files" :key="index">
      <p>{{ file.name }}</p>
      <img v-if="isImage(file.name)" v-show="getImage(file.name)" :src="getImage(file.name)"
        class="w-24 h-24" />
      <button class="btn bg-error mt-2 outline-none" @click="deleteFile(file.name,index)">Delete</button>
    </div>
  </form>
</template>
