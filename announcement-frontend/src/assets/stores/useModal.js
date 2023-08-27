import { defineStore, acceptHMRUpdate } from 'pinia'
import { ref } from 'vue';

export const useModal = defineStore("modal",() => {
    const modals = ref([])
    const setModal = (id) => modals.value.push({ id: id, open: false })
    const getModals = () => modals.value
    const setOpen = (id) => {
        modals.value = findModalById(id) ? 
            modals.value.map((modal) =>  modal.id === id ? { ...modal, open: !modal.open } : modal) : 
            [ ...modals.value, { id: id, open: true } ]
    } 
    const findModalById = (id) => modals.value.find((modal) => modal.id === id)
    const isOpen = (id) => findModalById(id)?.open
    return { setModal,setOpen,isOpen,getModals }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useModal,import.meta.hot))
}