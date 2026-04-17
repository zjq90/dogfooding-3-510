<template>
  <div style="min-height: 100vh; display: flex; justify-content: center; align-items: center; background: linear-gradient(135deg, #409EFF 0%, #67C23A 100%);">
    <el-card shadow="hover" style="width: 450px;">
      <template #header>
        <h2 style="text-align: center; margin: 0; color: #409EFF; font-size: 24px; font-weight: 700;">OA办公自动化系统</h2>
      </template>
      <el-form :model="loginForm" label-width="80px" size="large" @submit.prevent="handleLogin" style="padding-top: 20px;">
        <el-form-item label="工号" prop="id">
          <el-input v-model="loginForm.id" placeholder="请输入工号" size="large">
            <template #prefix><el-icon><User /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" size="large" show-password @keyup.enter="handleLogin">
            <template #prefix><el-icon><Lock /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item style="margin-top: 30px;">
          <el-button type="primary" size="large" style="width: 100%;" :loading="loading" @click="handleLogin">
            <el-icon><Right /></el-icon>
            登录系统
          </el-button>
        </el-form-item>
      </el-form>
      <div style="text-align: center; color: #909399; font-size: 13px; margin-top: 20px; padding-top: 20px; border-top: 1px solid #ebeef5;">
        <p style="margin: 8px 0;">测试账号：y1004 / 123456 (朱元璋-员工)</p>
        <p style="margin: 8px 0;">测试账号：y1003 / 123456 (忽必烈-部门经理)</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'
import { User, Lock, Right } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const loginForm = ref({
  id: '',
  password: ''
})
const loading = ref(false)

const handleLogin = async () => {
  if (!loginForm.value.id || !loginForm.value.password) {
    ElMessage.warning('请输入工号和密码')
    return
  }
  loading.value = true
  try {
    await userStore.login(loginForm.value)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>
