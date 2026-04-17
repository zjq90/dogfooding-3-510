<template>
  <el-card shadow="hover">
    <template #header>
      <span style="font-size: 16px; font-weight: 600; color: #303133;">个人信息</span>
    </template>
    <el-descriptions :column="2" border size="default">
      <el-descriptions-item label="工号" align="center">
        <el-tag type="info" size="large">{{ userStore.employee?.id }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="姓名" align="center">
        <span style="font-size: 16px; font-weight: 600;">{{ userStore.employee?.name }}</span>
      </el-descriptions-item>
      <el-descriptions-item label="部门" align="center">{{ userStore.employee?.department?.name }}</el-descriptions-item>
      <el-descriptions-item label="职位" align="center">
        <el-tag :type="getPostType(userStore.employee?.post)" size="large">{{ userStore.employee?.post }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup>
import { useUserStore } from '../store/user'

const userStore = useUserStore()

const getPostType = (post) => {
  const map = {
    '总经理': 'danger',
    '部门经理': 'warning',
    '财务': 'success',
    '员工': 'info'
  }
  return map[post] || ''
}
</script>
