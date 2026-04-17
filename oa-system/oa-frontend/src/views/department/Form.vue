<template>
  <div class="form-container">
    <h3 style="margin-bottom: 20px;">{{ isEdit ? '编辑部门' : '添加部门' }}</h3>
    <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="max-width: 500px;">
      <el-form-item label="部门编号" prop="id">
        <el-input v-model="form.id" :disabled="isEdit"></el-input>
      </el-form-item>
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="部门地址" prop="address">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button @click="$router.back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addDepartment, updateDepartment, getDepartmentById } from '@/api/department'

export default {
  name: 'DepartmentForm',
  data() {
    return {
      isEdit: false,
      form: {
        id: '',
        name: '',
        address: ''
      },
      rules: {
        id: [{ required: true, message: '请输入部门编号', trigger: 'blur' }],
        name: [{ required: true, message: '请输入部门名称', trigger: 'blur' }],
        address: [{ required: true, message: '请输入部门地址', trigger: 'blur' }]
      }
    }
  },
  created() {
    const id = this.$route.params.id
    if (id) {
      this.isEdit = true
      this.fetchData(id)
    }
  },
  methods: {
    fetchData(id) {
      getDepartmentById(id).then(res => {
        this.form = res.data
      })
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const action = this.isEdit ? updateDepartment(this.form.id, this.form) : addDepartment(this.form)
          action.then(() => {
            this.$message.success(this.isEdit ? '修改成功' : '添加成功')
            this.$router.push('/departments')
          })
        }
      })
    }
  }
}
</script>
