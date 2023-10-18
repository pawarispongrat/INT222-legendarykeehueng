<script setup>
const props = defineProps({
    className: { type: String, required: false }
})
</script>
<template>
    <h1 class="px-1 text-2xl font-bold text-start text-accent-content" :class="className">
        <slot></slot>
    </h1>
</template>