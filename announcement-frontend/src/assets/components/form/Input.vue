<script setup>
const props = defineProps({
    label: { type: String, required: false },
    subLabel: { type: String, required: false },
    placeholder: String,
    labelType: String,
})

</script>
<template>
    <div class="flex flex-col w-full max-w-xl">
        <label class="label justify-start gap-x-2">
            {{label}}
            <span class="text-secondary" :v-show="subLabel">{{subLabel}}</span>
        </label>
        <input type={{labelType}} :placeholder="placeholder" class="input input-bordered rounded-lg w-full max-w-xl " />
    </div>
</template>