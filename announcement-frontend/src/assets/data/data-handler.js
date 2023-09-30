import router from '../../router/index.js';

const API_HOST = import.meta.env.VITE_BASE_URL
const API_ANNOUCEMENTS = `${API_HOST}/api/announcements`
const API_USERS = `${API_HOST}/api/users`
const API_PAGES = `${API_HOST}/api/announcements/pages`

function sendPage(page) {
    alert("The request page is not available")
    router.push(page)
}

function isLoaded(data,page = "/announcement/",alert = true) {
    if (!data) {
        if (alert) sendPage(page)
        return false
    } 
    return true
}
async function getUserAnnouncement(mode,page,category,size) {
    try {
        const PAGE = page ? `page=${page}` : ''
        const SIZE = size ? `size=${size}` : ''
        const MODE = mode ? `mode=${mode}` : ''
        const CATEGORY = category ? `category=${category}` : ''
        const res = await fetch(`${API_PAGES}?${PAGE}&${SIZE}&${MODE}&${CATEGORY}`
        ,{
            headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` },
        })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) {}
}

async function getAnnouncement() {
    try {
        const res = await fetch(`${API_ANNOUCEMENTS}`)
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) {}
}
async function getUser() {
    try {
        const res = await fetch(`${API_USERS}`
        ,{
            headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` },
        })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) {}
}
async function createUser(user)  {
    try {
        const res = await fetch(`${API_USERS}`, {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(user)
        })
        return res.json()
    } catch (err) {   }  
    
}
async function getAnnouncementById(id,count=false) {
    try {
        const res = await fetch(`${API_ANNOUCEMENTS}/${id}?count=${count}`
        ,{
            headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` },
        })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) {}
}
async function getUserById(id) {
    try {
        const res = await fetch(`${API_USERS}/${id}`
        ,{
            headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` },
        })
        if (res.ok) return res.json()
        else throw new Error('Error, data is error!')
    } catch (error) {}
}
async function createAnnouncement(announcement)  {
    try {
        const res = await fetch(`${API_ANNOUCEMENTS}`, {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(announcement)
        })
        return res.json()
    } catch (err) {}
    
}
async function putAnnouncement(announcement)  {
    try {
        const res = await fetch(`${API_ANNOUCEMENTS}/${announcement.id}`, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(announcement)
        })
        if (res.ok) {}
        else throw new Error('Error, cannot update!')
    } catch (err) {    
    } finally { router.push("/admin/announcement/") }
}

async function putUser(user)  {
    try {
        const res = await fetch(`${API_USERS}/${user.id}`, {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(user)
        })
        return res.json()
    } catch (err) {}
}

async function deleteAnnouncement(id) {
    try {
        const res = await fetch(`${API_ANNOUCEMENTS}/${id}`, {method: 'DELETE'} )
        if (res.ok) {
            // console.log("Delete Successfully")
        } else throw new Error('cannot delete!')
    } catch (err) {}
}
async function deleteUser(id) {
    try {
        const res = await fetch(`${API_USERS}/${id}`, {method: 'DELETE'} )
        if (res.ok) {
            // console.log("Delete Successfully")
        } else throw new Error('cannot delete!')
    } catch (err) {}
}
async function matchPassword(user)  {
    try {
        const res = await fetch(`${API_USERS}/match`, {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify(user)
        })
        return res.status

    } catch (err) {}
    
}

const createNewToken = async (data) => {
    try {
      const res = await fetch(`${API_HOST}/api/token`, {
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

        console.log(accessToken);
        console.log(refreshToken);

        localStorage.setItem("accessToken", accessToken)
        localStorage.setItem("refreshToken", refreshToken)
      }
      else if (res.status === 401 || res.status === 404) {
        throw res.status
      }
        else { throw new error(Error, `${res.status}`) }
    } catch (error) {
        throw error
    }
  }
  



export { createNewToken,getAnnouncement,matchPassword,getAnnouncementById,putAnnouncement,createAnnouncement,deleteAnnouncement,isLoaded, getUserAnnouncement,getUser,createUser,deleteUser,getUserById,putUser }