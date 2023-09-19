<script setup>
import Header from "@/assets/components/text/Header.vue";
import UserEditForm from "@/assets/components/form/list/UserEditForm.vue";
import { useRoute } from "vue-router";

const route = useRoute()
const id = route.params.id

</script>
 
<template>
    <Header className="flex justify-center py-8">Edit user of id {{ id }}</Header>
    <UserEditForm />
</template>
 
<style scoped></style>