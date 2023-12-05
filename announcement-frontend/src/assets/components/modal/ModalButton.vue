<script setup>
import { useModal } from '@/assets/stores/useModal';
import SvgIcon from '@jamescoyle/vue-icon'

const { setOpen } = useModal()
const props = defineProps({
    modalId: String,
    className: { required: false, type: String },
    name: { required: false, type: String },
    path: { required: false, type: String },
    size: { required: false, type: String },
    type: { required: false, type: String },
})
const open = () => {
    setOpen(props.modalId)
}
const CLASSES = "  inline-flex items-center border-opacity-20 justify-center rounded-lg gap-x-2 max-md:w-full p-2 text-base-100 transition ease-in-out  "
</script>
<template>
        <button :class="`${CLASSES} ${className}`" @click="open"><svg-icon v-if="type !== undefined" :type="`${type}`"  :path="`${path}`"/>{{ name }}</button>
</template>