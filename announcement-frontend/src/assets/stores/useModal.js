import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue';

export const useModal = defineStore("modal",() => {
    const modals = ref(new Map())
    const setModal = (id) => modals.value.set(id,false)
    const getModals = () => modals.value.entries()
    const setOpen = (id) => modals.value.set(id,!isOpen(id))
    const getModalById = (id) => modals?.value.get(id)
    const isOpen = (id) => getModalById(id)
    return { setModal,setOpen,isOpen,getModals }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useModal,import.meta.hot))
}