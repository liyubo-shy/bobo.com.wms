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
// Vue.prototype.$httpUrl="http://10.205.103.134:8090"
Vue.prototype.$httpUrl="http://localhost:8090"
Vue.prototype.$axios =axios;
Vue.prototype.$echarts = echarts;
Vue.config.productionTip = false;
Vue.use(VueRouter);
// Vue.use(ElementUI);
// Vue.prototype.$ELEMENT = { size: 'medium' };
Vue.use(ElementUI,{size:'small'});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');

