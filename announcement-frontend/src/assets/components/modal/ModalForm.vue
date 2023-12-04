<script setup>
import SvgIcon from '@jamescoyle/vue-icon';
import { useModal } from '@/assets/stores/useModal.js';
import { Teleport, ref } from 'vue';
import Input from '../form/Input.vue';


const { isOpen, setOpen, setModal } = useModal()
const props = defineProps({
    modalId: { type: String, default: false },
    icon: String,
    name: String,
    option: String,
    status: Object,
    placeholder: String,
    error: { type: String, default: null },
    isSlot: { type: Boolean, default: false },
    isOption: { type: Boolean, default: false },
    categories: { type: Array },
    open: { type: Boolean, default: false }
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

const getCategoryById = (id) => {
    const index = id - 1;
    return props.categories[index];
}
// onBeforeMount(() => setModal(props.modalId))
const emit = defineEmits(["confirm"])

</script>
<template>
    <Teleport to="#modals">
        <div v-if="isOpen(modalId)" class="relative z-10">
            <div class="fixed inset-0 z-10 bg-gray-700 bg-opacity-20  overflow-y-auto">
                <div class="flex min-h-full justify-center p-4 text-center items-center">
                    <div
                        class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all w-full max-w-lg">
                        <div class="bg-[#EFE2D7] p-12 space-y-4">
                            <Input v-if="name" :label="name" :placeholder="placeholder" :errors="error ? [error] : null" :required="true"
                                v-model.trim="input" :max="45" />
                            <div class="form-control ">
                                <p class="font-bold">[ {{ option }} ]</p>
                                <p v-if="status && status.exists" v-for="item in status.exists" :key="item.id">
                                    {{ item.exist ? `You are already Subscribe ${getCategoryById(item.id)}` : `You are
                                    Subscribe now ${getCategoryById(item.id)}` }}
                                </p>
                                <label v-if="isOption" class="cursor-pointer label" v-for="(category, index) in categories"
                                    :key="index">
                                    <div>
                                        <span class="font mr-3">{{ category }}</span>
                                        <input type="checkbox" :checked="isSelected(index + 1)"
                                            class="checkbox checkbox-error" @change="toggleSelection(index + 1)" />
                                    </div>
                                </label>
                            </div>
                        </div>
                        <div class="bg-slate-100 p-3 flex justify-end max-lg:flex-col gap-x-4 gap-y-4">
                            <button v-if="(open || (!isSlot && selectedOptions.length > 0 && input.length > 0))"
                                type="button" @click="$emit('confirm', input, selectedOptions)"
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