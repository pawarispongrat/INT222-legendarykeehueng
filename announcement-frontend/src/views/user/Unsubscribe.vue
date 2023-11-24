<script setup>
import { ref } from 'vue';
import { categories } from '@/assets/data/announcement';
import { useRoute } from 'vue-router';
import { unsubscribe } from '@/assets/data/dataHandler.js';

const route = useRoute();
const test = () =>{console.log(email);console.log(hash);console.log(selectedOptions.value);}
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
    <div>
        <div class="form-control">
            <p @click="test" class="font-bold"> Select Category to unsubscribe</p>
            <label class="cursor-pointer label" v-for="(category, index) in categories" :key="index">
                <div>
                    <span class="font mr-3">{{ category }}</span>
                    <input type="checkbox" :checked="isSelected(index + 1)" class="checkbox checkbox-error"
                        @change="toggleSelection(index + 1)" />
                </div>
            </label>
        </div>
        <button class="btn" :disabled="selectedOptions.length === 0" @click="unsubscribe(email,hash,selectedOptions)">Confirm</button>
    </div>
</template>
 
<style scoped></style>