import { createRouter, createWebHashHistory } from 'vue-router'

export default createRouter({
    //路由模式设置
    history: createWebHashHistory(),
    routes: [
        {
            path: "/",
            component: () => import("@/pages/home/HomePage.vue"),
            children: [
                {
                    path: "/sys/menu",
                    component: () => import("@/pages/sys/menu/MenuIndex.vue"),
                }, {
                    path: "/sys/dict",
                    component: () => import("@/pages/sys/dict/DictIndex.vue"),
                }, {
                    path: "/sys/schedule",
                    component: () => import("@/pages/sys/Schedule.vue"),
                }, {
                    path: "/sys/config",
                    component: () => import("@/pages/sys/Config.vue"),
                }, {
                    path: "/sys/user",
                    component: () => import("@/pages/sys/User.vue"),
                }, {
                    path: "/sys/role",
                    component: () => import("@/pages/sys/Role.vue"),
                }, {
                    path: "/sys/permission",
                    component: () => import("@/pages/sys/Permission.vue"),
                }, {
                    path: "/account/adjust",
                    component: () => import("@/pages/account/Adjust.vue"),
                }, {
                    path: "/account/subject",
                    component: () => import("@/pages/account/Subject.vue"),
                }, {
                    path: "/statistics/Report",
                    component: () => import("@/pages/statistics/Report.vue"),
                },{
                    path: "/:catchAll(.*)",
                    component: () => import("@/pages/404.vue"),
                }
            ]
        },
        {
            path: "/login",
            component: () => import("@/pages/Login.vue")
        }],
    scrollBehavior() {
        return {
            left: 0,
            top: 0
        }
    }
})