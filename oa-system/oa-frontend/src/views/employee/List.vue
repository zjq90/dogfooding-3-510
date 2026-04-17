<template>
  <div>
    <div class="page-header">
      <span class="page-title">员工列表</span>
      <el-button type="primary" size="small" @click="$router.push('/employees/add')">添加员工</el-button>
    </div>

    <div class="data-table">
      <el-table :data="list" border v-loading="loading">
        <el-table-column prop="id" label="工号" width="100"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column prop="department.name" label="所属部门" width="150">
          <template slot-scope="scope">
            {{ scope.row.department ? scope.row.department.name : '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="post" label="职位" width="120">
          <template slot-scope="scope">
            <el-tag size="small" :type="getPostType(scope.row.post)">{{ scope.row.post }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getEmployeeList, deleteEmployee } from '@/api/employee'

export default {
  name: 'EmployeeList',
  data() {
    return {
      loading: false,
      list: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      getEmployeeList().then(res => {
        this.list = res.data
      }).finally(() => {
        this.loading = false
      })
    },
    getPostType(post) {
      const types = {
        '总经理': 'danger',
        '部门经理': 'warning',
        '财务': 'success',
        '员工': ''
      }
      return types[post] || ''
    },
    handleEdit(row) {
      this.$router.push(`/employees/edit/${row.id}`)
    },
    handleDelete(row) {
      this.$confirm(`确认删除员工 ${row.name} 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteEmployee(row.id).then(() => {
          this.$message.success('删除成功')
          this.fetchData()
        })
      })
    }
  }
}
</script>
