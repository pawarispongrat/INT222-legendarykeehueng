<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import Dropdown from '../Dropdown.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { createUser } from "@/assets/data/dataHandler.js"
import { isUniqueUser,isEmptyUser } from '@/assets/data/validate.js';
import { toast } from 'vue3-toastify/index';

defineEmits(["submit"])
const ROLE_ENUM = [ 'announcer','admin']

const confirm = ref("")
const router = useRouter()
const user = ref({})
const errors = ref({})
const onCreate = async () => { 
    errors.value = { username: [], password: [], confirmPassword: [], name: [], email: [], role: [] } 
    if (!validateInput()) return
    const json = await createUser(user.value) 
    if (!isUniqueUser(json?.detail,errors)) router.push("/admin/user/")
    if (json?.status !== 200) handleServerError(json?.detail)
}

const validateInput = () => {
   const password = user.value?.password 
   const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,14}$/
   if (!(password.length >= 8 && password.length <= 14)) errors.value.password.push("Password size must be between 8 and 14") 
   if (!regex.test(password)) errors.value.password.push("must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters") 
   if (password !== confirm.value) errors.value.password.push("The password DOES NOT match") 
   return isEmptyUser(user,errors)
}


</script>
<template>
    <form class="space-y-8" @submit.prevent="onCreate">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" class-name="ann-username" error-class-name="ann-error-username" :required="true" 
                        v-model.trim="user.username" :max="45" :errors="errors.username" />
                <Input label="Password" placeholder="MyPasswordIsCool123!@" class-name="ann-password" type="password" error-class-name="ann-error-password" :required="true"
                        v-model.trim="user.password"  :max="14" :errors="errors.password"/>
                <Input label="Confirm password"  placeholder="MyPasswordIsCool123!@" type="password" class-name="ann-confirm-password" error-class-name="ann-error-confirm-password" :required="true"
                        v-model.trim="confirm" :max="14" :errors="errors.confirmPassword"/>                
                <Input label="Name" placeholder="Duangcharoen Siwasutum" class-name="ann-name" error-class-name="ann-error-name" :required="true"
                        v-model.trim="user.name" :max="100" :errors="errors.name"/>
                <Input label="Email" placeholder="rewlegendary@email.com" type="email" class-name="ann-email" error-class-name="ann-error-email" :required="true"
                        v-model.trim="user.email" :max="150" :errors="errors.email"/>
                <Dropdown label="Role" :options="ROLE_ENUM" :isFull="true" class-name="ann-role" error-class-name="ann-error-role"
                        :select="user.role ?? ROLE_ENUM[0]" @update="(value) => user.role = value" :errors="errors.role"/>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Cancel" to="/admin/user" class="ann-button my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
                <Button name="Add" type="submit" class="ann-button my-6 px-8 bg-blue-700 hover:bg-success" />
        </div>
    </form>
</template>