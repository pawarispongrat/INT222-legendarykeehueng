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
       if (status.value === 200) router.push({ name: 'Announcement' });
}
const check = ref({
      username: "",
      password: ""
})

</script>
 
<template>
<div class="bg-slate-50 flex items-center justify-center w-screen h-screen">
  <div class="w-full h-screen hidden lg:block">
    <img src="https://cdn.discordapp.com/attachments/950361261298827294/1157928414754373712/image.png?ex=651a645f&is=651912df&hm=928f22cf49872fb21e1c2154be6fc7c9ae4f9ddef43d238766acef54f8d4820e&" alt="Placeholder Image" class="object-cover w-full h-full">
  </div>
  <!-- Right: Login Form -->
  <div class="w-full max-w-2xl flex flex-col items-center justify-center">
    <div class="w-full max-w-xl p-8">
      <h1 class="text-2xl font-semibold mb-3">SIT Announcement</h1>
      <h1 class="text-3xl font-semibold mb-3">Welcome back</h1>
      <form @submit.prevent="submitForm">
            <!-- <div v-if="status === 200" class="ann-message w-full text-center rounded-md bg-emerald-400 text-white p-2">Password Matched</div> -->
            <!-- <div v-if="status === 401" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Password NOT Matched</div>
            <div v-else-if="status === 404" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">The specified username DOES NOT exist</div> -->
            <div v-if="status === 401" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Incorrect Password</div>
            <div v-else-if="status === 404" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Username does not exists</div>
            <div v-else-if="status !== undefined" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Please contact admin for the error!</div>
            <!-- Username Input -->
            <Input label="Username" class-name="ann-username" placeholder="RewLegendary" error-class-name="ann-error-username" 
                v-model.trim="check.username" :required="true"/>
            <!-- Password Input -->
            <Input label="Password" type="password"  class-name="ann-password" placeholder="•••••••••••••" error-class-name="ann-error-password" 
                v-model.trim="check.password" :required="true"/>
            <Button type="submit" name="Sign in to your account" class="ann-button w-full max-w-xl my-6 py-2  bg-orange-400 hover:bg-orange-500" />
      </form>
  </div>
  </div>
</div>
</template>
 
<style scoped>

</style>