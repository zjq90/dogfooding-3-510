<template>
  <div>
    <div class="content-header">
      <h2>日志记录</h2>
    </div>
    <div class="content-card">
      <el-table :data="list" stripe border style="width: 100%">
        <el-table-column prop="id" label="编号" width="80" align="center"></el-table-column>
        <el-table-column label="操作人" width="120" align="center">
          <template slot-scope="scope">
            {{ scope.row.employee ? scope.row.employee.name : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="操作" min-width="200"></el-table-column>
        <el-table-column label="操作时间" width="180" align="center">
          <template slot-scope="scope">
            {{ formatDate(scope.row.operationTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getLogList, deleteLog } from '@/api/log'

export default {
  name: 'LogList',
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
        const res = await getLogList()
        this.list = res.data
      } catch (error) {
        console.error(error)
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleString('zh-CN')
    },
    async handleDelete(id) {
      try {
        await this.$confirm('确定要删除该日志吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteLog(id)
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
