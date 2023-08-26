<script setup>
import UserForm from '@/assets/components/form/list/UserForm.vue';
import User from '@/assets/data/user';
import { getUserById,putUser,isLoaded } from '@/assets/data/data-handler';
import { ref, onBeforeMount, watch } from "vue";
import { useRoute } from 'vue-router';
import Header from "@/assets/components/Header.vue";
const user = ref('')
onBeforeMount(async () => {
    const route = useRoute()
    user.value = await getUserById(route.params.id)
    if (isLoaded(user.value)) {
        user.value = new User().fromJSON(user.value)
    }
    // create()
})


let edited = ref(-1)
watch([user], async () => { edited.value++ }, { deep: true })
const edit = (user,validate) => {
    // create()
    if (validate) putUser(user.toJSON())
}
</script>
 
<template>
    <div class="w-full p-8">
        <Header>Edit User Id: {{$route.params.id}}</Header>
        <UserForm></UserForm>
    </div>
</template>
 
<style scoped>

</style>