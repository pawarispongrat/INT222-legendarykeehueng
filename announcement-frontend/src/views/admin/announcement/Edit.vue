<script setup>
import Header from "@/assets/components/text/Header.vue";
import AnnouncementForm from "@/assets/components/AnnouncementForm.vue";
import Announcement from "@/assets/data/announcement.js"
import { ref, onBeforeMount, watch } from "vue";
import { getAnnouncementById, isLoaded, putAnnouncement } from "@/assets/data/dataHandler.js"
import { useRoute,useRouter } from 'vue-router';
import Loading from "vue-loading-overlay";

const loaded = ref(true)
const router = useRouter()
const announcement = ref('')
onBeforeMount(async () => {
    const route = useRoute()
    announcement.value = await getAnnouncementById(route.params.id)
    if (await isLoaded(announcement.value)) {
        announcement.value = new Announcement().fromJSON(announcement.value)
    }
})
// EDITED
let edited = ref(-1)
watch([announcement], async () => { edited.value++ }, { deep: true })
const edit = async (announcement,validate) => {
    if (validate) {
    loaded.value = false
    await putAnnouncement(announcement.toJSON())
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
        <Header class-name="py-8">Edit Announcement Detail</Header>
        <AnnouncementForm :announcement="announcement" @submit="edit" submit-text="Edit" :disabled-submit="edited <= 0"/>
    </div>
</template>
 
<style scoped></style>