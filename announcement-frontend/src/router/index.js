import { createRouter,createWebHistory } from "vue-router";
import Announcement from "../views/admin/announcement/Announcement.vue"
import AnnouncementDetails from "../views/admin/announcement/Details.vue"
import PageNotFound from "../views/PageNotFound.vue"
import AddAnnouncement from "../views/admin/announcement/AddAnnouncement.vue"
import EditAnnouncement from "../views/admin/announcement/EditAnnouncement.vue"
import UserAnnouncement from "../views/user/Announcement.vue"
import UserDetails from "../views/admin/user/Details.vue"
import User from "../views/admin/user/User.vue"
import AddUser from "../views/admin/user/AddUser.vue"
import EditUser from "../views/admin/user/EditUser.vue"
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
            path: '/admin/announcement/',
            name: 'Announcement',
            component: Announcement,
        },
        {
            path: '/admin/announcement/:id',
            name: 'AnnouncementDetails',
            component: AnnouncementDetails,
        },
        {
            path: '/:notfoundpath(.*)',
            name: 'PageNotFound',
            component: PageNotFound        
        },
        {
            path: '/admin/announcement/add',
            name: 'AddAnnouncement',
            component: AddAnnouncement,
        },
        {
            path: '/admin/announcement/:id/edit',
            name: 'EditAnnouncement',
            component: EditAnnouncement,
        },
        {
            path: '/admin/user/',
            name: 'User',
            component: User,
        },
        {
            path: '/admin/user/add',
            name: 'AddUser',
            component: AddUser,
        },
        {
            path: '/admin/user/:id/edit',
            name: 'EditUser',
            component: EditUser, 
        }
    
]
})
export default router