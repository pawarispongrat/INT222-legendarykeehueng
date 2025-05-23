<script setup>
import {ref, onMounted, computed, onBeforeMount} from 'vue';
import SvgIcon from '@jamescoyle/vue-icon';
import {mdiAccount, mdiBullhornVariant, mdiEmailCheck} from '@mdi/js';

import Header from '@/assets/components/text/Header.vue';
import { getUserAnnouncement,subscribe,verifyOtp } from '@/assets/data/dataHandler';
import { categories } from '@/assets/data/announcement';
import { formatDate } from '@/assets/utils/dateUtils';
import { useAnnounces, modes } from '@/assets/stores/useAnnounces';
import { useModal} from '@/assets/stores/useModal';
import Pagination from '@/assets/components/Pagination.vue';
import router from '@/router';
import Table from "@/assets/components/table/Table.vue";
import Loading from "vue-loading-overlay";
import ModalForm from '@/assets/components/modal/ModalForm.vue';
import Timezone from "@/assets/components/text/Timezone.vue";
import AbsoluteIcon from "@/assets/components/icon/AbsoluteIcon.vue";
import {useMsal} from "@/assets/stores/useMsal";
import Modal from "@/assets/components/modal/Modal.vue";
import {Auth} from "@/assets/data/msalAuthenticate";
import {clearToken, getAccessToken, getJwtEmail, getJwtName, getJwtRoles} from "@/assets/data/tokenStorage";

const msal = useMsal()
const user = useAnnounces()
const announcements = ref([])
const loaded = ref(false)
const { setOpen,setModal,clearModal  } = useModal()

const verifyEmail = ref("")
const otpResponse = ref()
const errors = ref({
    email: null,
    otp: null
})

onBeforeMount(async () => {
  await fetch()
  loaded.value = true
})
const fetch = async () => {
  announcements.value = await getUserAnnouncement(user.getMode(), user.getPage() - 1, user.category)
}
let previousEmail = ref("")
let previousCategories = ref([])

const sendSubscribe = async (email,categories) =>{
  previousEmail.value = email
  previousCategories.value = categories
  const statusValue = await subscribe(email,categories)
  verifyEmail.value = email
  if(statusValue === 200){
    setModal('annSubscribe')
    setOpen('annSubscribe1')
    errors.value.email = null
  }
  else { errors.value.email = "Wrong format email!" }
}
const resendOtp = async ()=>{
 await subscribe(previousEmail.value,previousCategories.value)
}

const sendOtp = async (otp) => {
  const response = await verifyOtp(verifyEmail.value, otp);
  if (response.status === 200) {
    otpResponse.value = response.json();
    setOpen('annSubscribe2');
    setModal('annSubscribe1');
    errors.value.otp = null;
  } else {
    errors.value.otp = "Otp is invalid or expired";
  }
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
const onOpenMail = () => {
  setOpen('annSubscribe')
}
const onLogin = async () => {
  // if (!getAccessToken()) await msal.login()
  // else setOpen("accountDetail")
  if (getAccessToken()) {
    setOpen("accountDetail")
  } else {
    await router.push("/login")
  }
}
const onLogout = () => {
  if (Auth.isLoggedIn()) {
    msal.logout()
    clearToken()
  }
  else if (getAccessToken()) {
    clearToken()
    window.location.reload()
  }
}
</script>
 
<template>
  <loading :active="!loaded" :can-cancel="false" :is-full-page="false"/>
  <div class="flex h-64 items-center space-x-4 px-12 ">
    <svg-icon type="mdi" :size="64" :path="mdiBullhornVariant"/>
    <Header>SIT Announcement System (SAS)</Header>
  </div>
  <div class="flex flex-col w-screen h-screen items-center bg-[#EFE2D7] " v-if="loaded">
    <AbsoluteIcon tip="Subscribe" @open="onOpenMail" :icon="mdiEmailCheck" icon-class="text-red-400" div-class="bottom-12 left-12 z-20"/>
    <AbsoluteIcon :tip="getAccessToken() ? 'Welcome' : 'Login'" @open="onLogin" :icon="mdiAccount" icon-class="text-red-400" div-class="bottom-32 left-12 z-10"/>
    <Modal @confirm="onLogout" confirm-text="Logout" modal-id="accountDetail" :title="`Welcome, ${getJwtName()}`">
      <template #body>
        <div class="text-slate-500">
          <p>EMAIL => {{ getJwtEmail() }}</p>
          <p>ROLE => {{ getJwtRoles() }}</p>
        </div>
      </template>
    </Modal>
    <ModalForm modal-id="annSubscribe"
               name="Enter the email for subscribe categories"
               input-label="Subscribe email for: "
               :input-value="getJwtEmail()"
               :error="errors?.email"
               :categories="categories" @confirm="sendSubscribe"
               :isOption="true"
               option="Categories"
               placeholder="abc123@email.com"
    />
    <ModalForm :modal-id="`annSubscribe1`"
               name="Verify OTP" @confirm="sendOtp"
               @resend="resendOtp"
               :isResend="true"
               :error="errors?.otp"
               option="The OTP has been sent"
               :open="true"
    />
    <ModalForm :modal-id="`annSubscribe2`"
               option="Thank you for subscribe SAS Announcement"
               :categories="categories"
               :open="true"
               :status=otpResponse
               @confirm="() => clearModal('annSubscribe2')"

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
        <Timezone header-class="text-xl text-[#C1A696]"/>
        <button @click="changeMode"
                class="text-md h-[3.4rem] w-64 max-lg:w-full border-0 btn-success text-white ann-button text-center uppercase rounded-md"
                :class="user.getMode() === modes.CLOSE ?
            'bg-success hover:bg-emerald-500' : 'bg-error hover:bg-red-500'">
          {{ getButton }}
        </button>

      </div>
      <div v-if="announcements?.content?.length > 0">
        <Table :head="['No','Title','Close Date','Category']"
               :body="announcements?.content"
               body-class="cursor-pointer hover hover:bg-base-300"
               :use-header="false" :use-action="false"
               @section-click="onClickDetails">>
          <template #column="{ items, index }">
            <td class="py-6">{{ index + 1 + (announcements?.page * announcements?.size) }}</td>
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