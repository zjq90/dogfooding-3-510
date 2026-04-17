<template>
  <div>
    <div class="page-header">
      <span class="page-title">部门列表</span>
      <el-button type="primary" size="small" @click="$router.push('/departments/add')">添加部门</el-button>
    </div>

    <div class="data-table">
      <el-table :data="list" border v-loading="loading">
        <el-table-column prop="id" label="部门编号" width="120"></el-table-column>
        <el-table-column prop="name" label="部门名称" width="150"></el-table-column>
        <el-table-column prop="address" label="部门地址"></el-table-column>
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
import { getDepartmentList, deleteDepartment } from '@/api/department'

export default {
  name: 'DepartmentList',
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
      getDepartmentList().then(res => {
        this.list = res.data
      }).finally(() => {
        this.loading = false
      })
    },
    handleEdit(row) {
      this.$router.push(`/departments/edit/${row.id}`)
    },
    handleDelete(row) {
      this.$confirm(`确认删除部门 ${row.name} 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDepartment(row.id).then(() => {
          this.$message.success('删除成功')
          this.fetchData()
        })
      })
    }
  }
}
</script>
