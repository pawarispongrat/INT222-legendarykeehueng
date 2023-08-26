<script setup>
import Header from "@/assets/components/text/Header.vue";
import UserFrom from "@/assets/components/UserFrom.vue"
import User from "@/assets/data/user.js";
import { ref } from "vue";
import { createUser } from '@/assets/data/data-handler';

const user = ref(new User())
const create = (user,validate) => {
    if (validate) createUser(user.toJSON())
}
</script>
 
<template>
   <Header>Add User Detail:</Header>
   <UserFrom :user="user" @submit="create" submit-text="Save"></UserFrom>

</template>
 
<style scoped>

</style>