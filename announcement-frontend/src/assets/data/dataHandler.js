import router from '@/router/index.js';
import {
    setAccessToken,
    setRefreshToken
} from "@/assets/data/tokenStorage";
import FetchHandler from "@/assets/data/fetchHandler";

const API_HOST = import.meta.env.VITE_BASE_URL
const API_ANNOUNCEMENTS = `${API_HOST}/api/announcements`
const API_USERS = `${API_HOST}/api/users`
const API_PAGES = `${API_HOST}/api/announcements/pages`
const API_TOKEN = `${API_HOST}/api/token`


async function isLoaded(data,page = "/announcement/",isAlert = true) {
    if (!data) {
        if (isAlert) {
            alert("The request page is not available")
            await router.push(page)
        }
        return false
    } 
    return true
}

export async function revokeToken(refreshToken) {
    const response = await new FetchHandler(API_TOKEN).bearer(refreshToken).response()
    const details = await response?.json()
    if (response?.ok) setAccessToken(details.token)
    return response?.status
}

function getAnnouncementParams(mode,page,category,size) {
    return [
        page ? `page=${page}` : '',
        size ? `size=${size}` : '',
        mode ? `mode=${mode}` : '',
        category ? `category=${category}` : ''
    ].filter(Boolean).join('&')
}

export async function getUserAnnouncement(mode,page,category,size) {
    const query = getAnnouncementParams(mode,page,category,size)
    return await new FetchHandler(`${API_PAGES}?${query}`).json()
}
export async function getAdminAnnouncement(mode,page,category,size) {
    const query = getAnnouncementParams(mode,page,category,size)
    return await new FetchHandler(`${API_PAGES}?${query}`).authorize().json()
}

async function getAnnouncement() {
    return await new FetchHandler(API_ANNOUNCEMENTS).json()
}
async function getUser() {
    return await new FetchHandler(API_USERS).authorize().json()
}
async function createUser(user)  {
    return await new FetchHandler(API_USERS).authorize().post(user).json()
}
async function getAnnouncementById(id,count=false) {
    return await new FetchHandler(`${API_ANNOUNCEMENTS}/${id}?count=${count}`).authorize().json()
}
async function getUserById(id) {
    return await new FetchHandler(`${API_USERS}/${id}`).authorize().json()
}
async function createAnnouncement(announcement)  {
    return await new FetchHandler(API_ANNOUNCEMENTS).authorize().post(announcement).json()
    
}
async function putAnnouncement(announcement)  {
    const put = await new FetchHandler(`${API_ANNOUNCEMENTS}/${announcement.id}`).authorize().put(announcement).json()
    await router.push("/admin/announcement/")
    return put
}

async function putUser(user)  {
    return await new FetchHandler(`${API_USERS}/${user.id}`).authorize().put(user).json()
}

async function deleteAnnouncement(id) {
    return new FetchHandler(`${API_ANNOUNCEMENTS}/${id}`).authorize().delete().json()
}
async function deleteUser(id) {
    return new FetchHandler(`${API_USERS}/${id}`).authorize().delete().json()
}
 async function matchPassword(user)  {
    const response  = await new FetchHandler(`${API_USERS}/match`).authorize().post(user).response()
    return response?.status
}

const createNewToken = async (data) => {
    const response = await new FetchHandler(API_TOKEN)
        .header("Content-Type","application/json")
        .post(data).response()
    const details = await response.json()
    if (response.ok) {
        setAccessToken(details.token)
        setRefreshToken(details.refreshToken)
        return response.status
    }
    return response?.status
}
  



export { createNewToken,getAnnouncement,matchPassword,getAnnouncementById,putAnnouncement,createAnnouncement,deleteAnnouncement,isLoaded,
    getUser,createUser,deleteUser,getUserById,putUser }