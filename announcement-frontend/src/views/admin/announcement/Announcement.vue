<script setup>
import { onBeforeMount, ref } from 'vue';
import { getAdminAnnouncement,deleteAnnouncement } from '@/assets/data/dataHandler.js';
import Table from "@/assets/components/table/Table.vue"
import Button from '@/assets/components/form/Button.vue';
import Header from "@/assets/components/text/Header.vue"
import Timezone from "@/assets/components/text/Timezone.vue"
import { modes,useAnnounces } from '@/assets/stores/useAnnounces';
import Pagination from '@/assets/components/Pagination.vue';
import ModalButton from '@/assets/components/modal/ModalButton.vue';
import Modal from '@/assets/components/modal/Modal.vue';
import { mdiAlertCircleOutline } from '@mdi/js';
import { humanizeDate } from '@/assets/utils/dateUtils';
import { isAdmin } from '@/assets/data/tokenStorage';
import Loading from "vue-loading-overlay";

const loaded = ref(false)
const announces = useAnnounces()
const announcement = ref([])
const sections = ref(["Id", "Title", "Publish Date", "Close Date", "Display", "Category", "Owner", "Action"])

const fetch = async () => {
  announcement.value = await getAdminAnnouncement(modes.ADMIN, announces.getPage() -1, 0)
  loaded.value = true
  if (!isAdmin()) sections.value = sections.value.filter((section) => section !== "Owner")
}
onBeforeMount(async () => { await fetch() })


const announcementDelete = async (id) => {
  announcement.value.content = announcement.value.content.filter((announce) => announce.id !== id)
  await deleteAnnouncement(id)
} 

const announcementEditRoute = (id) => `/admin/announcement/${id}/details`
</script>
<template>
  <loading :active="!loaded"
           :can-cancel="false"
           :is-full-page="false"/>
  <div v-if="loaded">
    <div class="flex max-lg:flex-col items-center justify-between py-6">
      <Header>Announcement Table</Header>
      <Timezone />
    </div>
    <Table :createPath="'/admin/announcement/add'"  :head="sections" :body="announcement?.content" emptyText="No Announcement">
      <template #column="{ items,index }">
        <td>{{ index+1 }}</td>
        <td>{{ items.announcementTitle }}</td>
        <td>{{ humanizeDate(items.publishDate) }}</td>
        <td>{{ humanizeDate(items.closeDate) }}</td>
        <td >{{ items.announcementDisplay }}</td>
        <td>{{ items.announcementCategory }}</td>
        <td v-if="isAdmin()">{{ items.announcementOwner }}</td>
      </template>
      <template #action="{ id }">
        <Button name="Edit" :to="announcementEditRoute(id)"  class=" bg-[#C1A696] px-6 hover:bg-[#E4B79D]"/>
        <ModalButton :modal-id="`annDeleteConfirm-${id}`" name="Delete" class-name="bg-error hover:bg-red-500 px-6"/>
        <Modal :modal-id="`annDeleteConfirm-${id}`"
               @confirm="() => announcementDelete(id)" :icon="mdiAlertCircleOutline"
               :title="`Do you want to delete announcement ${id}?`"
        />
      </template>
    </Table>
    <Pagination @fetch="fetch" :total-pages="announcement?.totalPages" :total-elements="announcement?.totalElements" class="float-right"/>
  </div>
</template>
<style scoped></style>
