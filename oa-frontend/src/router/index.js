import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/profile',
    children: [
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue')
      },
      {
        path: 'change-password',
        name: 'ChangePassword',
        component: () => import('../views/ChangePassword.vue')
      },
      {
        path: 'logs',
        name: 'Logs',
        component: () => import('../views/Logs.vue')
      },
      {
        path: 'departments',
        name: 'Departments',
        component: () => import('../views/Departments.vue')
      },
      {
        path: 'employees',
        name: 'Employees',
        component: () => import('../views/Employees.vue')
      },
      {
        path: 'claim-voucher/add',
        name: 'ClaimVoucherAdd',
        component: () => import('../views/ClaimVoucherAdd.vue')
      },
      {
        path: 'claim-voucher/edit/:id',
        name: 'ClaimVoucherEdit',
        component: () => import('../views/ClaimVoucherEdit.vue')
      },
      {
        path: 'claim-voucher/self',
        name: 'ClaimVoucherSelf',
        component: () => import('../views/ClaimVoucherSelf.vue')
      },
      {
        path: 'claim-voucher/deal',
        name: 'ClaimVoucherDeal',
        component: () => import('../views/ClaimVoucherDeal.vue')
      },
      {
        path: 'claim-voucher/detail/:id',
        name: 'ClaimVoucherDetail',
        component: () => import('../views/ClaimVoucherDetail.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  
  if (to.path === '/login') {
    if (userStore.isLoggedIn) {
      next('/')
    } else {
      next()
    }
  } else {
    if (userStore.isLoggedIn) {
      next()
    } else {
      try {
        await userStore.getInfo()
        next()
      } catch (error) {
        next('/login')
      }
    }
  }
})

export default router
