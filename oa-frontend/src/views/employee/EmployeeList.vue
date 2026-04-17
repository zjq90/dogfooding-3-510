<template>
  <div>
    <div class="content-header">
      <h2>员工列表</h2>
    </div>
    <div class="content-card">
      <div class="toolbar">
        <div></div>
        <el-button type="primary" icon="el-icon-plus" @click="$router.push('/employee/add')">添加员工</el-button>
      </div>
      <el-table :data="list" stripe border style="width: 100%">
        <el-table-column prop="id" label="工号" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120" align="center"></el-table-column>
        <el-table-column label="部门" min-width="150">
          <template slot-scope="scope">
            {{ scope.row.department ? scope.row.department.name : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="post" label="职务" width="120" align="center"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(scope.row.id)">修改</el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
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
      list: []
    }
  },
  created() {
    this.loadList()
  },
  methods: {
    async loadList() {
      try {
        const res = await getEmployeeList()
        this.list = res.data
      } catch (error) {
        console.error(error)
      }
    },
    handleUpdate(id) {
      this.$router.push(`/employee/update/${id}`)
    },
    async handleDelete(id) {
      try {
        await this.$confirm('确定要删除该员工吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteEmployee(id)
        this.$message.success('删除成功')
        this.loadList()
      } catch (error) {
        if (error !== 'cancel') {
          console.error(error)
        }
      }
    }
  }
}
</script>
