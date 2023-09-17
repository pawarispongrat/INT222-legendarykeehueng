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
    disabledDate: { type: Boolean, default: false },
    disabledSave: { type: Boolean , required: false }
})
defineEmits(["submit"])
const computedDate = (date) => computed(() => (humanizeDate(date ? date : new Date()))).value

const isUsername = () => {

}
</script>
<template>
    <div class="space-y-8">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" class-name="ann-username" error-class-name="ann-error-username" :required="true"
                        :model-value="user.username" 
                        @update="(input) => user.username = input"/>
                <Input label="Name" placeholder="Duangcharoen Siwasutum" class-name="ann-name"
                        :model-value="user.name" 
                        @update="(input) => user.name = input"/>
                <Input label="Email" placeholder="rewlegendary@email.com" class-name="ann-email"
                        :model-value="user.email" 
                        @update="(input) => user.email = input"/>
                <Dropdown label="Role" :options="ROLE_ENUM" :isFull="true" class-name="ann-role"
                        :select="user.role ?? ROLE_ENUM[0]" 
                        @update="(input) => user.role = input"/>
                <div v-if="!disabledDate" class="pt-8 space-y-1">
                    <p>Created on:<span class="ann-created-on"> {{ computedDate(user.createdOn) }}</span></p>
                    <p>Updated on:<span class="ann-updated-on"> {{ computedDate(user.updatedOn) }}</span></p>
                </div>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Cancel" to="/admin/user" class="ann-button my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
                <Button name="Save" class="ann-button my-6 px-8 bg-blue-700 hover:bg-success" @click="$emit('submit')" :disabled="disabledSave"/>
        </div>
    </div>
</template>