<script setup>
import { ref } from 'vue';
import { getUser,deleteUser } from '@/assets/data/data-handler.js';
import Table from "@/assets/components/table/Table.vue"
import Button from '@/assets/components/form/Button.vue';
import Header from "@/assets/components/text/Header.vue"
import Timezone from "@/assets/components/text/Timezone.vue"
import Modal from '@/assets/components/modal/Modal.vue';
import ModalButton from '@/assets/components/modal/ModalButton.vue';
import { mdiAlertCircleOutline } from '@mdi/js';
import { Teleport } from 'vue';
import { humanizeDate } from '../../../assets/utils/dateUtils';

const users = ref([])
const fetch = async () => { users.value = await getUser() }
await fetch()
const userDelete = async (id) => {
  users.value = users.value.filter((user) => user.id !== id)
  await deleteUser(id)
} 
const userSections = ["No", "Username", "Name", "Email", "Role", "CreatedOn", "UpdatedOn", "Action"]
const userEditRoute = (id) => `/admin/user/${id}/edit`
</script>
<template>
  <div class="flex max-lg:flex-col items-center justify-between py-6">
    <Header>User Table</Header>
    <Timezone />
  </div>
  <Table create-path="/admin/user/add"  :head="userSections" :body="users" emptyText="No User">
    <template #column="{ items,index }">
        <td>{{ index+1 }}</td>
        <td>{{ items.username }}</td>
        <td>{{ items.name }}</td>
        <td>{{ items.email }}</td>
        <td>{{ items.role }}</td>
        <td>{{ humanizeDate(items.createdOn) }}</td>
        <td>{{ humanizeDate(items.updatedOn) }}</td>
      </template>
      <template #action="{ id }">
        <Button name="Edit" :to="userEditRoute(id)"  class="bg-blue-700 px-6 hover:bg-blue-800"/>
        <ModalButton :modal-id="`userDeleteConfirm-${id}`" name="Delete" class-name="bg-error hover:bg-red-500 px-6"/>
        <Modal :modal-id="`userDeleteConfirm-${id}`" 
            @confirm="() => userDelete(id)" :icon="mdiAlertCircleOutline" 
            :title="`Do you want to delete user ${id}?`"
        />
    </template>
  </Table>
</template>
 
<style scoped></style>