import { createRouter,createWebHistory } from "vue-router";
import MainAdmin from "@/views/admin/MainAdmin.vue"
import PageNotFound from "@/views/PageNotFound.vue"
import UserAnnouncement from "@/views/user/Announcement.vue"
import UserDetails from "@/views/admin/user/Details.vue"
import MatchPassword from "@/views/admin/MatchPassword.vue"
import AddUser from "@/views/admin/user/Add.vue"
import EditUser from "@/views/admin/user/Edit.vue"
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:[
        {
            path: '/',
            redirect: { name: 'UserAnnouncement' }
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
                { path: '/admin/announcement/',name: 'Announcement',component: () => import('@/views/admin/announcement/Announcement.vue'), },
                { path: '/admin/announcement/add', name: 'AddAnnouncement', component: () => import('@/views/admin/announcement/Add.vue'),},
                { path: '/admin/announcement/:id/details', name: 'Details', component: () => import('@/views/admin/announcement/Details.vue'),},
                { path: '/admin/announcement/:id/edit', name: 'EditAnnouncement', component: () => import('@/views/admin/announcement/Edit.vue'),},
                { path: '/admin/user/',name: 'User',component: () => import('@/views/admin/user/User.vue'), },
                { path: '/admin/user/add',name: 'AddUser',component: AddUser },
                { path: '/admin/user/:id/edit',name: 'EditUser',component: EditUser },
                { path: '/admin/user/match',name: 'MatchPassword',component: MatchPassword }
            ]
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