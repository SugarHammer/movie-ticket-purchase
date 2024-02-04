import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './vantui'
import 'reset-css/reset.css'
import './assets/css/style.css'
import cookies from 'vue-cookies'
import './elementui'
import LyTab from 'ly-tab'

Vue.use(LyTab)
Vue.use(cookies)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
