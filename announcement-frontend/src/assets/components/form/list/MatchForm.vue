<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import { ref } from 'vue';
import {matchPassword} from '@/assets/data/data-handler.js';
const matching = ref(Boolean)
const check = ref({
      username:"",
      password:""
})
const match = () => {
    matchPassword(check.value)
    .then(result => {
    if( result === "match"){
      return matching.value = true 
    }else matching.value = false
  })
}

</script>
 
<template>
       
 <div class="space-y-8">
   
        <div class="flex justify-center">
            <div v-if="matching == true" class="justify-center absolute w-1/2 mb-10 text-center rounded-lg bg-green-500">Password Matched</div>
            <div v-if="matching == false" class="justify-center absolute w-1/2 mb-10 text-center rounded-lg bg-red-500">Worng Password</div>
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" class-name="ann-username" error-class-name="ann-error-username" :required="true"
                  :model-value="check.username"
                  @update="(input) => check.username = input"/>
                <Input label="Password" placeholder="" class-name="ann-password"
                  :model-value="check.password" 
                  @update="(input) => check.password = input"/>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Match or not" class="ann-button my-6 px-8 bg-blue-700 hover:bg-success" @click="match()" />
        </div>
    </div>
</template>
 
<style scoped>

</style>