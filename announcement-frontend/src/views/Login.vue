<script setup>
import Input from '../assets/components/form/Input.vue';
import { ref } from 'vue';
import Button from '../assets/components/form/Button.vue';
import { createNewToken } from '../assets/data/data-handler';
import router from '@/router';

const status = ref(undefined)

const submitForm = async () => {
       const result = await createNewToken(check.value);
       status.value = result; // Assign the HTTP status code
       if(status.value === 200){
        router.push({ name: 'Announcement' });
       }
}
const check = ref({
      username: "",
      password: ""
})

</script>
 
<template>
<div class=" bg-slate-50  flex justify-center items-center h-screen">
<div class="w-1/2 h-screen hidden lg:block ">
  <img src="https://cdn.discordapp.com/attachments/950361261298827294/1157928414754373712/image.png?ex=651a645f&is=651912df&hm=928f22cf49872fb21e1c2154be6fc7c9ae4f9ddef43d238766acef54f8d4820e&" alt="Placeholder Image" class="object-cover w-full h-full">
</div>
<!-- Right: Login Form -->
<div class="lg:p-36 md:p-52 sm:20 p-8 w-full lg:w-1/2">
  <h1 class="text-6xl font-semibold mb-4">Login</h1>
  <form  @submit.prevent="submitForm" >
       <div v-if="status === 200" class="ann-message text-center rounded-md bg-emerald-400 text-white p-2">Password Matched</div>
       <div v-else-if="status === 401" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Password NOT Matched</div>
       <div v-else-if="status === 404" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">The specified username DOES NOT exist</div>
       <div v-else-if="status !== undefined" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Something went wrong!</div>
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