<script setup>
import { computed, inject } from 'vue';

const props = defineProps({
    label: { type: String, required: false },
    subLabel: { type: String, required: false },
    field: { type: String, required: false },
    modelValue: { type: String },
    className: { type: String, required: String },
    placeholder: String,
    labelType: String,
})
defineEmits(['update'])

const errors = inject("errors")
const error = computed(() => errors?.value?.filter((error) => error.field === props.field).map((error) => error.errorMessage))
const isError = computed(() => error.value?.length > 0)
</script>
<template>
    <div class="flex flex-col w-full max-w-xl">
        <label class="label justify-start gap-x-2">
            {{label}}
            <span class="text-secondary" :v-show="subLabel">{{subLabel}}</span>
            <span class="text-error" v-if="isError">{{error.join(' ')}}</span>
        </label>
        <input :type="labelType" :placeholder="placeholder"
            :value="modelValue" 
            @input="$emit('update', $event.target.value)" 
            class="input input-bordered rounded-lg w-full max-w-xl " 
            :class="`${isError? 'border-error' : ''} ${className}`" />
    </div>
</template>