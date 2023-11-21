<script setup>
import { ref, onMounted, computed } from 'vue';
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiBullhornVariant,mdiEmailCheck } from '@mdi/js';

import Header from '@/assets/components/text/Header.vue';
import { getUserAnnouncement,subscribe,verifyOtp } from '@/assets/data/dataHandler';
import { categories } from '@/assets/data/announcement';
import { TIMEZONE, formatDate } from '@/assets/utils/dateUtils';
import { useAnnounces, modes } from '@/assets/stores/useAnnounces';
import { useModal} from '@/assets/stores/useModal';
import Pagination from '@/assets/components/Pagination.vue';
import router from '@/router';
import Table from "@/assets/components/table/Table.vue";
import Loading from "vue-loading-overlay";
import ModalButton from '@/assets/components/modal/ModalButton.vue';
import ModalForm from '@/assets/components/modal/ModalForm.vue';

const user = useAnnounces()
const announcements = ref([])
const loaded = ref(false)
const { setOpen  } = useModal()

const verifyEmail = ref("")

onMounted(async () => {
  await fetch()
  loaded.value = true
})
const fetch = async () => {
  announcements.value = await getUserAnnouncement(user.getMode(), user.getPage() - 1, user.category)
}
const sendSubscribe = async (email,categories) =>{
  setOpen('annSubscribe1')
  await subscribe(email,categories)
  verifyEmail.value = email
}
const sendOtp = async (otp) => {
  await verifyOtp(verifyEmail.value,otp)
}

const changeMode = async () => {
  user.changeMode()
  await fetch()
}
const changeCategory = async () => {
  user.setPage(1)
  await fetch()
}
const getButton = computed(() => user.getMode() === modes.CLOSE ? 'Active Announcements' : 'Closed Announcements')
const onClickDetails = (id) => router.push({ name: 'UserDetails', params: { id: id } })
</script>
 
<template>
  
  <loading :active="!loaded" :can-cancel="false" :is-full-page="false"/>
  <div class="flex h-64 items-center space-x-4 px-12">
    <svg-icon type="mdi" :size="64" :path="mdiBullhornVariant"/>
    <Header>SIT Announcement System (SAS)</Header>
  </div>
  <div class="flex flex-col w-screen h-screen items-center bg-base-100" v-if="loaded">

    <div class="absolute bottom-10 left-10 h-16 w-16 bg-white rounded-2xl shadow-2xl flex items-center justify-center ">
    <svg-icon type="mdi" class=" text-red-600" :size="52" :path="mdiEmailCheck" />
    </div>
  
    <ModalButton :modal-id="`annSubscribe`" class="absolute bottom-10 left-10 h-16 w-16 rounded-full shadow-2xl justify-center transition-transform transform-gpu hover:scale-125"/>
        <ModalForm :modal-id="`annSubscribe`"
                   name="Please fill email"
                   :categories="categories" @confirm="sendSubscribe"
                   option="Subscribe"
                   placeholder="abc123@email.com"
        />
        <ModalForm :modal-id="`annSubscribe1`"
           name="Verify OTP" @confirm="sendOtp"
           :option="`The OTP has been sented`"
        />
   

    <div class="w-full max-w-[96rem] p-12 space-y-4">
      <div class="flex items-center">
        <p class="text-lg pr-2">Category</p>
        <select v-model="user.category" class="border border-gray-300 h-10 px-2 rounded-md ann-category-filter"
                @change="changeCategory()">
          <option :value="0">ทั้งหมด</option>
          <option v-for="(category, index) of categories" :value="index + 1">{{ category }}</option>
        </select>
        
      </div>
     
      <div class="w-full flex flex-wrap items-center justify-between max-lg:justify-center max-lg:gap-y-2">
        <p class="text-xl font-bold text-[#C1A696]">
          Date/Time shown in Timezone: <span class="kanit-light text-base-content">{{ TIMEZONE }}</span>
        </p>
       
        <button @click="changeMode"
                class="text-md h-[3.4rem] w-64 max-lg:w-full border-0 btn-success text-white ann-button text-center uppercase rounded-md"
                :class="user.getMode() === modes.CLOSE ?
            'bg-success hover:bg-emerald-500' : 'bg-error hover:bg-red-500'">
          {{ getButton }}
        </button>
        
      </div>
      <div v-if="announcements?.content?.length > 0">
        <Table :head="['No','Title','Close Date','Category']"
               :body="announcements.content"
               body-class="cursor-pointer hover hover:bg-base-300"
               :use-header="false" :use-action="false"
               @section-click="onClickDetails">>
          <template #column="{ items, index }">
            <td class="py-6">{{ index + 1 + (announcements.page * announcements.size) }}</td>
            <td class="ann-title text-left"> {{ items.announcementTitle }}</td>
            <td class="ann-close-date px-3 text-center ">{{ formatDate(items.closeDate) }}</td>
            <td class="ann-category text-center ">
              <span class="text-[#FAA497] bg-[#FAA497] bg-opacity-20 rounded-lg px-2 py-1 text-sm">{{ items.announcementCategory }}</span>
            </td>
          </template>
        </Table>
        <Pagination @fetch="fetch" :total-pages="announcements?.totalPages" :total-elements="announcements?.totalElements" />
      </div>
      <div v-else class="flex items-center justify-center text-error text-lg">No Announcement</div>
    </div>
  </div>
</template>
 
<style scoped></style>