import { createRouter,createWebHistory } from "vue-router";
import MainAdmin from "@/views/admin/MainAdmin.vue"
import PageNotFound from "@/views/PageNotFound.vue"
import UserAnnouncement from "@/views/user/Announcement.vue"

import User from '@/views/admin/user/User.vue'
import UserDetails from "@/views/admin/user/Details.vue"
import MatchPassword from "@/views/admin/MatchPassword.vue"
import AddUser from "@/views/admin/user/Add.vue"
import EditUser from "@/views/admin/user/Edit.vue"

import Announcement from '@/views/admin/announcement/Announcement.vue'
import AddAnnouncement from '@/views/admin/announcement/Add.vue'
import AnnouncementDetails from '@/views/admin/announcement/Details.vue'
import EditAnnouncement from '@/views/admin/announcement/Edit.vue'

import Login from "@/views/Login.vue"
import { isAuthenticated } from "@/assets/data/dataHandler";

const guardRoutes = async (to,from,next) => {
    const authenticated = await isAuthenticated()
    if (authenticated) next()
    else next("/login")
}
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/',
            redirect: { name: 'Login' }
        },
        {
            path: '/announcement/',
            name: 'UserAnnouncement',
            component: UserAnnouncement
        },
        {
            path: '/announcement/:id',
            name: 'UserDetails',
            component: UserDetails
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
                { path: '/admin/user/',name: 'User',component: User, },
                { path: '/admin/user/add',name: 'AddUser',component: AddUser },
                { path: '/admin/user/:id/edit',name: 'EditUser',component: EditUser },
                { path: '/admin/user/match',name: 'MatchPassword',component: MatchPassword }
            ],
            beforeEnter: (to,from,next) => guardRoutes(to,from,next)
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