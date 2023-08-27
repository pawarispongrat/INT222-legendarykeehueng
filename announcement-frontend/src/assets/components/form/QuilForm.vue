<script setup>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import { computed, inject } from 'vue';

const props = defineProps({
    label: { type: String, required: false },
    field: { type: String, required: false },
    modelValue: { type: String }
})
defineEmits(["update"])

const errors = inject("errors")
const error = computed(() => errors?.value?.filter((error) => error.field === props.field).map((error) => error.errorMessage))
const isError = computed(() => error.value?.length > 0)
</script>
<template>
    <label class="label justify-start gap-x-2">
        {{ label }}
        <span class="text-error" v-if="isError">{{ error.join(' ') }}</span>
    </label>
    <QuillEditor theme="snow" toolbar="full" 
            :content="modelValue"
            class=" bg-white ann-description"
            contentType="html" 
    />
</template>