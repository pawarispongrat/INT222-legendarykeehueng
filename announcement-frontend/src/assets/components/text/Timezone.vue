<script setup>
import { TIMEZONE } from '@/assets/utils/dateUtils';
const props = defineProps({
    text: { type: String, default: "Date/Time shown in Timezone:"}
})

</script>
<template>
    <h2 class="text-base font-bold ann-timezone">
       {{ text }} <span class="kanit-light text-base-content">{{ TIMEZONE }}</span>
    </h2>
</template>