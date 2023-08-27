<script setup>
import { computed, inject } from 'vue';


const props = defineProps({
    label: { type: String, required: false },
    isFull: { type: Boolean, required: false },
    field: { type: String, required: false },
    select: { type: String ,required: false },
    options: Array
})
defineEmits(["update"])

const errors = inject("errors")
const error = computed(() => errors?.value?.filter((error) => error.field === props.field))
const isError = computed(() => error.value?.length > 0)
</script>
<template>
    <div :class="isFull && 'w-full'">
        <label class="label justify-start gap-x-2">
            {{label}}
            <span class="text-error" v-if="isError">{{error.join(' ')}}</span>
        </label>
        <select id="namePrefix" 
            class="input input-bordered text-md px-4 h-[3rem] rounded-lg" 
            :class="`${isFull && 'w-full'} ${isError ? 'border-error' : ''}`"
             @change="$emit('update',$event.target.value)">
            <option v-for="(option,index) in options" :key="index" :value="index" :selected="option === select">{{option}}</option>
        </select>
    </div>
</template>