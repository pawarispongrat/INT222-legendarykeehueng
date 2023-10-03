import router from '../../router/index.js';

const API_HOST = import.meta.env.VITE_BASE_URL
const API_ANNOUCEMENTS = `${API_HOST}/api/announcements`
const API_USERS = `${API_HOST}/api/users`
const API_PAGES = `${API_HOST}/api/announcements/pages`
const API_TOKEN = `${API_HOST}/api/token`

function sendPage(page) {
    alert("The request page is not available")
    router.push(page)
}

function authorizeToken(token) {
    return {'Content-type': 'application/json', 'Authorization': `Bearer ${token}` }
}

function getAuthorizedToken() {
    return authorizeToken(localStorage.getItem("accessToken"))
}

//GET STATUS TO CHECK THAT TOKEN WORKS!
async function isAuthenticated() {
    return await validateToken() !== null
}
//REVOKE TOKEN
async function validateToken() {
    try {
        const refreshToken = localStorage.getItem("refreshToken")
        const status = await matchPassword(null) //IS EXPIRED
        if (status === 401 && refreshToken !== null) return await revokeToken(refreshToken)
        else return null
    } catch (error) {
        return null
    }
}


function isLoaded(data,page = "/announcement/",alert = true) {
    if (!data) {
        if (alert) sendPage(page)
        return false
    } 
    return true
}

async function revokeToken(refreshToken) {
    try {
        const res = await fetch(`${API_TOKEN}`, { headers: authorizeToken(refreshToken) })
        const tokenDetail = await res.json()
        if (res.ok) {
            const accessToken = tokenDetail.token
            localStorage.setItem("accessToken", accessToken)
            return res.status
        } 
        else return null
    } catch (error) { return null }
}

async function getUserAnnouncement(mode,page,category,size) {
    try {

        const PAGE = page ? `page=${page}` : ''
        const SIZE = size ? `size=${size}` : ''
        const MODE = mode ? `mode=${mode}` : ''
        const CATEGORY = category ? `category=${category}` : ''
        const res = await fetch(`${API_PAGES}?${PAGE}&${SIZE}&${MODE}&${CATEGORY}`,{ headers: getAuthorizedToken(), })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) { return null }
}

async function getAnnouncement() {
    try {

        const res = await fetch(`${API_ANNOUCEMENTS}`)
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) { return null }
}
async function getUser() {
    try {

        const res = await fetch(`${API_USERS}`,{ headers: getAuthorizedToken(), })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) { return null }
}
async function createUser(user)  {
    try {

        const res = await fetch(`${API_USERS}`, {
            method: 'POST',
            headers: getAuthorizedToken(),
            body: JSON.stringify(user)
        })
        return res.json()
    } catch (err) { return null }  
    
}
async function getAnnouncementById(id,count=false) {
    try {

        const res = await fetch(`${API_ANNOUCEMENTS}/${id}?count=${count}`, { headers: getAuthorizedToken(), })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) { return null }
}
async function getUserById(id) {
    try {

        const res = await fetch(`${API_USERS}/${id}`, { headers: getAuthorizedToken(), })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) { return null }
}
async function createAnnouncement(announcement)  {
    try {

        const res = await fetch(`${API_ANNOUCEMENTS}`, {
            method: 'POST',
            headers: getAuthorizedToken(),
            body: JSON.stringify(announcement)
        })
        return res.json()
    } catch (err) { return null }
    
}
async function putAnnouncement(announcement)  {
    try {

        const res = await fetch(`${API_ANNOUCEMENTS}/${announcement.id}`, {
            method: 'PUT',
            headers: getAuthorizedToken(),
            body: JSON.stringify(announcement)
        })
        if (res.ok) {}
        else throw new Error('Error, cannot update!')
    } catch (err) { return null } 
    finally { router.push("/admin/announcement/") }
}

async function putUser(user)  {
    try {

        const res = await fetch(`${API_USERS}/${user.id}`, {
            method: 'PUT',
            headers: getAuthorizedToken(),
            body: JSON.stringify(user)
        })
        return res.json()
    } catch (err) { return null }
}

async function deleteAnnouncement(id) {
    try {

        const res = await fetch(`${API_ANNOUCEMENTS}/${id}`, {method: 'DELETE', headers: getAuthorizedToken(),} )
        if (res.ok) {
            // console.log("Delete Successfully")
        } else throw new Error('cannot delete!')
    } catch (err) { return null }
}
async function deleteUser(id) {
    try {

        const res = await fetch(`${API_USERS}/${id}`, {method: 'DELETE', headers: getAuthorizedToken(), } )
        if (res.ok) {
            // console.log("Delete Successfully")
        } else throw new Error('cannot delete!')
    } catch (err) { return null }
}
async function matchPassword(user)  {
    try {
        const res = await fetch(`${API_USERS}/match`, {
            method: 'POST',
            headers: getAuthorizedToken(),
            body: JSON.stringify(user)
        })
        return res.status

    } catch (err) { return null }
    
}

const createNewToken = async (data) => {
    try {
        const res = await fetch(`${API_TOKEN}`, {
            method: "POST",
            headers: { 
            "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        })
        const tokenDetail = await res.json()
        if (res.ok) {
            const accessToken = tokenDetail.token
            const refreshToken = tokenDetail.refreshToken
            localStorage.setItem("accessToken", accessToken)
            localStorage.setItem("refreshToken", refreshToken)
            return res.status
        }
        else if (res.status === 401 || res.status === 404) {
            return res.status
        }
        else { throw new error(Error, `${res.status}`) }
    } catch (error) { return null }
  }
  



export { createNewToken,getAnnouncement,matchPassword,getAnnouncementById,putAnnouncement,createAnnouncement,deleteAnnouncement,isLoaded, 
    getUserAnnouncement,getUser,createUser,deleteUser,getUserById,putUser, isAuthenticated }