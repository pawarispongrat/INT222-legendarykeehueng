<script setup>
import Header from "@/assets/components/text/Header.vue";
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";
import { getAnnouncementById, isLoaded } from "@/assets/data/dataHandler.js";
import { formatDate } from "@/assets/utils/dateUtils";
import { displays } from "@/assets/data/announcement";
import Loading from "vue-loading-overlay";
const loaded = ref(false);
const announcement = ref("");
onBeforeMount(async () => {
    const route = useRoute();
    announcement.value = await getAnnouncementById(route.params.id,true);
    announcement.value = announcement.value?.announcementDisplay === displays.N ? null : announcement.value
    loaded.value = isLoaded(announcement.value,true)
})
</script>
<template>
    <loading :active="!loaded" :can-cancel="false" :is-full-page="false"/>
    <div v-if="loaded" class="h-screen w-screen flex flex-col justify-center items-center ann-item">
        <Header class="p-8"> User Details</Header>
        <div class="overflow-hidden bg-base-100 max-w-2xl rounded-md">
            <p class="font-bold text-xl text-gray-100 py-5 w-full px-4 bg-[#C1A696] ann-title">
                {{ announcement.announcementTitle }}
            </p>
            <p class="px-6 py-5 text-error">
                Closed on: <span class="ann-close-date">{{ formatDate(announcement.closeDate) }}</span>
            </p>
            <p  class="text-gray-500 mb-4 kanit-light px-6 ann-description" v-html="announcement.announcementDescription">

            </p>
            <div class="px-5 pt-4 pb-5 rounded-lg">
                <span class="inline-block px-3 py-2 text-sm mr-2 text-[#FAA497] bg-[#FAA497] bg-opacity-20 rounded-md ann-category">
                    {{ announcement.announcementCategory }}
                </span>
                <router-link
                    class="inline-block btn-outline btn-error float-right px-6 py-2 text-sm border rounded-md ann-button"
                    to="/announcement">Back</router-link>
            </div>
        </div>
    </div>
</template>
<style scoped></style>
