import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect:'/login'
  },
  {
    path: '/login',
    name: '登录页面',
    component:()=>import('../components/Login')
  },
  {
    path: '/index',
    name: '管理页面',
    component:()=>import('../components/Index'),
    children:[
      {
        path: '/index',
        redirect: '/index/database'
      },
      {
        path: 'database',
        name: '数据展示页面',
        component: () =>import('../components/index/Database')
      },
      {
        path: 'user_manage',
        name: '用户管理页面',
        component:()=>import('../components/index/UserManage')
      },
      {
        path: 'Movie_manage',
        name: '电影管理页面',
        component:()=>import('../components/index/MovieManage')
      },
      {
        path: 'cinema_manage',
        name: '影院管理页面',
        component:()=>import('../components/index/CinemaManage')
      },
      {
        path: 'hall_manage',
        name: '影厅管理页面',
        component:()=>import('../components/index/HallManage')
      },
      {
        path: 'movie_schedule',
        name: '电影排片页面',
        component:()=>import('../components/index/MovieSchedule')
      },
      {
        path: 'comment_manage',
        name: '评论管理页面',
        component:()=>import('../components/index/CommentManage')
      },
      {
        path: 'order_manage',
        name: '订单管理页面',
        component:()=>import('../components/index/OrderManage')
      },
    ]
  }
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
