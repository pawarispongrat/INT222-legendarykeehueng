<script setup>
import { onMounted, ref } from 'vue';

const props = defineProps({
  filesAnnouncement: Array
})


const files = ref([]);
const previewImageFiles = ref([]);

const chooseBinaryFile = (event) => {
  const file = event.target.files[0];
  previewImage(file);
  files.value.push(file);
  files.value = props.filesAnnouncement
};

const previewImage = async () => {
  const reader = new FileReader();
  reader.onload = () => {
    previewImageFiles.value.push(reader.result);
  };
  // const response = await fetchl("http://ocalhost:8080/attachments/85/Screenshot%202023-11-27%20002546%20%284%29.png",{
  //   method: "GET",
  //   mode: "no-cors"
  // } ) 
  const blob = await getFileFromUrl(url)
  reader.readAsDataURL(blob);
  files.value = props.filesAnnouncement
};
const url = "http://localhost:8080/attachments/85/Screenshot%202023-11-27%20002546%20%284%29.png"
async function getFileFromUrl(url){
  const response = await fetch(url,{mode:"no-cors"});
  const data = await response.blob();
  return new File([data], 'Screenshot%202023-11-27%20002546%20%284%29.png', {
    type: data.type 
  });
}
onMounted(async () => {
  console.log(await getFileFromUrl("http://localhost:8080/attachments/85/Screenshot%202023-11-27%20002546%20%284%29.png"));
  })
//  console.log(URL.createObjectURL(await fetch(url,{mode:"no-cors"}).then(res => res.blob())));
const deleteFile = (index) => {
  files.value.splice(index, 1);
  files.value = props.filesAnnouncement
};
const isImage = (fileName) => {
  const lowerCaseFileName = fileName.toLowerCase();
  const allowedExtensions = ['.png', '.jpeg', '.gif', '.jpg'];
  return allowedExtensions.some((extension) => lowerCaseFileName.endsWith(extension));
};
const test = () => { console.log(files.value); console.log(previewImageFiles.value);console.log(props.filesAnnouncement); }
</script>

<template>
  <form id="formEle"  @click="test()">
    File upload: <input type="file" accept=".jpg, .jpeg,.png,.pdf" @change="chooseBinaryFile" multiple />
    <div v-for="(file, index) in files" :key="index">
      <p>{{ file.name }}</p>
      <img v-if="isImage(file.name)" v-show="previewImageFiles[index]" :src="previewImageFiles[index]"
        class="w-24 h-24" />
      <button class="btn bg-error mt-2 outline-none" @click="deleteFile(index)">Delete</button>
    </div>
  </form>
</template>
