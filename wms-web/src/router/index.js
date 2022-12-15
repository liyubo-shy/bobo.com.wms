import VueRouter from 'vue-router'

const routes = [



    {
        path: '/',
        name: 'login',
        component: () => import('../components/Login')
    },
    {
        path: '/Index',
        name: 'index',
        component: () => import('../components/Index'),
        children: [
            {
                path: '/Home',
                name: 'home',
                meta: {
                    title: '个人中心'
                },
                component: () => import('../components/Home')
            },
            {
                path: '/Main',
                name: 'main',
                meta: {
                    title: '首页'
                },
                component: () => import('../components/Main')
            },
            {
                path: '/Analysis',
                name: 'analysis',
                component: () => import('../components/Analysis'),

            },
            // {
            //     path: '/404',
            //     name: '404',
            //     component: () => import('../components/Page404'),
            //     meta: {
            //         title: '404NotFound',
            //         needLogin: false
            //     }
            // },


        ]

    },
    // {
    //     path: '*',
    //     redirect: '/404',
    //     hidden: true
    // }
]
const router = new VueRouter(
    {
        routes,
        mode: "history"
    }
)

export function resetRouter() {
    router.macher = new VueRouter({
        mode: 'history',
        routes: []
    }).macher
}

// 防止连续点击多次路由报错
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(err => err)
}

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

export default router;
