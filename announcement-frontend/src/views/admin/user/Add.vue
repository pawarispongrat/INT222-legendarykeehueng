<script setup>
import Header from "@/assets/components/text/Header.vue";
import UserForm from "@/assets/components/form/list/UserForm.vue"
import { provide, ref } from "vue";
import { createUser } from '@/assets/data/data-handler';
import { useRouter } from "vue-router";

const router = useRouter()
const user = ref({})
const onCreate = async () => { 
    const json = await createUser(user.value) 
    if (!json?.status) router.push("/admin/user/")
    else errors.value = json.detail
}
const errors = ref()
provide("errors",errors)
</script>
 
<template>
        <Header class-name="flex justify-center py-8">Add User</Header>
        <UserForm :user="user" @submit="onCreate"/>
</template>
 
<style scoped>

</style>