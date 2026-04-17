<template>
  <div>
    <div class="page-header">
      <span class="page-title">操作日志</span>
    </div>

    <div class="data-table">
      <el-table :data="list" border v-loading="loading">
        <el-table-column prop="id" label="编号" width="80"></el-table-column>
        <el-table-column prop="employee.name" label="操作人" width="120">
          <template slot-scope="scope">
            {{ scope.row.employee ? scope.row.employee.name : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="employee.post" label="职位" width="120">
          <template slot-scope="scope">
            {{ scope.row.employee ? scope.row.employee.post : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="操作类型"></el-table-column>
        <el-table-column prop="operationTime" label="操作时间" width="160"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getLogList } from '@/api/log'

export default {
  name: 'LogList',
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
      getLogList().then(res => {
        this.list = res.data
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
