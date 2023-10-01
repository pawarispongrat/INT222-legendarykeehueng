<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import Dropdown from '../Dropdown.vue';
import { computed,onBeforeMount,ref, watch } from 'vue';
import { humanizeDate } from '../../../utils/dateUtils';
import { useRoute, useRouter } from 'vue-router';
import { getUserById,putUser } from "@/assets/data/data-handler.js"
import { isUniqueUser,isEmptyUser } from '@/assets/data/validate.js';

defineEmits(["submit"])

const ROLE_ENUM = [ 'announcer','admin']

const computedDate = (date) => computed(() => (humanizeDate(date ? date : new Date()))).value
const route = useRoute()
const router = useRouter()
const user = ref('')

const id = route.params.id
const fetchUserId = async () => {
    user.value = await getUserById(id)
//     isLoaded(user.value, "/admin/user")
    if (!user.value) router.push("/admin/user/")
}

const errors = ref({})
const onSave = async () => {
    errors.value = { username: [], name: [], email: [], role: []}
    if (!isEmptyUser(user,errors)) return
    const json = await putUser(user.value)
    if (!isUniqueUser(json?.detail,errors)) router.push("/login")
}


let edited = ref(-1)
watch([user], async () => { edited.value++ }, { deep: true })
onBeforeMount(async () => { await fetchUserId() })

</script>
<template>
    <form class="space-y-8" @submit.prevent="onSave">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" class-name="ann-username" error-class-name="ann-error-username" :required="true" 
                        v-model.trim="user.username" :max="45" :errors="errors.username"/>         
                <Input label="Name" placeholder="Duangcharoen Siwasutum" class-name="ann-name" error-class-name="ann-error-name" :required="true"
                        v-model.trim="user.name" :max="100" :errors="errors.name"/>
                <Input label="Email" placeholder="rewlegendary@email.com" type="email" class-name="ann-email" error-class-name="ann-error-email" :required="true"
                        v-model.trim="user.email" :max="150" :errors="errors.email"/>
                <Dropdown label="Role" :options="ROLE_ENUM" :isFull="true" class-name="ann-role" error-class-name="ann-error-role"
                        :select="user.role ?? ROLE_ENUM[0]" @update="(value) => user.role = value" :errors="errors.role"/>
                <div class="pt-8 space-y-1">
                    <p>Created on: <span class="ann-created-on"> {{ computedDate(user.createdOn) }}</span></p>
                    <p>Updated on: <span class="ann-updated-on"> {{ computedDate(user.updatedOn) }}</span></p>
                </div>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Cancel" to="/admin/user" class="ann-button my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
                <Button name="Save" type="submit" class="ann-button my-6 px-8 bg-blue-700 hover:bg-success" :disabled="edited <= 0"/>
        </div>
    </form>
</template>