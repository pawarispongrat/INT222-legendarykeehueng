<script setup>
import Header from "@/assets/components/text/Header.vue";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";
import { getUserAnnouncementById, isLoaded,getFileById } from "@/assets/data/dataHandler.js";
import { formatDate } from "@/assets/utils/dateUtils";
import { displays } from "@/assets/data/announcement";
import Loading from "vue-loading-overlay";
import FileViewer from "./FileViewer.vue";
const loaded = ref(false);
const announcement = ref("");
const files = ref([])
onBeforeMount(async () => {
    const route = useRoute();
    announcement.value = await getUserAnnouncementById(route.params.id,true);
    files.value = await getFileById(route.params.id)
    announcement.value = announcement.value?.announcementDisplay === displays.N ? null : announcement.value
    loaded.value = await isLoaded(announcement.value,true)
})

</script>
<template>
    <loading :active="!loaded" :can-cancel="false" :is-full-page="false"/>
    <div v-if="loaded" class=" h-screen w-screen flex flex-col items-center ann-item p-8">
        <Header class="p-8"> Announcement Details</Header>
        <div class="overflow-auto bg-base-100 w-full max-w-[80rem] rounded-md">
            <div class="flex items-center justify-between font-bold text-xl text-gray-100 py-5 w-full px-4 bg-[#C1A696] ann-title">
                {{ announcement.announcementTitle }}
              <router-link
                  class="btn btn-error text-sm border rounded-md  px-6 text-white hover:bg-red-500 hover:border-red-500"
                  to="/announcement">Back</router-link>
            </div>

            <p class="px-6 py-5 text-error">
                Closed on: <span class="ann-close-date">{{ formatDate(announcement.closeDate) }}</span>
            </p>
            <p  class="text-gray-500 mb-4 kanit-light px-6 ann-description" v-html="announcement.announcementDescription">
            </p>
            <div class="px-5 pt-4 pb-5 rounded-lg">

                <span class="inline-block px-3 py-2 text-sm mr-2 text-[#FAA497] bg-[#FAA497] bg-opacity-20 rounded-md ann-category">
                    {{ announcement.announcementCategory }}
                </span>
       
                <FileViewer v-for="(file, index) in files" :key="index"  :file="file" />
            </div>
        </div>
    </div>
</template>
<style scoped></style>
