import router from '../../router/index.js';
import {
    authorizeToken,
    getAuthorizedToken,
    getRefreshToken,
    setAccessToken,
    setRefreshToken
} from "@/assets/data/tokenStorage";

const API_HOST = import.meta.env.VITE_BASE_URL
const API_ANNOUNCEMENTS = `${API_HOST}/api/announcements`
const API_USERS = `${API_HOST}/api/users`
const API_PAGES = `${API_HOST}/api/announcements/pages`
const API_TOKEN = `${API_HOST}/api/token`

function sendPage(page) {
    alert("The request page is not available")
    router.push(page)
}

//GET STATUS TO CHECK THAT TOKEN WORKS!
async function isAuthenticated() {
    return await validateToken() !== null
}
//REVOKE TOKEN
async function validateToken() {
    try {
        const refreshToken = getRefreshToken()
        const status = await matchPassword(null) //IS EXPIRED

        return (status === 401 && refreshToken !== null) ? revokeToken(refreshToken) : null
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
        const response = await fetch(`${API_TOKEN}`, { headers: authorizeToken(refreshToken) })
        const tokenDetail = await response.json()
        return response.ok ? setAccessToken(tokenDetail.token, response.status) : null
    } catch (error) { return null }
}

async function getUserAnnouncement(mode,page,category,size) {
    try {
        const PAGE = page ? `page=${page}&` : ''
        const SIZE = size ? `size=${size}&` : ''
        const MODE = mode ? `mode=${mode}&` : ''
        const CATEGORY = category ? `category=${category}` : ''
        const res = await fetch(`${API_PAGES}?${PAGE}${SIZE}${MODE}${CATEGORY}`,{ headers: getAuthorizedToken(), })
        return res.ok ? res.json() : null
    } catch (error) { return null }
}

async function getAnnouncement() {
    try {
        const res = await fetch(`${API_ANNOUNCEMENTS}`)
        return res.ok ? res.json() : null
    } catch (error) { return null }
}
async function getUser() {
    try {
        const res = await fetch(`${API_USERS}`,{ headers: getAuthorizedToken(), })
        return res.ok ? res.json() : null
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
        const res = await fetch(`${API_ANNOUNCEMENTS}/${id}?count=${count}`, { headers: getAuthorizedToken(), })
        return res.ok ? res.json() : null
    } catch (error) { return null }
}
async function getUserById(id) {
    try {

        const res = await fetch(`${API_USERS}/${id}`, { headers: getAuthorizedToken(), })
        return res.ok ? res.json() : null
    } catch (error) { return null }
}
async function createAnnouncement(announcement)  {
    try {

        const res = await fetch(`${API_ANNOUNCEMENTS}`, {
            method: 'POST',
            headers: getAuthorizedToken(),
            body: JSON.stringify(announcement)
        })
        return res.json()
    } catch (err) { return null }
    
}
async function putAnnouncement(announcement)  {
    try {
        const res = await fetch(`${API_ANNOUNCEMENTS}/${announcement.id}`, {
            method: 'PUT',
            headers: getAuthorizedToken(),
            body: JSON.stringify(announcement)
        })
        return res.ok ? res.json() : null
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
        const res = await fetch(`${API_ANNOUNCEMENTS}/${id}`, {method: 'DELETE', headers: getAuthorizedToken(),} )
        return res.ok ? res.json() : null
    } catch (err) { return null }
}
async function deleteUser(id) {
    try {
        const res = await fetch(`${API_USERS}/${id}`, {method: 'DELETE', headers: getAuthorizedToken(), } )
        return res.ok ? res.json() : null
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
    } catch (error) { return null }
}

const createNewToken = async (data) => {
    try {
        const res = await fetch(`${API_TOKEN}`, {
            method: "POST",
            headers: { 'Content-type': 'application/json' },
            body: JSON.stringify(data)
        })
        const tokenDetail = await res.json()
        if (res.ok) {
            setAccessToken(tokenDetail.token)
            setRefreshToken(tokenDetail.refreshToken)
            return res.status
        }
        else return res?.status ?? null
    } catch (error) { return null }
  }
  



export { createNewToken,getAnnouncement,matchPassword,getAnnouncementById,putAnnouncement,createAnnouncement,deleteAnnouncement,isLoaded, 
    getUserAnnouncement,getUser,createUser,deleteUser,getUserById,putUser, isAuthenticated }