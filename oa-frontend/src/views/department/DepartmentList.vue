<template>
  <div>
    <div class="content-header">
      <h2>部门列表</h2>
    </div>
    <div class="content-card">
      <div class="toolbar">
        <div></div>
        <el-button type="primary" icon="el-icon-plus" @click="$router.push('/department/add')">添加部门</el-button>
      </div>
      <el-table :data="list" stripe border style="width: 100%">
        <el-table-column prop="id" label="编号" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="部门名称" min-width="150"></el-table-column>
        <el-table-column prop="address" label="地址" min-width="200"></el-table-column>
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
import { getDepartmentList, deleteDepartment } from '@/api/department'

export default {
  name: 'DepartmentList',
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
        const res = await getDepartmentList()
        this.list = res.data
      } catch (error) {
        console.error(error)
      }
    },
    handleUpdate(id) {
      this.$router.push(`/department/update/${id}`)
    },
    async handleDelete(id) {
      try {
        await this.$confirm('确定要删除该部门吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteDepartment(id)
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
