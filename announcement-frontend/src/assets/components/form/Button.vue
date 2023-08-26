<script setup>
import { RouterLink } from 'vue-router';
import SvgIcon from '@jamescoyle/vue-icon';

const props = defineProps({
    name: String,
    icon: { type: String, required: false },
    to: { type: String, required: false },
    onClick: { required: false },
    className: { required: false }
})

const CLASSES = `inline-flex items-center border-opacity-20 justify-center rounded-lg gap-x-2 max-md:w-full p-2 text-base-100 transition ease-in-out`
</script>
<template>
    <RouterLink v-if="to" :to="to" :onClick="onClick" :class="`${CLASSES} ${className}`">
        <svg-icon v-if="icon" type="mdi" :path="icon" :size="24"/>
        <span>{{name}}</span>
    </RouterLink>
    <button v-else :onClick="onClick" :class="`${CLASSES} ${className}`">
        <svg-icon v-if="icon" type="mdi" :path="icon" :size="24"/>
        <span>{{name}}</span>
    </button>
</template>