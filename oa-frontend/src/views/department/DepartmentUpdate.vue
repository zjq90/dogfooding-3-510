<template>
  <div>
    <div class="content-header">
      <h2>修改部门</h2>
    </div>
    <div class="content-card">
      <el-form :model="form" :rules="rules" ref="departmentForm" label-width="100px" style="max-width: 500px;">
        <el-form-item label="编号">
          <el-input v-model="form.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getDepartment, updateDepartment } from '@/api/department'

export default {
  name: 'DepartmentUpdate',
  data() {
    return {
      form: {
        id: '',
        name: '',
        address: ''
      },
      rules: {
        name: [{ required: true, message: '请输入部门名称', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        const res = await getDepartment(this.$route.params.id)
        this.form = res.data
      } catch (error) {
        console.error(error)
      }
    },
    handleSubmit() {
      this.$refs.departmentForm.validate(async valid => {
        if (valid) {
          try {
            await updateDepartment(this.form)
            this.$message.success('修改成功')
            this.$router.push('/department/list')
          } catch (error) {
            console.error(error)
          }
        }
      })
    }
  }
}
</script>
