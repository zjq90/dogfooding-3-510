<template>
  <div>
    <div class="content-header">
      <h2>添加部门</h2>
    </div>
    <div class="content-card">
      <el-form :model="form" :rules="rules" ref="departmentForm" label-width="100px" style="max-width: 500px;">
        <el-form-item label="编号" prop="id">
          <el-input v-model="form.id"></el-input>
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
import { addDepartment } from '@/api/department'

export default {
  name: 'DepartmentAdd',
  data() {
    return {
      form: {
        id: '',
        name: '',
        address: ''
      },
      rules: {
        id: [{ required: true, message: '请输入编号', trigger: 'blur' }],
        name: [{ required: true, message: '请输入部门名称', trigger: 'blur' }]
      }
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.departmentForm.validate(async valid => {
        if (valid) {
          try {
            await addDepartment(this.form)
            this.$message.success('添加成功')
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
