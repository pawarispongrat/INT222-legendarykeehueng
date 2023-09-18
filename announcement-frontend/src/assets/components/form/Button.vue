<script setup>
import { RouterLink } from 'vue-router';
import SvgIcon from '@jamescoyle/vue-icon';

defineEmits(["click"])

const props = defineProps({
    name: String,
    type: { type: String, required: false },
    icon: { type: String, required: false },
    to: { type: String, required: false },
    className: { required: false },
    disabled: { type: Boolean , default: false}
})

const CLASSES = `inline-flex items-center border-opacity-20 justify-center rounded-lg gap-x-2 max-md:w-full p-2 text-base-100 transition ease-in-out disabled:bg-slate-500`
</script>
<template>
    <RouterLink v-if="to" @click="$emit('click',$event)" :to="to" :class="`${CLASSES} ${className}`" :disabled="disabled">
        <svg-icon v-if="icon" type="mdi" :path="icon" :size="24"/>
        <span>{{name}}</span>
    </RouterLink>
    <button :type="type" v-else @click="$emit('click',$event)" :class="`${CLASSES} ${className}`" :disabled="disabled">
        <svg-icon v-if="icon" type="mdi" :path="icon" :size="24"/>
        <span>{{name}}</span>
    </button>
</template>