import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import './assets/global.css'
import axios from "axios";
import VueRouter from "vue-router";
import router from "@/router";
import store from './store';
import echarts from 'echarts'
import ECharts from 'vue-echarts'
import Plugin from 'v-fit-columns';

// Vue.prototype.$httpUrl="http://10.98.13.113:8090/"
Vue.prototype.$httpUrl = "http://localhost:8090"
// Vue.prototype.$httpUrl="http://622e004e.r7.cpolar.top"
Vue.prototype.$axios = axios;
Vue.prototype.$echarts = echarts;
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(Plugin);

Vue.component('ECharts', ECharts);
// Vue.use(ElementUI);
// Vue.prototype.$ELEMENT = { size: 'medium' };
Vue.use(ElementUI, {size: 'small'});

// axios.interceptors.request.use(config =>{
//       console.log(config)
//       //为请求头对象，添加token验证的Authoorization字段
//       config.headers.authorization = window.localStorage.getItem('token')
//       return config
//     }
// )

// export function getToken() {
//     return JSON.parse(sessionStorage.getItem('CurUser')).token;
// }
// axios.defaults.headers.common['authorization'] = getToken()
// axios.interceptors.request.use(config =>{
//         console.log(config)
//         //为请求头对象，添加token验证的Authoorization字段
//         config.headers['authorization'] = JSON.parse(sessionStorage.getItem('CurUser')).token;
//         return config
//     }
// )
axios.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    if (sessionStorage.getItem('CurUser')) {
        config.headers['authorization'] = JSON.parse(sessionStorage.getItem('CurUser')).token;
    }
    console.log(config)
    return config
}, error => {
    return Promise.reject(error)
});

// let token = JSON.parse(sessionStorage.getItem('CurUser')).token
// axios.interceptors.request.use(
//     config => {
//         if(token) config.headers['authorization'] = token
//         return config
//     },
//     error => {
//         console.log(error)
//         return Promise.reject(error)
//     }
// )
// let commonURL = "http://192.168.50.115:8081";

// request拦截器，将用户token放入头中


new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount('#app');

