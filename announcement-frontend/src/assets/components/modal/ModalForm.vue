<script setup>
import SvgIcon from '@jamescoyle/vue-icon';
import { useModal } from '@/assets/stores/useModal.js';
import { Teleport, ref } from 'vue';
import Input from '../form/Input.vue';


const { isOpen, setOpen,setModal } = useModal()
const props = defineProps({
    modalId: { type: String, default: false },
    icon: String,
    name: String,
    option: String,
    placeholder: String,
    isSlot: { type: Boolean, default: false },
    categories: { type: Array }
})
const input = ref("")

const selectedOptions = ref([]);

const isSelected = (index) => selectedOptions.value.includes(index);
const toggleSelection = (index) => {
    const indexInSelectedOptions = selectedOptions.value.indexOf(index);
    if (indexInSelectedOptions === -1) {
        selectedOptions.value.push(index);
    } else {
        selectedOptions.value.splice(indexInSelectedOptions, 1);
    }
};
// onBeforeMount(() => setModal(props.modalId))
const emit = defineEmits(["confirm"])

// const test = () => console.log(selectedOptions.value);
</script>
<template>
    <Teleport to="#modals">
        <div v-if="isOpen(modalId)" class="relative z-10">
            <div class="fixed inset-0 z-10 bg-gray-700 bg-opacity-20  overflow-y-auto">
                <div class="flex min-h-full justify-center p-4 text-center items-center">
                    <div
                        class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all w-full max-w-lg">
                        <div class="bg-white p-12 space-y-4">

                            <Input :label="name" :placeholder="placeholder" class="font" :required="true"
                                v-model.trim="input" :max="45" />
                            <div class="form-control">
                                <p class="font-bold">{{ option }}</p>
                                <label class="cursor-pointer label" v-for="(category, index) in categories" :key="index">
                                    <div>
                                        <span class="font mr-3">{{ category }}</span>
                                        <input type="checkbox" :checked="isSelected(index + 1)" class="checkbox checkbox-error"
                                            @change="toggleSelection(index + 1)" />
                                    </div>
                                </label>
                            </div>
                        </div>
                        <div class="bg-slate-100 p-3 flex justify-end max-lg:flex-col gap-x-4 gap-y-4">
                            <button v-if="!isSlot" type="button" @click=" $emit('confirm', input,selectedOptions),setModal(modalId)"
                                class="btn btn-error text-white hover:bg-red-500">Confirm</button>
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
    </Teleport>
</template>