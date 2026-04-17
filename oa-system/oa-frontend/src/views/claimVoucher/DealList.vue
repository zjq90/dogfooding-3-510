<template>
  <div>
    <div class="page-header">
      <span class="page-title">待处理报销单</span>
    </div>

    <div class="data-table">
      <el-table :data="list" border v-loading="loading">
        <el-table-column prop="id" label="编号" width="80"></el-table-column>
        <el-table-column prop="cause" label="报销事由"></el-table-column>
        <el-table-column prop="creator.name" label="创建人" width="120">
          <template slot-scope="scope">
            {{ scope.row.creator ? scope.row.creator.name : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
        <el-table-column prop="totalAmount" label="总金额" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.totalAmount ? scope.row.totalAmount.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <span :class="getStatusClass(scope.row.status)">{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleDetail(scope.row)">详情</el-button>
            <el-button type="success" size="mini" @click="handleCheck(scope.row)">审核</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getDealClaimVouchers } from '@/api/claimVoucher'

export default {
  name: 'DealClaimVouchers',
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
      getDealClaimVouchers().then(res => {
        this.list = res.data
      }).finally(() => {
        this.loading = false
      })
    },
    getStatusClass(status) {
      const classes = {
        '新创建': 'status-created',
        '已提交': 'status-submitted',
        '已审核': 'status-approved',
        '已打回': 'status-rejected',
        '已终止': 'status-terminated',
        '待复审': 'status-submitted',
        '已打款': 'status-paid'
      }
      return classes[status] || ''
    },
    handleDetail(row) {
      this.$router.push(`/claim-vouchers/detail/${row.id}`)
    },
    handleCheck(row) {
      this.$router.push(`/claim-vouchers/check/${row.id}`)
    }
  }
}
</script>
