import Vue from 'vue'
import VueRouter from 'vue-router'
import { getCurrentUser } from '@/api/auth'
import store from '../store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { public: true }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    redirect: '/self',
    children: [
      {
        path: 'self',
        name: 'Self',
        component: () => import('../views/Self.vue'),
        meta: { title: '个人信息' }
      },
      {
        path: 'change-password',
        name: 'ChangePassword',
        component: () => import('../views/ChangePassword.vue'),
        meta: { title: '修改密码' }
      },
      {
        path: 'employees',
        name: 'EmployeeList',
        component: () => import('../views/employee/List.vue'),
        meta: { title: '员工管理' }
      },
      {
        path: 'employees/add',
        name: 'EmployeeAdd',
        component: () => import('../views/employee/Form.vue'),
        meta: { title: '添加员工' }
      },
      {
        path: 'employees/edit/:id',
        name: 'EmployeeEdit',
        component: () => import('../views/employee/Form.vue'),
        meta: { title: '编辑员工' }
      },
      {
        path: 'departments',
        name: 'DepartmentList',
        component: () => import('../views/department/List.vue'),
        meta: { title: '部门管理' }
      },
      {
        path: 'departments/add',
        name: 'DepartmentAdd',
        component: () => import('../views/department/Form.vue'),
        meta: { title: '添加部门' }
      },
      {
        path: 'departments/edit/:id',
        name: 'DepartmentEdit',
        component: () => import('../views/department/Form.vue'),
        meta: { title: '编辑部门' }
      },
      {
        path: 'claim-vouchers',
        name: 'MyClaimVouchers',
        component: () => import('../views/claimVoucher/MyList.vue'),
        meta: { title: '我的报销单' }
      },
      {
        path: 'claim-vouchers/add',
        name: 'ClaimVoucherAdd',
        component: () => import('../views/claimVoucher/Form.vue'),
        meta: { title: '填写报销单' }
      },
      {
        path: 'claim-vouchers/edit/:id',
        name: 'ClaimVoucherEdit',
        component: () => import('../views/claimVoucher/Form.vue'),
        meta: { title: '编辑报销单' }
      },
      {
        path: 'claim-vouchers/detail/:id',
        name: 'ClaimVoucherDetail',
        component: () => import('../views/claimVoucher/Detail.vue'),
        meta: { title: '报销单详情' }
      },
      {
        path: 'claim-vouchers/deal',
        name: 'DealClaimVouchers',
        component: () => import('../views/claimVoucher/DealList.vue'),
        meta: { title: '待处理报销单' }
      },
      {
        path: 'claim-vouchers/check/:id',
        name: 'ClaimVoucherCheck',
        component: () => import('../views/claimVoucher/Check.vue'),
        meta: { title: '审核报销单' }
      },
      {
        path: 'logs',
        name: 'LogList',
        component: () => import('../views/log/List.vue'),
        meta: { title: '操作日志' }
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach(async (to, from, next) => {
  if (to.meta && to.meta.public) {
    next()
    return
  }

  try {
    const user = await store.dispatch('fetchUser')
    if (user) {
      next()
    } else {
      next('/login')
    }
  } catch (error) {
    next('/login')
  }
})

export default router
