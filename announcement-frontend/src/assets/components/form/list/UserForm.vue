<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import Dropdown from '../Dropdown.vue';
import { computed } from 'vue';
import { humanizeDate } from '../../../utils/dateUtils';

const ROLE_ENUM = [ 'announcer','admin']
const props = defineProps({
    user: { required: false },
    errors: { required: false },
    disabledSave: { type: Boolean , required: false }
})
defineEmits(["submit"])
const computedDate = (date) => computed(() => (humanizeDate(date ? date : new Date()))).value
</script>
<template>
    <div class="space-y-8">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" field="username"
                        :model-value="user.username" @update="(input) => user.username = input"/>
                <Input label="Name" placeholder="Duangcharoen Siwasutum" field="name" 
                        :model-value="user.name" @update="(input) => user.name = input"/>
                <Input label="Email" placeholder="rewlegendary@email.com" field="email" 
                        :model-value="user.email" @update="(input) => user.email = input"/>
                <Dropdown label="Role" :options="ROLE_ENUM" :isFull="true" field="role"
                        :select="user.role" @update="(input) => user.role = ROLE_ENUM[input]"/>
                <div class="pt-8 space-y-1">
                    <p class="ann-created-on">Created on: {{ computedDate(user.createdOn) }}</p>
                    <p class="ann-updated-on">Updated on: {{ computedDate(user.updatedOn) }}</p>
                </div>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Cancel" to="/admin/user" class="my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
                <Button name="Save" class="my-6 px-8 bg-blue-700 hover:bg-success" @click="$emit('submit')" :disabled="disabledSave"/>
        </div>
    </div>
</template>