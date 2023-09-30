<script setup>
import Input from '../assets/components/form/Input.vue';
import { ref } from 'vue';
import Button from '../assets/components/form/Button.vue';
import { createNewToken } from '../assets/data/data-handler';
import router from '@/router';

const submitForm = async () => {
  try {
    await createNewToken(check.value);
    router.push({ name: 'Announcement' })
  } catch (error) {
    // Handle error, e.g., display an error message
  }
};
const check = ref({
      username: "",
      password: ""
})

</script>
 
<template>
<div class=" bg-slate-50  flex justify-center items-center h-screen">
<div class="w-1/2 h-screen hidden lg:block ">
  <img src="https://campus.campus-star.com/app/uploads/2020/10/lx-bu.jpg" alt="Placeholder Image" class="object-cover w-full h-full">
</div>
<!-- Right: Login Form -->
<div class="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
  <h1 class="text-6xl font-semibold mb-4">Login</h1>
  <form  @submit.prevent="submitForm" >
    <!-- Username Input -->
    <div class="mb-4">
        <Input label="Username" class-name="ann-username" error-class-name="ann-error-username" 
        v-model.trim="check.username" :required="true"/>
    </div>
    <!-- Password Input -->
    <div class="mb-4">
        <Input label="Passname"  class-name="ann-password" error-class-name="ann-error-password" 
        v-model.trim="check.password" :required="true"/>
    </div>
    <Button type="submit" name="LOGIN"  class="ann-button my-6 px-8 bg-orange-700 hover:bg-success" />
  </form>
</div>
</div>
</template>
 
<style scoped>

</style>