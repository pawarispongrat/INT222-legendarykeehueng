<script setup>

import { getCurrentDate } from "@/assets/utils/dateUtils";
import { computed } from "vue";
const props = defineProps({ 
    dateText: String,
    date: String,time: String , 
    isClose: { type: Boolean ,default: false},
    validate: { type: Boolean, default: false},
    minDate: String,
    maxDate: String,
    errorText: String
})
defineEmits(["date","time"])

const computedMinDate = computed(() => props.isClose && props.minDate ? props.minDate : getCurrentDate())
</script>
<template>
    <div class="space-y-2 w-96">
        <div class="flex items-center pr-5">
            <p>{{ dateText }} <span v-show="!validate" class="text-sm text-red-500">{{ errorText }}</span></p>
        </div>
        <div class="flex justify-between">
            <div class="relative w-44">
                <input type="date" :value="date" @input="$emit('date',$event)" :min="computedMinDate" :max="maxDate"
                    class=" pl-8 border text-sm rounded-lg block w-full p-2.5 bg-gray-50 text-gray-900 border-gray-300 cursor-text" 
                    :class="isClose ? 'ann-close-date':'ann-publish-date',!validate ? 'outline outline-red-600' : ''">
            </div>
            <div class="relative w-44">
                <input type="time" :value="time" @input="$emit('time',$event)" 
                class=" pl-12 border text-sm rounded-lg block w-full p-2.5 bg-gray-50 text-gray-900 border-gray-300 cursor-text" 
                :class="isClose ? 'ann-close-time':'ann-publish-time',!validate ? 'outline outline-red-600' : ''" >
            </div>
    </div>                  
</div></template>
<style>

</style>