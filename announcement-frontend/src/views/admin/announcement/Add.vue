<script setup>
import Header from "@/assets/components/text/Header.vue";
import AnnouncementForm from "@/assets/components/AnnouncementForm.vue";
import { provide, ref } from "vue";
import { createAnnouncement,uploadFile } from "@/assets/data/dataHandler.js"
import Announcement from "../../../assets/data/announcement";
import { useRouter } from "vue-router";
import Loading from "vue-loading-overlay";

const loaded = ref(true)
const files = ref([])

const router = useRouter()
const announcement = ref(new Announcement())
const create = async (announcement,validate,file) => {
    if (validate) {
        loaded.value = false
        const id = await createAnnouncement(announcement.toJSON())
        await uploadFile(id, file)
        loaded.value = true
        router.push("/admin/announcement/")
    }
}

</script>

<template>
      <loading :active="!loaded"
           :can-cancel="false"
           :is-full-page="false"/>
    <div class="flex flex-col justify-center items-center">
        <Header class-name="py-8">Add Announcement</Header>
        <!-- <AnnouncementForm :announcement="announcement" @submit="onCreate" /> -->
        <AnnouncementForm :announcement="announcement" :files="files" @submit="create" submit-text="Submit"/>
    </div>
</template>
 
<style scoped></style>