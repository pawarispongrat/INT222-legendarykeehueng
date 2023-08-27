<script setup>
import Button from "../form/Button.vue"
import { mdiFileDocumentPlusOutline, mdiMagnify, mdiIsland } from "@mdi/js"
import SvgIcon from '@jamescoyle/vue-icon';
import { isIsoDate, humanizeDate } from "../../utils/dateUtils";

const props = defineProps({
    head: Array,
    body: Array,
    useIndex: { type: Boolean , default: false },
    emptyText: String,
    createPath: String,
})
const computedDateBody = (value) => (isIsoDate(value) ? humanizeDate(value) : value) 
</script>
<template>
    <div class="space-y-5">
        <div class="flex flex-wrap text-slate-400 md:justify-between max-md:space-y-5">
            <div class="inline-flex items-center gap-x-2 input input-bordered w-96 max-md:w-full ">
                <svg-icon type="mdi" :path="mdiMagnify" :size="24" />
                <input type="text" placeholder="ต้องการหาอะไร เช่น นาย..., อินทร..." class="w-full" />
            </div>
            <Button :to="createPath" :icon="mdiFileDocumentPlusOutline" name="ADD" class-name="bg-blue-700 px-5 py-3 hover:bg-blue-800" />

        </div>
        <div class="overflow-x-auto">
            <table class="table table-auto text-base z-1 w-full">
                <thead class="text-white h-12">
                    <tr>
                        <td v-for="(section, index) in head" :key="index" class="bg-blue-700 text-base text-center">
                            {{ section }}
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(items, index) of body" :key="index">
                        <td v-for="(value, key, indexKey) in items" class="text-center" :key="key">
                            {{ useIndex && indexKey === 0 ? index+1 : (value || '-') }}
                        </td>
                        <td class="flex gap-x-3 justify-center">
                            <slot name="action" :id="items.id"></slot>
                            
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
        <div v-if="body.length === 0" class="flex flex-col items-center justify-center pt-40 space-y-6 text-slate-400">
            <svg-icon type="mdi" :path="mdiIsland" :size="128" />
            <div>{{ emptyText }}</div>
        </div>
    </div>
</template>