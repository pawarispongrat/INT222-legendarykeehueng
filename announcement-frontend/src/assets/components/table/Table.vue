<script setup>
import Button from "../form/Button.vue"
import { mdiFileDocumentPlusOutline, mdiMagnify, mdiIsland } from "@mdi/js"
import SvgIcon from '@jamescoyle/vue-icon';
import { isIsoDate, humanizeDate } from "../../utils/dateUtils";

const props = defineProps({
    head: Array,
    body: Array,
    bodyClass: { required: false, type: String },
    useHeader: { type: Boolean, default: true },
    useAction: { type: Boolean, default: true },
    emptyText: String,
    createPath: String,
})

const emits = defineEmits(["sectionClick"])
</script>
<template>
    <div class="space-y-5">
        <div class="flex flex-wrap text-slate-400 md:justify-between max-md:space-y-5" v-if="useHeader">
            <div class="inline-flex items-center gap-x-2 input input-bordered w-96 max-md:w-full ">
                <svg-icon type="mdi" :path="mdiMagnify" :size="24" />
                <input type="text" placeholder="ต้องการหาอะไร เช่น นาย..., อินทร..." class="w-full" />
            </div>
            <Button :to="createPath" :icon="mdiFileDocumentPlusOutline" name="ADD" class-name="ann-button bg-[#E2C593] px-5 py-3 hover:bg-[#E68572]" />

        </div>
        <div class="overflow-x-auto">
            <table class="table table-auto text-base z-1 w-full">
                <thead class="text-white h-12">
                    <tr>
                        <td v-for="(section, index) in head" :key="index" class="bg-[#E2C593] text-base text-center">
                            {{ section }}
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(items, index) of body" :key="index" class="text-center ann-item" :class="bodyClass" @click="$emit('sectionClick',items.id)">
                        <slot name="column" :items="items,index"></slot>
                        <td v-if="useAction" class="flex gap-x-3 justify-center">
                            <slot name="action" :id="items.id"></slot>
                            
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
        <div v-if="body?.length === 0" class="flex flex-col items-center justify-center pt-40 space-y-6 text-slate-400">
            <svg-icon type="mdi" :path="mdiIsland" :size="128" />
            <div>{{ emptyText }}</div>
        </div>
    </div>
</template>