<template>
  <div>
    <div class="page-header">
      <span class="page-title">我的报销单</span>
      <el-button type="primary" size="small" @click="$router.push('/claim-vouchers/add')">填写报销单</el-button>
    </div>

    <div class="data-table">
      <el-table :data="list" border v-loading="loading">
        <el-table-column prop="id" label="编号" width="80"></el-table-column>
        <el-table-column prop="cause" label="报销事由"></el-table-column>
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
        <el-table-column prop="dealer.name" label="待处理人" width="120">
          <template slot-scope="scope">
            {{ scope.row.dealer ? scope.row.dealer.name : '-' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleDetail(scope.row)">详情</el-button>
            <el-button
              v-if="scope.row.status === '新创建' || scope.row.status === '已打回'"
              type="warning"
              size="mini"
              @click="handleEdit(scope.row)"
            >编辑</el-button>
            <el-button
              v-if="scope.row.status === '新创建' || scope.row.status === '已打回'"
              type="success"
              size="mini"
              @click="handleSubmit(scope.row)"
            >提交</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getMyClaimVouchers, submitClaimVoucher } from '@/api/claimVoucher'

export default {
  name: 'MyClaimVouchers',
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
      getMyClaimVouchers().then(res => {
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
    handleEdit(row) {
      this.$router.push(`/claim-vouchers/edit/${row.id}`)
    },
    handleSubmit(row) {
      this.$confirm('确认提交报销单吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        submitClaimVoucher(row.id).then(() => {
          this.$message.success('提交成功')
          this.fetchData()
        })
      })
    }
  }
}
</script>
