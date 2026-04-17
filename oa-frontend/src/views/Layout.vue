<template>
  <div class="layout-container">
    <header class="layout-header">
      <div class="layout-brand">CD4356</div>
      <el-dropdown @command="handleCommand">
        <div class="layout-user">
          <img src="https://via.placeholder.com/36" alt="avatar">
          <span>{{ employee ? employee.name : '' }}</span>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="self">个人信息</el-dropdown-item>
          <el-dropdown-item command="password">设置密码</el-dropdown-item>
          <el-dropdown-item command="logout" divided>退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </header>
    <div class="layout-body">
      <aside class="layout-sidebar">
        <div class="sidebar-header">
          <div class="sidebar-user">
            <img src="https://via.placeholder.com/45" alt="avatar">
            <div class="sidebar-user-info">
              <div class="sidebar-user-name">{{ employee ? employee.name : '' }}</div>
              <div class="sidebar-user-dept">{{ employee && employee.department ? employee.department.name : '' }} - {{ employee ? employee.post : '' }}</div>
            </div>
          </div>
        </div>
        <div class="sidebar-menu-wrapper">
          <el-menu
            :default-active="activeMenu"
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409EFF"
            router
          >
            <el-menu-item index="label1" disabled class="menu-label">
              <span slot="title">日常管理</span>
            </el-menu-item>
            <el-menu-item index="/claim/deal">
              <i class="el-icon-document"></i>
              <span>待处理报销单</span>
            </el-menu-item>
            <el-menu-item index="/claim/self">
              <i class="el-icon-house"></i>
              <span>个人报销单</span>
            </el-menu-item>
            <el-menu-item index="/claim/add">
              <i class="el-icon-edit"></i>
              <span>填写报销单</span>
            </el-menu-item>
            <el-menu-item index="label2" disabled class="menu-label">
              <span slot="title">基础信息管理</span>
            </el-menu-item>
            <el-submenu index="employee">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>员工管理</span>
              </template>
              <el-menu-item index="/employee/list">所有员工</el-menu-item>
              <el-menu-item index="/employee/add">添加员工</el-menu-item>
            </el-submenu>
            <el-submenu index="department">
              <template slot="title">
                <i class="el-icon-office-building"></i>
                <span>部门管理</span>
              </template>
              <el-menu-item index="/department/list">所有部门</el-menu-item>
              <el-menu-item index="/department/add">添加部门</el-menu-item>
            </el-submenu>
            <el-menu-item index="label3" disabled class="menu-label">
              <span slot="title">个人信息管理</span>
            </el-menu-item>
            <el-submenu index="personal">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>个人中心</span>
              </template>
              <el-menu-item index="/self">个人信息</el-menu-item>
              <el-menu-item index="/change-password">设置密码</el-menu-item>
              <el-menu-item index="/log/list">日志记录</el-menu-item>
            </el-submenu>
          </el-menu>
        </div>
        <div class="sidebar-logout" @click="handleLogout">
          <i class="el-icon-switch-button"></i>
          <span>退出登录</span>
        </div>
      </aside>
      <main class="layout-content">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Layout',
  computed: {
    ...mapGetters(['employee']),
    activeMenu() {
      return this.$route.path
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'self') {
        this.$router.push('/self')
      } else if (command === 'password') {
        this.$router.push('/change-password')
      } else if (command === 'logout') {
        this.handleLogout()
      }
    },
    handleLogout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('logout')
        this.$router.push('/login')
        this.$message.success('已退出登录')
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.layout-sidebar {
  display: flex;
  flex-direction: column;
}
.sidebar-menu-wrapper {
  flex: 1;
  overflow-y: auto;
}
.layout-sidebar ::v-deep .el-menu {
  border-right: none;
}
.layout-sidebar ::v-deep .el-menu-item.is-disabled {
  opacity: 1;
  cursor: default;
  color: rgba(255, 255, 255, 0.4) !important;
  font-size: 12px;
  padding-left: 20px !important;
  text-transform: uppercase;
}
.layout-sidebar ::v-deep .el-menu-item.is-disabled:hover {
  background-color: transparent !important;
}
.sidebar-logout {
  padding: 15px 20px;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.3s;
  background: #263445;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  flex-shrink: 0;
}
.sidebar-logout:hover {
  background: #1f2d3d;
  color: #fff;
}
.sidebar-logout i {
  margin-right: 10px;
  width: 20px;
  text-align: center;
}
</style>
