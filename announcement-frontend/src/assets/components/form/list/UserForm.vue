<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import Dropdown from '../Dropdown.vue';
import { computed } from 'vue';
import { humanizeDate } from '../../../utils/dateUtils';

const ROLE_ENUM = [
    'announcer','admin'
]
const props = defineProps({
    user: { required: false }
})
const computedInput = (data) => computed(() => (data && data)).value
const computedDate = (date) => computed(() => (humanizeDate(date ? date : new Date()))).value
</script>
<template>
    <div class="space-y-8">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" :model-value="computedInput(user.username)" />
                <Input label="Name" placeholder="นายสมร ใจดี" :model-value="computedInput(user.name)"/>
                <Input label="Email" placeholder="rewlegendary@email.com" :model-value="computedInput(user.email)"/>
                <Dropdown label="Role" :options="ROLE_ENUM" :value="computedInput(user.role)" :isFull="true"/>
                <div class="pt-8 space-y-1">
                    <p class="ann-created-on">Create on: {{ computedDate(user.createdOn) }}</p>
                    <p class="ann-updated-on">Update on: {{ computedDate(user.updatedOn) }}</p>
                </div>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Cancel" to="/admin/user" class="my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
                <Button name="Save" class="my-6 px-8 bg-blue-700 hover:bg-success" />
        </div>
    </div>
</template>