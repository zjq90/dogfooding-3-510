<template>
  <div class="form-container">
    <h3 style="margin-bottom: 20px;">{{ isEdit ? '编辑员工' : '添加员工' }}</h3>
    <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="max-width: 500px;">
      <el-form-item label="工号" prop="id">
        <el-input v-model="form.id" :disabled="isEdit"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="所属部门" prop="departmentId">
        <el-select v-model="form.departmentId" placeholder="请选择部门" style="width: 100%;">
          <el-option
            v-for="dept in departments"
            :key="dept.id"
            :label="dept.name"
            :value="dept.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职位" prop="post">
        <el-select v-model="form.post" placeholder="请选择职位" style="width: 100%;">
          <el-option
            v-for="post in posts"
            :key="post"
            :label="post"
            :value="post"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button @click="$router.back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addEmployee, updateEmployee, getEmployeeById } from '@/api/employee'
import { getDepartmentList } from '@/api/department'
import { getPosts } from '@/api/auth'

export default {
  name: 'EmployeeForm',
  data() {
    return {
      isEdit: false,
      departments: [],
      posts: [],
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
        post: [{ required: true, message: '请选择职位', trigger: 'change' }]
      }
    }
  },
  created() {
    this.fetchOptions()
    const id = this.$route.params.id
    if (id) {
      this.isEdit = true
      this.fetchData(id)
    }
  },
  methods: {
    fetchOptions() {
      getDepartmentList().then(res => {
        this.departments = res.data
      })
      getPosts().then(res => {
        this.posts = res.data
      })
    },
    fetchData(id) {
      getEmployeeById(id).then(res => {
        this.form = res.data
      })
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const action = this.isEdit ? updateEmployee(this.form.id, this.form) : addEmployee(this.form)
          action.then(() => {
            this.$message.success(this.isEdit ? '修改成功' : '添加成功')
            this.$router.push('/employees')
          })
        }
      })
    }
  }
}
</script>
