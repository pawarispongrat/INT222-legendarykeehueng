<script setup>
import { computed } from 'vue';
import { mdiChevronRight, mdiChevronLeft } from "@mdi/js"
import { useAnnounces } from '../stores/useAnnounces';
import SvgIcon from '@jamescoyle/vue-icon';
const announces = useAnnounces()
const props = defineProps({totalPages: { required: true} ,totalElements: {  required: true} })
const emits = defineEmits(["fetch"])
const pages = (size = 10) => computed(() => {
  const show = Math.min(size, props.totalPages);
  const min = Math.min(announces.getPage() - Math.floor(show/2) - Math.floor(size/2.5) , props.totalPages - show + 1)
  const first = Math.max(min,1);
  return [...Array(show)].map((_, i) => i + first);

}).value
const next = () => {
    announces.addPage()
    fetchPage()
}
const previous = () => {
    announces.substractPage()
    fetchPage()
}
const number = (selectPage) => {
    announces.setPage(selectPage)
    fetchPage()
}
const fetchPage = () => emits('fetch',announces.getPage())
const selectedPage = (pageIndex) => computed(() => pageIndex === announces.getPage()).value

const PRIMARY_BG_CLASSES = "bg-[#E2C593]"
const HOVER_PRIMARY_BG_CLASSES = "hover:bg-[#E2C593]"
const HOVER_SECONDARY_BG_CLASSES = "hover:bg-[#E4B79D]"
const DISABLED_PRIMARY_BG_CLASSES = "disabled:bg-[#E2C593]"
const DISABLED_NAVIGATE_CLASSES = "disabled:bg-[#cdc3ad]"
</script>
<template>
    <div class="btn-group flex-wrap mt-8" v-if="totalElements > 5">
        <button class="btn border-0 shadow-md ann-page-prev text-gray-100"
            :class="`${PRIMARY_BG_CLASSES} ${HOVER_SECONDARY_BG_CLASSES} ${DISABLED_NAVIGATE_CLASSES}`"
            :disabled="announces.getPage() === 1" @click="previous()">
            <svg-icon type="mdi" :path="mdiChevronLeft" :size="24"/>
        </button>
        <button class="btn bg-base-100 border-0 shadow-md text-base-content w-12 hover:text-white disabled:text-white"
            v-for="[index, i] of pages().entries()" @click="number(i)"
            :class="`${HOVER_PRIMARY_BG_CLASSES} ${DISABLED_PRIMARY_BG_CLASSES}  ann-page-${index}`" :disabled="selectedPage(i)">{{ i }}
        </button>
        <button class="btn border-0 shadow-md text-gray-100 ann-page-next"
            :class="`${PRIMARY_BG_CLASSES} ${HOVER_SECONDARY_BG_CLASSES} ${DISABLED_NAVIGATE_CLASSES}`"
            :disabled="announces.getPage() === totalPages" @click="next()">
            <svg-icon type="mdi" :path="mdiChevronRight" :size="24"/>
        </button>
    </div>
</template>