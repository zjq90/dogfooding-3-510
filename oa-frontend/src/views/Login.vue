<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">欢迎使用自动化管理系统</h2>
      <el-form :model="form" :rules="rules" ref="loginForm">
        <el-form-item prop="id">
          <el-input v-model="form.id" placeholder="请输入工号" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="el-icon-lock" @keyup.enter.native="handleLogin"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="remember">记住我</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" :loading="loading" @click="handleLogin">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { login } from '@/api/auth'

export default {
  name: 'Login',
  data() {
    return {
      form: {
        id: '',
        password: ''
      },
      rules: {
        id: [{ required: true, message: '请输入工号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      remember: true,
      loading: false
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(async valid => {
        if (valid) {
          this.loading = true
          try {
            const res = await login(this.form)
            this.$store.dispatch('login', {
              employee: res.data.employee,
              token: res.data.token
            })
            this.$message.success('登录成功')
            this.$router.push('/claim/deal')
          } catch (error) {
            console.error(error)
          } finally {
            this.loading = false
          }
        }
      })
    }
  }
}
</script>
