<template>
  <el-card shadow="hover">
    <template #header>
      <span style="font-size: 16px; font-weight: 600; color: #303133;">修改密码</span>
    </template>
    <el-form :model="passwordForm" label-width="100px" style="max-width: 550px;" size="default">
      <el-form-item label="原密码">
        <el-input v-model="passwordForm.oldPassword" type="password" show-password style="width: 100%;" size="default">
          <template #prefix><el-icon><Lock /></el-icon></template>
        </el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="passwordForm.newPassword" type="password" show-password style="width: 100%;" size="default">
          <template #prefix><el-icon><Key /></el-icon></template>
        </el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="passwordForm.confirmPassword" type="password" show-password style="width: 100%;" size="default">
          <template #prefix><el-icon><Lock /></el-icon></template>
        </el-input>
      </el-form-item>
      <el-form-item style="margin-top: 20px;">
        <el-button type="primary" size="default" :loading="loading" @click="handleSubmit">
          <el-icon><Check /></el-icon>
          提交修改
        </el-button>
        <el-button size="default" @click="passwordForm = {oldPassword: '', newPassword: '', confirmPassword: ''}">
          <el-icon><Refresh /></el-icon>
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'
import { Lock, Key, Check, Refresh } from '@element-plus/icons-vue'

const userStore = useUserStore()

const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})
const loading = ref(false)

const handleSubmit = async () => {
  if (!passwordForm.value.oldPassword || !passwordForm.value.newPassword || !passwordForm.value.confirmPassword) {
    ElMessage.warning('请填写完整信息')
    return
  }
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }
  loading.value = true
  try {
    await userStore.changePassword(passwordForm.value)
    ElMessage.success('密码修改成功')
    passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>
