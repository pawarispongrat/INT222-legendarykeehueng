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
const API_SUBSCRIBE = `${API_HOST}/api/subscription/subscribe`
const API_UNSUBSCRIBE = `${API_HOST}/api/subscription/unsubscribe`

const API_OTP = `${API_HOST}/api/subscription/subscribe-otp`

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
export async function getUserAnnouncementById(id,count=false) {
    return await new FetchHandler(`${API_ANNOUNCEMENTS}/${id}?count=${count}`).json()
}
async function getUserById(id) {
    return await new FetchHandler(`${API_USERS}/${id}`).authorize().json()
}
async function createAnnouncement(announcement)  {

    const test = await new FetchHandler(API_ANNOUNCEMENTS).authorize().post(announcement).json()
    console.log(test);
    return test
}

async function putAnnouncement(announcement)  {
    return await new FetchHandler(`${API_ANNOUNCEMENTS}/${announcement.id}`).authorize().put(announcement).json()
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
async function subscribe(Email,categoryId){
    // console.log(await new FetchHandler(API_SUBSCRIBE).post({subscriberEmail:Email,categoryId:categoryId}).json());
    const response = await new FetchHandler(API_SUBSCRIBE).post({'subscriberEmail':Email,'categoryId':categoryId}).response()
    console.log(response.status);
    return response.status
}
async function unsubscribe(Email,hashEmail,categoryId){
    // console.log(await new FetchHandler(API_SUBSCRIBE).post({subscriberEmail:Email,categoryId:categoryId}).json());
    const response = await new FetchHandler(API_UNSUBSCRIBE).post({'subscriberEmail':Email,'hashEmail':hashEmail,'categoryId':categoryId}).json()
    console.log(response);
    return response
}
async function verifyOtp(Email,otp) {
    const response = await new FetchHandler(API_OTP).post({'subscriberEmail':Email,'otp':otp}).response()
    return response.json()
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
    getUser,createUser,deleteUser,getUserById,putUser,subscribe,verifyOtp,unsubscribe }