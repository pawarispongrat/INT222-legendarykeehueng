<script setup>
import SvgIcon from '@jamescoyle/vue-icon';
import { useModal } from '@/assets/stores/useModal.js';
import { Teleport } from 'vue';
import { onBeforeMount } from 'vue';

const { isOpen, setOpen } = useModal()
const props = defineProps({
    modalId: { type: String, default: false },
    icon: String,
    isSlot: { type: Boolean, default: false },
    title: { type: String, required: false },
    body: { type: String, required: false },
    confirmText: { type: String, default: "Confirm"}
})
// onBeforeMount(() => setModal(props.modalId))
defineEmits(["confirm"])
</script>
<template>
    <Teleport to="#modals">
        <div v-if="isOpen(modalId)" class="relative z-10">
            <div class="fixed inset-0 z-10 bg-gray-700 bg-opacity-20  overflow-y-auto">
                <div class="flex min-h-full justify-center p-4 text-center items-center">
                    <div
                        class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all w-full max-w-lg">
                        <div class="bg-white p-12 space-y-4">
                            <h3 class="flex items-center gap-x-2" id="modal-title">
                                <svg-icon v-if="icon" type="mdi" :path="icon" :size="26" />
                                {{ title }}
                            </h3>
                            <p v-show="body" class="text-slate-500">{{ body }}</p>
                        </div>
                        <div class="bg-slate-100 p-3 flex justify-end max-lg:flex-col gap-x-4 gap-y-4">
                            <button v-if="!isSlot" type="button" @click="setOpen(modalId),$emit('confirm', $event)"
                                class="btn btn-error text-white hover:bg-red-500">{{ confirmText }}</button>
                            <button v-if="!isSlot" type="button" @click="setOpen(modalId)"
                                class="btn btn-outline">Cancel</button>
                            <div v-else>
                                <slot></slot>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</Teleport></template>