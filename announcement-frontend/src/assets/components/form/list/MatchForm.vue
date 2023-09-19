<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import { ref } from 'vue';
import {matchPassword} from '@/assets/data/data-handler.js';

const status = ref(undefined)
const check = ref({
      username: "",
      password: ""
})
const match = () => matchPassword(check.value).then((result) =>  status.value = result )

</script>
 
<template>
       
  <form class="space-y-8" @submit.prevent="match">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <div v-if="status === 200" class="ann-message text-center rounded-md bg-emerald-400 text-white p-2">Password Matched</div>
                <div v-else-if="status === 401" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Password NOT Matched</div>
                <div v-else-if="status === 404" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">The specified username DOES NOT exist</div>
                <div v-else-if="status !== undefined" class="ann-message text-center rounded-md bg-rose-400 text-white p-2">Something went wrong!</div>
                <Input label="Username" placeholder="RewLegendary" class-name="ann-username" error-class-name="ann-error-username" 
                  v-model.trim="check.username" :required="true"/>
                <Input label="Password" placeholder="MyPasswordIsCool123!@" type="password" class-name="ann-password"
                  v-model.trim="check.password" :required="true"/>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button type="submit" name="Match or not" class="ann-button my-6 px-8 bg-blue-700 hover:bg-success" />
        </div>
    </form>
</template>
 
<style scoped>

</style>