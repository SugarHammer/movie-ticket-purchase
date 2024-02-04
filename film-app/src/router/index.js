import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect:'/index',
  },
  {
    path: '/index',
    name: '首页',
    component: () => import('../components/Index'),
    meta:{showTabBar:true}
  },
  {
    path: '/search',
    name: '搜索页面',
    component: () => import('../components/index/Search')
  },
  {
    path: '/movie',
    name: '电影影片',
    component: () => import('../components/Movie'),
    meta:{showTabBar:true}
  },
  {
    path: '/movie_detail',
    name: '电影详情',
    component: () => import('../components/movie/MovieDetail')
  },
  {
    path: '/comment_panel',
    name: '评论页面',
    component: () => import('../components/movie/Comment')
  },
  {
    path: '/cinema',
    name: '影院',
    component: () => import('../components/Cinema'),
    meta:{showTabBar:true}
  },
  {
    path: '/cinema_detail',
    name: '影院详情页面',
    component: () => import('../components/cinema/CinemaDetail')
  },
  {
    path: '/select_cinema',
    name: '购票影院界面',
    component: () => import('../components/cinema/SelectCinema')
  },
  {
    path: '/user',
    name: '用户',
    component: () => import('../components/User'),
    meta:{showTabBar:true}
  },
  {
    path: '/user_info',
    name: '用户信息',
    component: () => import('../components/user/UserInfo')
  },
  {
    path: '/user_order',
    name: '用户订单',
    component: () => import('../components/user/UserOrder')
  },
  {
    path: '/user_movie',
    name: '用户收藏',
    component: () => import('../components/user/UserMovie')
  },
  {
    path: '/user_history',
    name: '观看历史',
    component: () => import('../components/user/UserHistory')
  },
  {
    path: '/login',
    name: '用户登录',
    component: () => import('../components/Login')
  },
  {
    path: '/select_seat',
    name: '选择座位',
    component: () => import('../components/deal/SelectSeat')
  },
  {
    path: '/submit_order',
    name: '订单页面',
    component: () => import('../components/deal/Order')
  },
  {
    path: '/pay',
    name: '支付页面',
    component: () => import('../components/deal/Pay')
  }
]

const router = new VueRouter({
  routes
})

export default router
