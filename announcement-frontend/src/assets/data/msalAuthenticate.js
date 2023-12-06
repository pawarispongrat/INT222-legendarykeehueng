
import {
    InteractionRequiredAuthError,
    PublicClientApplication
} from '@azure/msal-browser'

const scopes = ["05274b39-da42-4da3-b122-41d5825c0480/Roles"]
const REDIRECT_URI = import.meta.env.VITE_AZURE_REDIRECT

const config = {
    auth: {

        clientId: '05274b39-da42-4da3-b122-41d5825c0480', // This is the ONLY mandatory field that you need to supply.
        authority: 'https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d', // Defaults to "https://login.microsoftonline.com/common"
        redirectUri: REDIRECT_URI, // Points to window.location.origin. You must register this URI on Azure Portal/App Registration.
        postLogoutRedirectUri: REDIRECT_URI, // Indicates the page to navigate after logout.
        clientCapabilities: ['CP1'], // this lets the resource owner know that this client is capable of handling claims challenge.
    },
}
/**
 * MSAL instance
 */
export const msal = new PublicClientApplication(config)

/**
 * Auth service
 */
export const Auth = {
    /**
     * Initialize and return active account
     */
    async initialize (client) {
    // start msal
        await msal.handleRedirectPromise()

        // hook into application router
        if (client) {
            msal.setNavigationClient(client)
        }

        // grab and set account if in session
        const accounts = msal.getAllAccounts()
        if (accounts?.length) {
        this.setAccount(accounts[0])
    }

    // return any active account
    return msal.getActiveAccount()
},


/**
 * Login
 */
async login() {
    const request = { redirectUri: config.auth.redirectUri, scopes, }
    return msal.loginPopup(request).then(result => {
        // could do something with the AuthResult here if you need to
        // console.log('Logged in with', result)
        // set active account
        return this.setAccount(result.account)
    })
        .catch((error) => {
            // if we get stuck, clear session and attempt to log in again
            if (error.errorCode === 'interaction_in_progress') {
                this.reset()
                return this.login()
            }
            throw(new Error(error.errorMessage))
        })
},

/**
 * Logout
 */
async logout () {
    return msal.logoutPopup({
        // required to make the application return to the home page
        mainWindowRedirectUri: config.auth.postLogoutRedirectUri
    })
},

/**
 * Get token for api
 */
async getToken() {
    const request = { scopes }

    return msal
        // try getting the token silently
        .acquireTokenSilent(request)
        // attempt login popup if this fails
        .catch(async (error) => {
            if (error instanceof InteractionRequiredAuthError) return msal.acquireTokenPopup(request)
            throw error
        })
        .then((result) => {
            return result.accessToken
        })
},

/**
 * Set active account
 * @private
 */
setAccount(account) {

    msal.setActiveAccount(account)
    return account
},

    isLoggedIn() {
        return msal.getAllAccounts().length > 0;
    } ,

/**
 * Escape hatch when msal gets stuck
 * @private
 */
reset () { sessionStorage.clear()}, }