<script setup>
const props = defineProps({
    label: { type: String, required: false },
    isFull: { type: Boolean, required: false },
    options: Array
})
</script>
<template>
    <div :class="isFull && 'w-full'">
        <label class="label">{{label}}</label>
        <select id="namePrefix" 
            class="input input-bordered text-md px-4 h-[3rem] rounded-lg" 
            :class="isFull && 'w-full'">
            <option v-for="(option,index) in options" :key={index} :value="option">{{option}}</option>
        </select>
    </div>
</template>