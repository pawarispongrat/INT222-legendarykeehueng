<script setup>
import { computed } from 'vue';
const props = defineProps({
    label: { type: String, required: false },
    type: { type: String, required: false, default: "text" },
    subLabel: { type: String, required: false },
    required: { type: Boolean, default: false },
    // field: { type: String, required: false },
    modelValue: { type: String },
    className: { type: String, required: false },
    headerClassName: { type: String, required: false },
    placeholder: String,
    errors: { type: Array, default: [] , required: false },
    errorClassName: { type: String, required: false },
    min: { type: Number, required: false },
    max: { type: Number, required: false }
})
const emits = defineEmits(['update:modelValue'])

// const errors = inject("errors")
// const error = computed(() => errors?.value?.filter((error) => error.field === props.field).map((error) => error.errorMessage))
const isError = computed(() => props.errors?.length > 0)
const handleInput = (event) => {
    let input = event.target.value
    if (!/\S/.test(input)) input = input.trim()
    event.target.value = input
    emits('update:modelValue', input)
}
</script>
<template>
    <div class="flex flex-col w-full max-w-xl" :class="headerClassName">
        <label class="label justify-start gap-x-2">
            {{label}}
            <span class="text-secondary" :v-show="subLabel">{{subLabel}}</span>
            <span :class="`text-error ${errorClassName}`" v-if="isError">{{errors[0]}}</span>
        </label>
        <input :type="type" :placeholder="placeholder" :minlength="min" :maxlength="max"
            :value="modelValue" 
            @input="handleInput" 
            class="input font input-bordered rounded-lg w-full" 
            :class="`${isError? 'border-error' : ''} ${className}`" :required="required" />
    </div>
</template>