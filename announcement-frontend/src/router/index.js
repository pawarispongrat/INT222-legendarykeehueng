import { createRouter,createWebHistory } from "vue-router";
import MainAdmin from "@/views/admin/MainAdmin.vue"
import PageNotFound from "@/views/PageNotFound.vue"
import UserAnnouncement from "@/views/user/Announcement.vue"

import User from '@/views/admin/user/User.vue'
import UserDetails from "@/views/user/AnnouncementDetails.vue"
import MatchPassword from "@/views/admin/MatchPassword.vue"
import AddUser from "@/views/admin/user/Add.vue"
import EditUser from "@/views/admin/user/Edit.vue"
import UserUnSubscribe from "@/views/user/Unsubscribe.vue"
import Announcement from '@/views/admin/announcement/Announcement.vue'
import AddAnnouncement from '@/views/admin/announcement/Add.vue'
import AnnouncementDetails from '@/views/admin/announcement/Details.vue'
import EditAnnouncement from '@/views/admin/announcement/Edit.vue'

import Login from "@/views/Login.vue"
import {isEditor, isAdmin, isTokenExpired, getRefreshToken, getAccessToken} from "@/assets/data/tokenStorage";
import {revokeToken} from "@/assets/data/dataHandler";
import {Auth} from "@/assets/data/msalAuthenticate";

const isAuthenticated = async () => {
    if (isTokenExpired()) {
        const refreshToken = getRefreshToken()
        const status = await revokeToken(refreshToken)
        return status && status !== 401 && status !== 403 && status !== 404
    }
    return true
}
const isLogin = (to,from,next) => {
    if (Auth.isLoggedIn() || getAccessToken()) next("/announcement")
    else next()
}
const guardRoutes = async (to,from,next) => {
    // await isAuthenticated()
    const authenticated = await isAuthenticated()
    if (authenticated && isEditor()) next()
    else next("/login")
}
const guardAdmin = async (to,from,next) => {
    const authenticated = await isAuthenticated()
    if (authenticated && isAdmin()) next()
    else next("/admin/announcement")
}
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path: '/login',
            name: 'Login',
            component: async () => Login,
            beforeEnter: isLogin
        },
        {
            path: '/',
            redirect: { name: 'Login' }
        },
        {
            path: '/announcement/',
            name: 'UserAnnouncement',
            component:  UserAnnouncement
        },
        {
            path: '/unsubscribe/',
            name: 'UserUnSubscribe',
            component: UserUnSubscribe
          },
        {
            path: '/announcement/:id',
            name: 'UserDetails',
            component: async () => UserDetails
        },
        {
            path: '/admin/',
            name: 'MainAdmin',
            component: MainAdmin,
            redirect: { name: 'User' },
            children: [
                { path: '/admin/announcement/',name: 'Announcement',component: Announcement, },
                { path: '/admin/announcement/add', name: 'AddAnnouncement', component: AddAnnouncement,},
                { path: '/admin/announcement/:id/details', name: 'Details', component: AnnouncementDetails,},
                { path: '/admin/announcement/:id/edit', name: 'EditAnnouncement', component: EditAnnouncement,},
                { path: '/admin/user/',name: 'User',component: User, beforeEnter: guardAdmin },
                { path: '/admin/user/add',name: 'AddUser',component: AddUser, beforeEnter: guardAdmin },
                { path: '/admin/user/:id/edit',name: 'EditUser',component: EditUser, beforeEnter: guardAdmin },
                { path: '/admin/user/match',name: 'MatchPassword',component: MatchPassword, beforeEnter: guardAdmin }
            ],
            beforeEnter: guardRoutes

        },
        {
            path: '/:notfoundpath(.*)',
            name: 'PageNotFound',
            component: PageNotFound
        },
        
        ],
        linkActiveClass: "active", // active class for non-exact links.
        linkExactActiveClass: "active" // active class for *exact* links.
})

export default router