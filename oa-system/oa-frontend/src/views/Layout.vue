<template>
  <el-container class="main-container">
    <el-aside width="220px" class="sidebar">
      <div style="height: 60px; display: flex; align-items: center; justify-content: center; color: white; font-size: 18px; font-weight: bold; border-bottom: 1px solid #1f2d3d;">
        OA管理系统
      </div>
      <el-menu
        :default-active="$route.path"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/self">
          <i class="el-icon-user"></i>
          <span slot="title">个人信息</span>
        </el-menu-item>

        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-document"></i>
            <span>日常管理</span>
          </template>
          <el-menu-item index="/claim-vouchers/deal">
            <span>待处理报销单</span>
          </el-menu-item>
          <el-menu-item index="/claim-vouchers">
            <span>我的报销单</span>
          </el-menu-item>
          <el-menu-item index="/claim-vouchers/add">
            <span>填写报销单</span>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>基础信息管理</span>
          </template>
          <el-menu-item index="/departments">
            <span>部门管理</span>
          </el-menu-item>
          <el-menu-item index="/employees">
            <span>员工管理</span>
          </el-menu-item>
          <el-menu-item index="/logs">
            <span>操作日志</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header" height="60px">
        <div class="header-title">{{ $route.meta.title || 'OA管理系统' }}</div>
        <div>
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link" style="cursor: pointer; color: #333;">
              {{ userName }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="self">个人信息</el-dropdown-item>
              <el-dropdown-item command="password">修改密码</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="content-wrapper">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { logout } from '@/api/auth'

export default {
  name: 'Layout',
  computed: {
    userName() {
      return this.$store.state.user ? this.$store.state.user.name : '未知用户'
    }
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case 'self':
          this.$router.push('/self')
          break
        case 'password':
          this.$router.push('/change-password')
          break
        case 'logout':
          this.handleLogout()
          break
      }
    },
    handleLogout() {
      this.$confirm('确认退出登录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        logout().then(() => {
          this.$store.dispatch('logout')
          this.$router.push('/login')
          this.$message.success('已退出登录')
        })
      })
    }
  }
}
</script>
