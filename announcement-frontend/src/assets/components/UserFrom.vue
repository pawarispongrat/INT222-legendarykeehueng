<script setup>
import { ref } from "vue";
import router from "../../router";


const props = defineProps({
  user: { required: true },
  submitText: String,
  disabledSubmit: { type: Boolean, default: false },
});

const emits = defineEmits(["submit"])
const MAX_TITLE = 200;
const validate = ref(false);
const validates = ref({
  username: true,
  name: true,
  email: true,
})
const errorText = ref({
  username: '',
  name: '',
  email: '',
})
const submit = () => {
  validateName()
  validateUsername()
  validateEmail()
  validate.value = Object.values(validates.value).every((item) => item);
  emits("submit", props.user, validate.value)
};
const sendErrorText = (type, text, byValidate) => {
  validates.value[type] = byValidate
  errorText.value[type] = byValidate ? "" : text
}

const validateName = () => {
  const name = props.user.name.trim()
  if (!name) sendErrorText("name", "Please fill the name", Boolean(name))
  else sendErrorText("name", "name size must lower than 200", name.length <= 200)
}
const validateUsername = () => {
  const username = props.user.username.trim()
  if (!username) sendErrorText("username", "Please fill the username", Boolean(username))
  else sendErrorText("username", "name size must lower than 200", username.length <= 200)
}
const validateEmail = () => {
  const email = props.user.email.trim()
  if (!email) sendErrorText("email", "Please fill the email", Boolean(email))
  else sendErrorText("email", "name size must lower than 200", email.length <= 200)
}
</script>
 
<template>
 <div class="space-y-1 w-1/4">
    <div class="-mt-6">
      <p>
        Username
        <span class="text-sm text-gray-500">({{ user.username?.length }}/{{ MAX_TITLE }})</span>
        <span v-show="!validates.username" class="text-sm text-red-500">{{ ` ${errorText.username}` }}</span>
      </p>
      <input @click="validates.username = true" type="text" :maxlength="MAX_TITLE" v-model="user.username"
        class="input input-bordered w-full ann-title placeholder:text-gray-500"
        :class="{ 'outline outline-red-500': !validates.username }" />
    </div>
    <div class="-mt-6">
      <p>
        name
        <span class="text-sm text-gray-500">({{ user.name?.length }}/{{ MAX_TITLE }})</span>
        <span v-show="!validates.name" class="text-sm text-red-500">{{ ` ${errorText.name}` }}</span>
      </p>
      <input @click="validates.name = true" type="text" :maxlength="MAX_TITLE" v-model="user.name"
        class="input input-bordered w-full ann-title placeholder:text-gray-500"
        :class="{ 'outline outline-red-500': !validates.name }" />
    </div>
    <div class="-mt-6">
      <p>
        email
        <span class="text-sm text-gray-500">({{ user.email?.length }}/{{ MAX_TITLE }})</span>
        <span v-show="!validates.email" class="text-sm text-red-500">{{ ` ${errorText.email}` }}</span>
      </p>
      <input @click="validates.email = true" type="text" :maxlength="MAX_TITLE" v-model="user.email"
        class="input input-bordered w-full ann-title placeholder:text-gray-500"
        :class="{ 'outline outline-red-500': !validates.email }" />
    </div>
    <div class="space-y-2">
      <p>role</p>
      <select v-model="user.role" class="select select-bordered w-full ann-category">
         <option value="admin">admin</option>
         <option value="announcer" selected>announcer</option>
      </select>
    </div>


    <div class="flex  w-full">
      <button class="btn border-0 bg-[#C1A696] ann-button text-gray-100 w-44 hover:bg-[#E4B79D] disabled:bg-base-100"
        @click="submit()" :disabled="disabledSubmit">
        {{ submitText }}
      </button>
      <router-link class="btn btn-error ann-button w-44 ml-10 hover:bg-red-500 border-0 text-gray-100"
        :to="{ name: 'User' }">Back</router-link>
    </div>
  </div>  
</template>
 
<style scoped>

</style>