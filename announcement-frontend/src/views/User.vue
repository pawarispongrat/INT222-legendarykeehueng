<script setup>
import Header from '../assets/components/Header.vue';
import { ref, onMounted } from 'vue';
import { getUser,deleteUser } from '../assets/data/data-handler.js';
import { formatDate, TIMEZONE } from '../assets/utils';
import Sidebar from '../assets/components/Sidebar.vue';

const users = ref([])

const loaded = ref(false)
onMounted(async () => {
  await fetch()
  loaded.value = true
  console.log(users.value);
})
const fetch = async () => {
  users.value = await getUser()
}

const removeUser = (id) => {
  users.value = users.value.filter((users) => users.id !== id)
  deleteUser(id)
}
const currentUser = ref('')
const setUser = (user) => currentUser.value = user

</script>
<template>
 <!-- <Sidebar class="fixed"></Sidebar>   -->
 <div v-show="loaded" class="max-w-[65rem]">
    
    <Header>USER MANAGEMENT</Header>
    <div class="flex items-center justify-between py-5">
      <!-- <Sidebar class="absolute inset-y-0 left-0 w-3/12"></Sidebar> -->
      <h2 class="text-xl font-bold text-[#C1A696] py-3">
        Date/Time shown in Timezone: <span class="kanit-light text-base-content">{{ TIMEZONE }}</span>
      </h2>
      <router-link v-if="users && users?.content?.length !== 0"
      class="text-md px-4 py-3 border-0 btn-success text-white ann-button text-center shadow-md shadow-[#C1A696] bg-[#C1A696] hover:bg-[#E4B79D] rounded-md"
        :to="{ name: 'AddUser' }">Add User</router-link>
    </div>
    <div v-if="users && users?.content?.length !== 0">
      <table class="bg-base-100 shadow-md">
        <thead>
          <tr class="bg-[#C1A696] uppercase text-sm text-gray-100">
            <th class="p-5">No.</th>
            <th class="text-left w-64">Username</th>
            <th class="text-left w-28">Name</th>
            <th class="text-left  w-32">Email</th>
            <th class="text-left w-32">Role</th>
            <th class="text-left w-36 ">CreatedOn</th>
            <th class=" w-36">UpdatedOn</th>
            <th>Action</th>
           
          </tr>
        </thead>
        <tbody>
          <tr v-for="(users, index) in users" :key="users.id"
            class="hover border-base-300 border-b kanit-light ann-item">
            <th class="py-6">{{  index + 1 }}</th>
            <td class="ann-title py-1">{{ users.username }}</td>
            <td class="ann-category px-4">{{users.name }}</td>
            <td class="ann-category px-4">{{users.email }}</td>
            <td class="ann-category px-4">{{users.role }}</td>
            <td class="ann-publish-date px-4">{{ formatDate(users.createOn) }}</td>
            <td class="ann-close-date px-4">{{ formatDate(users.updateOn) }}</td> 
            <!-- <td class="text-center ann-display"><span class="px-2 py-1 bg-opacity-20 rounded-lg text-sm"
                :class="computedDisplayColor(users.announcementDisplay)">{{ users.announcementDisplay
                }}</span></td>  -->

            <td class="text-center px-4">
              <router-link class="text-sm px-4 py-1 mr-3 rounded-lg btn-outline bg-[#FAA497] hover:bg-[#E4B79D] text-white ann-button"
                :to="{ name: 'AnnouncementDetails', params: { id: users.id } }">edit</router-link>

              <label for="delete"
              class="text-sm px-4 py-1  rounded-lg btn-outline  bg-error hover:bg-red-500 text-white ann-button cursor-pointer"
                @click="setUser(users)">delete</label>
              <input type="checkbox" id="delete" class="modal-toggle" />
              <div class="modal" id="delete">
                <div class="modal-box rounded-lg w-96 h-72 flex flex-col justify-center">
                  <h3 class="font-bold text-xl text-error mb-2">DELETE?</h3>
                  <div class="bg-origin-content bg-left-top rounded-2xl border-4 border-[#E6D2C7] items-center h-48" style="background-image: url(https://media.discordapp.net/attachments/518037078731128832/1107630576527224922/Front_End_7.jpg?width=555&height=270)"></div>
                  <p class="p-2">If yes, press confirm and if you don't want to delete press cancel</p>
                  <div class="modal-action flex justify-center gap-x-3 w-full">
                    <label for="delete" class="btn btn-error hover:bg-red-500 border-0 ann-button rounded-lg w-1/3 text-gray-100"
                      @click="removeUser(currentUser.id)">Confirm</label>
                    <label for="delete" class="btn ann-button rounded-lg w-1/3 btn-outline border-[#C1A696] text-[#C1A696] border-2 hover:bg-[#E4B79D] hover:border-[#E4B79D]">Cancel</label>
                  </div>
                </div>
              </div>
            </td>
           
          </tr>
        </tbody>
      </table>
      <!-- <Pagination @fetch="fetch" :total-pages="announcements?.totalPages" :total-elements="announcements?.totalElements" class="float-right"/> -->
    </div>
    <div v-else class="flex justify-center items-center h-96 w-full">
      <div class="text-error text-3xl">No Announcement</div>
    </div>

  <div class="py-6"></div>
</div>
</template>
 
<style scoped>

</style>