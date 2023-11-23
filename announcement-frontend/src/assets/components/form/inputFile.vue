<script setup>
  import { ref, onMounted } from 'vue';
  
  const MAX_FILES = 5;
  const filePreviews = ref([]);
const test = () =>{
    console.log(filePreviews.value);
}
  const handleFileChange = (event) => {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      if (filePreviews.value.length < MAX_FILES) {
        const reader = new FileReader();
        reader.onload = () => {
          filePreviews.value.push({
            name: file.name,
            url: reader.result,
          });
        };
        reader.readAsDataURL(file);
      }
    }
  };

  const deleteFile = (index) => {
    filePreviews.value.splice(index, 1);
  };
</script>

<template>
  <div>
    <input type="file" @change="handleFileChange" multiple />
    <div @click="test()" class="mt-4" v-for="(file, index) in filePreviews" :key="index">
      <p>{{ file.name }}</p>
      <img :src="file.url" alt="Preview" />
      <button class="btn bg-error mt-2 outline-none" @click="deleteFile(index)">Delete</button>
    </div>
  </div>
</template>