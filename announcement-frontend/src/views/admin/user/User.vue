<script setup>
import { onBeforeMount, ref } from 'vue';
import { getUser, deleteUser } from '@/assets/data/data-handler.js';
import Table from "@/assets/components/table/Table.vue"
import Button from '@/assets/components/form/Button.vue';
import Header from "@/assets/components/text/Header.vue"
import Timezone from "@/assets/components/text/Timezone.vue"
import EditUser from './EditUser.vue';
const users = ref([])
const fetch = async () => { users.value = await getUser() }
await fetch()

const userSections = ["No", "Username", "Name", "Email", "Role", "CreatedOn", "UpdatedOn", "Action"]
const userEditRoute = (id) => `/admin/user/${id}/edit`
</script>
<template>
  <div class="flex max-lg:flex-col items-center justify-between py-6">
    <Header>Users Table</Header>
    <Timezone />
  </div>
  <Table :head="userSections" :body="users" emptyText="No User">
    <template #action="{ id }">
        <Button name="Edit" :to="userEditRoute(id)"  class=" bg-blue-700 px-6 hover:bg-blue-800"/>
        <Button name="Delete" class="bg-error px-4 hover:bg-red-500"/>
    </template>
  </Table>
</template>
 
<style scoped></style>