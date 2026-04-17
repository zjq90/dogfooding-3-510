import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'
import Self from '@/views/Self.vue'
import ChangePassword from '@/views/ChangePassword.vue'
import ClaimVoucherSelf from '@/views/claim/ClaimVoucherSelf.vue'
import ClaimVoucherDeal from '@/views/claim/ClaimVoucherDeal.vue'
import ClaimVoucherAdd from '@/views/claim/ClaimVoucherAdd.vue'
import ClaimVoucherDetail from '@/views/claim/ClaimVoucherDetail.vue'
import ClaimVoucherUpdate from '@/views/claim/ClaimVoucherUpdate.vue'
import ClaimVoucherCheck from '@/views/claim/ClaimVoucherCheck.vue'
import EmployeeList from '@/views/employee/EmployeeList.vue'
import EmployeeAdd from '@/views/employee/EmployeeAdd.vue'
import EmployeeUpdate from '@/views/employee/EmployeeUpdate.vue'
import DepartmentList from '@/views/department/DepartmentList.vue'
import DepartmentAdd from '@/views/department/DepartmentAdd.vue'
import DepartmentUpdate from '@/views/department/DepartmentUpdate.vue'
import LogList from '@/views/LogList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/main',
    component: Layout,
    redirect: '/claim/deal',
    children: [
      {
        path: '/self',
        name: 'Self',
        component: Self
      },
      {
        path: '/change-password',
        name: 'ChangePassword',
        component: ChangePassword
      },
      {
        path: '/claim/self',
        name: 'ClaimVoucherSelf',
        component: ClaimVoucherSelf
      },
      {
        path: '/claim/deal',
        name: 'ClaimVoucherDeal',
        component: ClaimVoucherDeal
      },
      {
        path: '/claim/add',
        name: 'ClaimVoucherAdd',
        component: ClaimVoucherAdd
      },
      {
        path: '/claim/detail/:id',
        name: 'ClaimVoucherDetail',
        component: ClaimVoucherDetail
      },
      {
        path: '/claim/update/:id',
        name: 'ClaimVoucherUpdate',
        component: ClaimVoucherUpdate
      },
      {
        path: '/claim/check/:id',
        name: 'ClaimVoucherCheck',
        component: ClaimVoucherCheck
      },
      {
        path: '/employee/list',
        name: 'EmployeeList',
        component: EmployeeList
      },
      {
        path: '/employee/add',
        name: 'EmployeeAdd',
        component: EmployeeAdd
      },
      {
        path: '/employee/update/:id',
        name: 'EmployeeUpdate',
        component: EmployeeUpdate
      },
      {
        path: '/department/list',
        name: 'DepartmentList',
        component: DepartmentList
      },
      {
        path: '/department/add',
        name: 'DepartmentAdd',
        component: DepartmentAdd
      },
      {
        path: '/department/update/:id',
        name: 'DepartmentUpdate',
        component: DepartmentUpdate
      },
      {
        path: '/log/list',
        name: 'LogList',
        component: LogList
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
