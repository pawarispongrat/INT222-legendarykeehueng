<script setup>
import { computed, inject } from 'vue';


const props = defineProps({
    label: { type: String, required: false },
    isFull: { type: Boolean, required: false },
    // field: { type: String, required: false },
    select: { type: String ,required: false },
    className: { type: String, required: String },
    options: Array,
    errors: { type: Array, default: [] },
})
defineEmits(["update"])

// const errors = inject("errors")
// const error = computed(() => errors?.value?.filter((error) => error.field === props.field))
const isError = computed(() => errors.value?.length > 0)
</script>
<template>
    <div :class="isFull && 'w-full'">
        <label class="label justify-start gap-x-2">
            {{label}}
            <span class="text-error" v-if="isError">{{error.join(' ')}}</span>
        </label>
        <select class="input input-bordered text-md px-4 h-[3rem] rounded-lg" 
            :class="`${isFull && 'w-full'} ${isError ? 'border-error' : ''} ${className}`"
             @change="$emit('update',$event.target.value)">
            <option v-for="(option,index) in options" :key="index" :value="option" :selected="option === select">{{option}}</option>
        </select>
    </div>
</template>