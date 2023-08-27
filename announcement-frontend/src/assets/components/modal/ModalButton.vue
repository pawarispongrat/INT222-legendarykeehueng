<script setup>
import { useModal } from '@/assets/stores/useModal';

const { setOpen,isOpen, getModals } = useModal()
const props = defineProps({
    modalId: String,
    className: { required: false, type: String },
    name: String
})
const open = () => {
    setOpen(props.modalId)
}
const CLASSES = "inline-flex items-center border-opacity-20 justify-center rounded-lg gap-x-2 max-md:w-full p-2 text-base-100 transition ease-in-out"
</script>
<template>
        <button :class="`${CLASSES} ${className}`" @click="open">{{ name }}</button>
</template>