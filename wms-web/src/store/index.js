import vue from 'vue'
import Vuex from 'vuex'
import router from "../router"
import createPersistedState from 'vuex-persistedstate'

vue.use(Vuex)

function addNewRoute(menuList) {
    // console.log(menuList, '111')
    let routes = router.options.routes
    // console.log(routes, '2222')
    routes.forEach(routeItem => {
        if (routeItem.path === "/Index") {
            menuList.forEach(menu => {
                let childRoute = {
                    path: '/' + menu.menuclick,
                    name: menu.menuclick,
                    meta: {
                        title: menu.menuname
                    },
                    component: () => import('../components/' + menu.menucomponent)
                }
                routeItem.children.push(childRoute)
            })
        }
    })
    // resetRouter()
    for (let x of routes) {
        router.addRoute(x)

    }


}

export default new Vuex.Store({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state, menuList) {
            state.menu = menuList
            addNewRoute(menuList)
        },
        setRouter(state, menuList) {
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menu
        }
    },
    plugins:[createPersistedState()]
})
