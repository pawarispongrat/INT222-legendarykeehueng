<script setup>
import Button from '../Button.vue';
import Input from '../Input.vue';
import Dropdown from '../Dropdown.vue';

</script>
<template>
    <div class="space-y-8">
        <div class="flex justify-center">
            <div class="form-control w-[30rem]">
                <Input label="Username" placeholder="RewLegendary" />
                <Input label="Name" placeholder="นายสมร ใจดี" />
                <Input label="Email" placeholder="rewlegendary@email.com" />
                <Dropdown label="Role" :options="['announcer','admin']" :isFull="true"/>
                <div class="pt-8">
                    <p class="ann-created-on">Create on:</p>
                    <p class="ann-updated-on">Update on:</p>
                </div>
            </div>
        </div>
        <div class="flex justify-end gap-x-4 border-t border-slate-400">
                <Button name="Cancel" to="/admin/user" class="my-6 px-8 border-black text-black border bg-transparent hover:bg-error hover:border-error hover:text-white" />
                <Button name="Save" class="my-6 px-8 bg-[#E4B79D] hover:bg-success" />
        </div>
    </div>
</template>