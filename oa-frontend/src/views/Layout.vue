<template>
  <el-container style="height: 100vh">
    <el-header style="background: #409EFF; color: white; padding: 0 20px; display: flex; align-items: center; justify-content: space-between;">
      <h2 style="margin: 0; color: white;">OA办公自动化系统</h2>
      <div style="display: flex; align-items: center; gap: 20px;">
        <span>欢迎，{{ userStore.employee?.name }} ({{ userStore.employee?.post }})</span>
        <el-button type="danger" @click="handleLogout">
          <el-icon><SwitchButton /></el-icon>
          退出登录
        </el-button>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" style="background: #545c64;">
        <el-menu
          :default-active="activeMenu"
          router
          style="height: 100%; border: none;"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="/profile">
            <el-icon><User /></el-icon>
            <span>个人中心</span>
          </el-menu-item>
          <el-menu-item index="/change-password">
            <el-icon><Lock /></el-icon>
            <span>修改密码</span>
          </el-menu-item>
          <el-menu-item index="/logs">
            <el-icon><Document /></el-icon>
            <span>操作日志</span>
          </el-menu-item>
          <el-sub-menu index="basic">
            <template #title>
              <el-icon><OfficeBuilding /></el-icon>
              <span>基本信息</span>
            </template>
            <el-menu-item index="/departments">部门管理</el-menu-item>
            <el-menu-item index="/employees">员工管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="claim">
            <template #title>
              <el-icon><Money /></el-icon>
              <span>报销管理</span>
            </template>
            <el-menu-item index="/claim-voucher/add">添加报销单</el-menu-item>
            <el-menu-item index="/claim-voucher/self">我的报销单</el-menu-item>
            <el-menu-item index="/claim-voucher/deal">待处理报销</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main style="background: #f5f7fa;">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { ElMessageBox } from 'element-plus'
import { SwitchButton, User, Lock, Document, OfficeBuilding, Money } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await userStore.logout()
    router.push('/login')
  }).catch(() => {})
}
</script>
