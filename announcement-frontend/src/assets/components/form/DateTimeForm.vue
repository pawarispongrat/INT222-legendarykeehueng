<script setup>
import { getCurrentDate } from "@/assets/utils/dateUtils";
import { computed, inject } from "vue";
const props = defineProps({
    label: String,
    date: String, time: String,
    field: { type: String, required: false },
    isClose: { type: Boolean, default: false },
    validate: { type: Boolean, default: false },
    minDate: String,
    maxDate: String,
    errorText: String
})
defineEmits(["date", "time"])

const computedMinDate = computed(() => props.isClose && props.minDate ? props.minDate : getCurrentDate())

const errors = inject("errors")
const error = computed(() => errors?.value?.filter((error) => error.field === props.field).map((error) => error.errorMessage))
const isError = computed(() => error.value?.length > 0)
</script>
<template>
    <div class="w-full">
        <label class="label justify-start gap-x-2">
            {{ label }}
            <span class="text-error" v-if="isError">{{ error.join(' ') }}</span>
        </label>
        <div class="flex gap-x-6 justify-between">
            <input type="date" :value="date" @input="$emit('date', $event)" :min="computedMinDate" :max="maxDate"
                class="input input-bordered rounded-lg w-full max-w-xl text-center"
                :class="isClose ? 'ann-close-date' : 'ann-publish-date', isError ? 'border-error' : ''">
            <input type="time" :value="time" @input="$emit('time', $event)"
                class="input input-bordered rounded-lg w-full max-w-xl text-center"
                :class="isClose ? 'ann-close-time' : 'ann-publish-time', isError ? 'border-error' : ''">
        </div>
    </div>
</template>
