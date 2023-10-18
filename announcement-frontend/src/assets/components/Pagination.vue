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

</script>
<template>
    <div class="btn-group flex-wrap mt-8" v-if="totalElements > 5">
        <button class="btn bg-blue-700 border-0 hover:bg-blue-800 shadow-mdann-page-prev disabled:bg-slate-500 disabled:text-white"
            :disabled="announces.getPage() === 1" @click="previous()">
            <svg-icon type="mdi" :path="mdiChevronLeft" :size="24"/>
        </button>
        <button class="btn bg-base-100 border-0 shadow-md text-base-content w-12 hover:bg-blue-700 hover:text-white"
            v-for="[index, i] of pages().entries()" @click="number(i)"
            :class="`disabled:bg-blue-700 disabled:text-white ann-page-${index}`" :disabled="selectedPage(i)">{{ i }}
        </button>
        <button class="btn bg-blue-700 border-0 hover:bg-blue-800 shadow-md text-gray-100 ann-page-next disabled:bg-gray"
            :disabled="announces.getPage() === totalPages" @click="next()">
            <svg-icon type="mdi" :path="mdiChevronRight" :size="24"/>
        </button>
    </div>
</template>