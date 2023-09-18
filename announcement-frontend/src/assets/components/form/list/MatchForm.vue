<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import { ref } from 'vue';
import {matchPassword} from '@/assets/data/data-handler.js';
const matching = ref(String)
const check = ref({
      username:"",
      password:""
})
const match = async () => {
    console.log(check.value.password);
    await matchPassword(check.value)
    .then((status) => {
      console.log(status);
      if ( status === 200) matching.value = "match" 
      else if(status === 401) matching.value = "wrong"
      else if(status === 404) matching.value = "wrong username"
    })
}

</script>
 
<template>
       
  <form class="space-y-8" @submit.prevent="match()">
   
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <div v-if="matching !== undefined && matching === 'match'" class="ann-message justify-center text-center rounded-md bg-emerald-400">Password Matched</div>
                <div v-if="matching !== undefined && matching === 'wrong'" class="ann-message justify-center text-center rounded-md bg-rose-400">Password NOT Matched</div>
                <div v-if="matching !== undefined && matching === 'wrong username'" class="ann-message justify-center text-center rounded-md bg-rose-400">The specified username DOES NOT exist</div>
                <Input label="Username" placeholder="RewLegendary" class-name="ann-username" error-class-name="ann-error-username" :required="true"
                  :model-value="check.username"
                  @update="(input) => check.username = input"/>
                <Input label="Password" placeholder="" type="password" class-name="ann-password"
                  :model-value="check.password" 
                  @update="(input) => check.password = input.trim()"/>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button type="submit" name="Match or not" class="ann-button my-6 px-8 bg-blue-700 hover:bg-success" />
        </div>
    </form>
</template>
 
<style scoped>

</style>