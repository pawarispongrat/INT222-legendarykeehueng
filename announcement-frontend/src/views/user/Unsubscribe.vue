<script setup>
import { ref } from "vue";
import { categories } from "@/assets/data/announcement";
import { useRoute } from "vue-router";
import { unsubscribe } from "@/assets/data/dataHandler.js";

const route = useRoute();
const test = () => {
  console.log(email);
  console.log(hash);
  console.log(selectedOptions.value);
};
const email = route.query.email;
const hash = route.query.hash;

const selectedOptions = ref([]);

const isSelected = (index) => selectedOptions.value.includes(index);
const toggleSelection = (index) => {
  const indexInSelectedOptions = selectedOptions.value.indexOf(index);
  if (indexInSelectedOptions === -1) {
    selectedOptions.value.push(index);
  } else {
    selectedOptions.value.splice(indexInSelectedOptions, 1);
  }
};
</script>

<template>
  <div class="card shrink-0 w-full mt-10 max-w-lg shadow-2xl bg-[#DBBEA5]">
    <div class="form-control m-8">
      <div class="card bg-white">
        <p class="font-black text-4xl text-[#d89c68d7] ms-24">UNSUBSCRIBE</p>
      </div>
      <p class="font-bold text-base-200 mt-6">
        [ Your email address ] :
        <span class="text-[#B2631F]">{{ email }} </span>
      </p>
      <p @click="test" class="font-bold text-base-200">
        " Select Category to unsubscribe "
      </p>
      <label
        class="cursor-pointer label text-base-200"
        v-for="(category, index) in categories"
        :key="index"
      >
        <div>
          <span class="font mr-3">{{ category }}</span>
          <input
            type="checkbox"
            :checked="isSelected(index + 1)"
            class="checkbox checkbox-error"
            @change="toggleSelection(index + 1)"
          />
        </div>
      </label>
    </div>
   
      <label for="my_modal_6" :class="selectedOptions.length === 0 ? 'pointer-events-none  bg-gray-500 text-base-300 border border-gray-500 ':'bg-[#d89c68d7] border border-[#d89c68d7]'" class="btn w-28 ml-10 mb-7" @click="unsubscribe(email, hash, selectedOptions)">unsubscribe</label>

  
    <input type="checkbox" id="my_modal_6" class="modal-toggle" />
    <div class="modal" role="dialog">
      <div class="modal-box bg-[#DBBEA5] border border-white">
        <h3 class="font-bold text-xl text-[#B2631F] ">" Success "</h3>
        <p class="py-4 text-white  "> 'You have successfully unsubscribed!'</p>
        <div class="modal-action">
          <label for="my_modal_6" class="btn bg-[#d89c68d7] border border-white">Close!</label>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
