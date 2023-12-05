<script setup>

import SvgIcon from "@jamescoyle/vue-icon";

defineProps({
  icon: String,
  iconClass: String,
  divClass: String,
  tip: String,
})
defineEmits(["open"])
</script>

<template>
  <button @click="$emit('open',$event)" :data-tip="tip" :class="`tooltip absolute h-16 w-16 bg-white rounded-[3rem] shadow-2xl flex items-center justify-center transition-transform transform-gpu hover:scale-125  ${divClass}`">
    <svg-icon v-if="icon" type="mdi" :class="iconClass" :size="46" :path="icon"></svg-icon>
  </button>
</template>

<style scoped>

</style>