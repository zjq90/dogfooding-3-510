<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">OA办公自动化系统</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0">
        <el-form-item prop="id">
          <el-input
            v-model="loginForm.id"
            placeholder="请输入工号"
            prefix-icon="el-icon-user"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
            size="large"
            @keyup.enter.native="handleLogin"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            style="width: 100%"
            :loading="loading"
            @click="handleLogin"
          >登录</el-button>
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
      loading: false,
      loginForm: {
        id: '',
        password: ''
      },
      rules: {
        id: [{ required: true, message: '请输入工号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          login(this.loginForm)
            .then(res => {
              this.$store.commit('SET_USER', res.data)
              this.$message.success('登录成功')
              this.$router.push('/')
            })
            .finally(() => {
              this.loading = false
            })
        }
      })
    }
  }
}
</script>
