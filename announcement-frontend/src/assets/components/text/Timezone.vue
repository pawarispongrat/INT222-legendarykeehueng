<script setup>
import { TIMEZONE } from '@/assets/utils/dateUtils';
const props = defineProps({
    text: { type: String, default: "Date/Time shown in Timezone:" },
    headerClass: String,
    bodyClass: String
})

</script>
<template>
    <h2 :class="`text-base font-bold ann-timezone ${headerClass}`">
       {{ text }} <span :class="`kanit-light text-base-content ${bodyClass}`">{{ TIMEZONE }}</span>
    </h2>
</template>