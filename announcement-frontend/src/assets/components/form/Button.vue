<script setup>
import { RouterLink } from 'vue-router';
const props = defineProps({
    name: String,
    to: { type: String, required: false },
    onClick: { required: false },
    className: { required: false }
})

const CLASSES = `inline-flex items-center border-opacity-20 justify-center rounded-lg gap-x-2 max-md:w-full p-3 text-base-100 transition ease-in-out`
</script>
<template>
    <RouterLink v-if="to" :to="to" :onClick="onClick" :class="`${CLASSES} ${className}`">
        <span>{{name}}</span>
    </RouterLink>
    <button v-else :onClick="onClick" :class="`${CLASSES} ${className}`">
        <span>{{name}}</span>
    </button>
</template>