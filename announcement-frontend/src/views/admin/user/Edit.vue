<script setup>
import Header from "@/assets/components/text/Header.vue";
import UserForm from '@/assets/components/form/list/UserForm.vue';
import { getUserById, putUser, isLoaded } from '@/assets/data/data-handler';
import { provide, ref, watch } from "vue";
import { useRoute, useRouter } from 'vue-router';


const route = useRoute()
const router = useRouter()
const user = ref('')
const errors = ref()

const id = route.params.id
const fetchUserId = async () => {
    user.value = await getUserById(id)
    isLoaded(user.value, "/admin/user")
}

const onSave = async () => {
    const json = await putUser(user.value)
    if (!json?.status) router.push("/admin/user/")
    else errors.value = json.detail
}

let edited = ref(-1)
watch([user], async () => { edited.value++ }, { deep: true })
provide("errors", errors)
await fetchUserId()
</script>
 
<template>
    <Header className="flex justify-center py-8">Edit user of id {{ id }}</Header>
    <UserForm :user="user" @submit="onSave" :disabled-save="edited <= 0" />
</template>
 
<style scoped></style>