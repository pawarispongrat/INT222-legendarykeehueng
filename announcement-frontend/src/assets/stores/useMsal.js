import {acceptHMRUpdate, defineStore} from "pinia";
import {ref} from "vue";
import {Auth} from "@/assets/data/msalAuthenticate";

export const useMsal = defineStore("msal",() => {
    const initialized = ref(false)
    const account = ref(null)
    const error = ref()

    async function initialize (client) {
        if (initialized.value === true) return account.value
        return Auth.initialize(client).then(data => {
            account.value = data
            return data
        })
    }
    async function login () {
        error.value = ''
        return Auth.login()
            .then(data => {
                account.value = data
                error.value = ''
            })
            .catch(err => {
                error.value = err.message
                throw(err)
            })
    }

    async function logout () {
        return Auth.logout().then(() => {
            account.value = null
        })
    }

    return { error, account, initialized, initialize, login, logout, }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useMsal,import.meta.hot))
}