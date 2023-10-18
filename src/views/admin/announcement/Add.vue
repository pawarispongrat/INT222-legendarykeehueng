<script setup>
import Header from "@/assets/components/text/Header.vue";
import AnnouncementForm from "@/assets/components/AnnouncementForm.vue";
import { provide, ref } from "vue";
import { createAnnouncement } from "@/assets/data/dataHandler.js"
import Announcement from "../../../assets/data/announcement";
import { useRouter } from "vue-router";

// const announcement = ref(new Announcement())
// const onCreate = async () => {
//     const json = await createAnnouncement(announcement.value)
//     console.log(json);
//     if (!json?.status) router.push("/admin/announcement/")
//     else errors.value = json.detail
// }
// const errors = ref()
// provide("errors",errors)
const router = useRouter()
const announcement = ref(new Announcement())
const create = async (announcement,validate) => {
    if (validate) {
        const json = await createAnnouncement(announcement.toJSON())
        if (!json?.status) router.push("/admin/announcement/")
    }
}

</script>

<template>
    <div class="flex flex-col justify-center items-center">
        <Header class-name="py-8">Add Announcement</Header>
        <!-- <AnnouncementForm :announcement="announcement" @submit="onCreate" /> -->
        <AnnouncementForm :announcement="announcement" @submit="create" submit-text="Submit"/>
    </div>
</template>
 
<style scoped></style>