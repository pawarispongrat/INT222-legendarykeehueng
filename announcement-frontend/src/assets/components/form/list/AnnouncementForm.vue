<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import Dropdown from '../Dropdown.vue';
import { computed } from 'vue';
import { humanizeDate } from '../../../utils/dateUtils';
import DateTimeForm from "../DateTimeForm.vue";
import Checkbox from '../Checkbox.vue';
import QuilForm from '../QuilForm.vue';

const CATEGORY_ENUM = [ 'ทั่วไป','ทุนการศึกษา','หางาน','ฝึกงาน' ]
const props = defineProps({
    announcement: { required: false },
    errors: { required: false },
    disabledSave: { type: Boolean , required: false }
})
defineEmits(["submit"])
const changeText = (e) => validateDesc.value = quilEditor.value.getQuil().getText();
const computedDate = (date) => computed(() => (humanizeDate(date ? date : new Date()))).value
</script>
<template>
    <div class="space-y-8">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Title" placeholder="The Super Title" field="title"
                    :model-value="announcement.title" @update="(input) => announcement.title = input"
                />
                <Dropdown label="Category" :options="CATEGORY_ENUM" :isFull="true" field="categoryId"
                        :select="announcement.categoryId" @update="(input) => announcement.categoryId = CATEGORY_ENUM[input]"/>
                <!-- <label class="label">Description</label>
                <QuillEditor theme="snow" toolbar="full"
                    v-model:content="announcement.description" class=" bg-white ann-description" contentType="html"
                    @text-change="changeText" ref="quilEditor" /> -->
                <QuilForm label="Description"  field="description"
                        :model-value="announcement.description" 
                />
                <DateTimeForm label="Publish Date" field="publishDate" @date="(e) => (announcement.publishDate = e.target.value)"
                    @time="(e) => (announcement.publishTime = e.target.value)" :date="announcement.publishDate"
                    :time="announcement.publishTime" :max-date="announcement.closeDate" :time-publish="announcement.publishTime"
                />
                <DateTimeForm label="Close Date" field="closeDate" :is-close="true" @date="(e) => (announcement.closeDate = e.target.value)"
                    @time="(e) => (announcement.closeTime = e.target.value)" :date="announcement.closeDate"
                    :time="announcement.closeTime" :min-date="announcement.publishDate" :time-close="announcement.closeTime"
               />
               <Checkbox label="Display" field="display" sub-label="Check to show this announcement"
                    :model-value="announcement.display" @update="(input) => announcement.display = input"/>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
            <Button name="Cancel" to="/admin/user" class="my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
            <Button name="Save" class="my-6 px-8 bg-blue-700 hover:bg-success" @click="$emit('submit')" :disabled="disabledSave"/>
        </div>
    </div>
</template>
<style scoped>
#editor {
    color: white;
}
</style>