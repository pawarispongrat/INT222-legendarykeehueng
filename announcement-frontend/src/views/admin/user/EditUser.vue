<script setup>
import Header from "@/assets/components/text/Header.vue";
import UserForm from '@/assets/components/form/list/UserForm.vue';
import User from '@/assets/data/user';
import { getUserById,putUser,isLoaded } from '@/assets/data/data-handler';
import { ref, onBeforeMount, watch } from "vue";
import { useRoute } from 'vue-router';


const route = useRoute()
const user = ref('')
const id = route.params.id
const fetchUserId = async () => {
    user.value = await getUserById(id)
    isLoaded(user.value)
}

let edited = ref(-1)
const edit = (user,validate) => {
    // create()
    if (validate) putUser(user.toJSON())
}

watch([user], async () => { edited.value++ }, { deep: true })
fetchUserId()
</script>
 
<template>
    <div class="flex flex-col">
        <Header className="flex justify-center py-8">Edit user of id {{id}}</Header>
        <UserForm :user="user"/>
    </div>
</template>
 
<style scoped>

</style>