<script setup>
import Input from '@/assets/components/form/Input.vue';
import {ref} from 'vue';
import Button from '@/assets/components/form/Button.vue';
import {createNewToken} from '@/assets/data/dataHandler';
import router from '@/router';
import {toast} from "vue3-toastify";

const submitForm = async () => {
    const status = await createNewToken(check.value); // Assign the HTTP status code
    if (status === 200) {
      await router.push({name: 'Announcement'})
      await toast.success("Login Successful",{ hideProgressBar: true })
    } else {
      let message
      switch (status) {
        case 401:
        case 404:
          message = "Incorrect Username / Password"
          break
        case 403:
          message = "Inaccessible"
          break
        default: message = "Please contact admin for the error!!"
      }
      toast.error(message,{ hideProgressBar: true })
    }
}
const check = ref({ username: "", password: "" })
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
            <Input label="Username" class-name="ann-username" placeholder="RewLegendary" error-class-name="ann-error-username"
                v-model.trim="check.username" :required="true"/>
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