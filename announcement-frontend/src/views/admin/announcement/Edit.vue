<script setup>
import Header from "@/assets/components/text/Header.vue";
import AnnouncementForm from "@/assets/components/AnnouncementForm.vue";
import Announcement from "@/assets/data/announcement.js"
import { ref, onBeforeMount, watch } from "vue";
import { getAnnouncementById, isLoaded, putAnnouncement,getFileById, updateFile } from "@/assets/data/dataHandler.js"
import FetchHandler from "@/assets/data/fetchHandler.js"
import { useRoute,useRouter } from 'vue-router';
import Loading from "vue-loading-overlay";

const loaded = ref(true)
const router = useRouter()
const announcement = ref('')
const files = ref([])

async function getFileFromUrl(url,name){
  const response = await new FetchHandler(url).authorize().response()
  const data = await response.blob()
  return new File([data], name, { type: data.type })
}


onBeforeMount(async () => {
    const route = useRoute()
    loaded.value = false
    const fetchAnnouncement = await getAnnouncementById(route.params.id)
    const filePayload = await getFileById(route.params.id) ?? []
    for (const fileData of filePayload) {
        if (fileData?.fileUrl === undefined) break
        const file = await getFileFromUrl(fileData.fileUrl,fileData.fileName)
        files.value.push(file)
        edited.value--
    }
    if (await isLoaded(fetchAnnouncement)) {
        announcement.value = new Announcement().fromJSON(fetchAnnouncement)
        loaded.value = true
    }

    
})

// EDITED
let edited = ref(-1)
watch([announcement], async () => { edited.value++; }, { deep: true })
watch([files], async () => { edited.value++; }, { deep: true })

const edit = async (announcement,validate,file) => {
    if (validate) {
        loaded.value = false
        await putAnnouncement(announcement.toJSON())
        await updateFile( announcement.id, file )
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
        <AnnouncementForm :announcement="announcement" :files="files" @submit="edit" submit-text="Edit" :disabled-submit="edited <= 0"/>
    </div>
</template>
 
<style scoped></style>