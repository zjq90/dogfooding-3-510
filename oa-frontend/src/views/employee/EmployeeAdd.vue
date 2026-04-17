<template>
  <div>
    <div class="content-header">
      <h2>添加员工</h2>
    </div>
    <div class="content-card">
      <el-form :model="form" :rules="rules" ref="employeeForm" label-width="100px" style="max-width: 500px;">
        <el-form-item label="工号" prop="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="departmentId">
          <el-select v-model="form.departmentId" placeholder="请选择部门" style="width: 100%;">
            <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职务" prop="post">
          <el-select v-model="form.post" placeholder="请选择职务" style="width: 100%;">
            <el-option v-for="post in posts" :key="post" :label="post" :value="post"></el-option>
          </el-select>
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
import { addEmployee, getPosts } from '@/api/employee'
import { getDepartmentList } from '@/api/department'

export default {
  name: 'EmployeeAdd',
  data() {
    return {
      form: {
        id: '',
        name: '',
        departmentId: '',
        post: ''
      },
      rules: {
        id: [{ required: true, message: '请输入工号', trigger: 'blur' }],
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        departmentId: [{ required: true, message: '请选择部门', trigger: 'change' }],
        post: [{ required: true, message: '请选择职务', trigger: 'change' }]
      },
      departments: [],
      posts: []
    }
  },
  created() {
    this.loadDepartments()
    this.loadPosts()
  },
  methods: {
    async loadDepartments() {
      try {
        const res = await getDepartmentList()
        this.departments = res.data
      } catch (error) {
        console.error(error)
      }
    },
    async loadPosts() {
      try {
        const res = await getPosts()
        this.posts = res.data.posts
      } catch (error) {
        console.error(error)
      }
    },
    handleSubmit() {
      this.$refs.employeeForm.validate(async valid => {
        if (valid) {
          try {
            await addEmployee(this.form)
            this.$message.success('添加成功')
            this.$router.push('/employee/list')
          } catch (error) {
            console.error(error)
          }
        }
      })
    }
  }
}
</script>
