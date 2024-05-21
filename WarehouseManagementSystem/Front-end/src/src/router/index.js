import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/dashboard',
    component: () => import('@/views/Dashboard.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: 'inventory'
      },
      {
        path: 'inventory',
        component: () => import('@/views/Inventory.vue')
      },
      {
        path: 'permissions',
        component: () => import('@/views/Permissions.vue')
      },
      {
        path: 'myinfo',
        component: () => import('@/views/MyInfo.vue')
      },
      {
        path: 'notification',
        component: () => import('@/views/Notification.vue')
      }
      
    ]
  },
  
  {
    path: '*',
    redirect: '/login'
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

// router.beforeEach((to, from, next) => {
//   const isAuthenticated = true; // 根据实际情况设置是否认证
//   const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

//   if (requiresAuth && !isAuthenticated) {
//     next('/login');
//   } else {
//     next();
//   }
// });

export default router;
