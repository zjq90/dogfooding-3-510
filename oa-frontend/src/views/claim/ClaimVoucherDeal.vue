<template>
  <div>
    <div class="content-header">
      <h2>待处理报销单</h2>
    </div>
    <div class="content-card">
      <div class="table-container">
        <el-table :data="list" stripe>
          <el-table-column prop="id" label="编号" width="80"></el-table-column>
          <el-table-column prop="cause" label="事由"></el-table-column>
          <el-table-column label="创建人" width="120">
            <template slot-scope="scope">
              {{ scope.row.creator ? scope.row.creator.name : '' }}
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180">
            <template slot-scope="scope">
              {{ formatDate(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="totalAmount" label="金额" width="100"></el-table-column>
          <el-table-column prop="status" label="状态" width="100">
            <template slot-scope="scope">
              <span :class="getStatusClass(scope.row.status)">{{ scope.row.status }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="handleDetail(scope.row.id)">详情</el-button>
              <el-button type="text" size="small" v-if="scope.row.status !== '新创建'" @click="handleCheck(scope.row.id)">审核</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { getDealClaimVouchers } from '@/api/claimVoucher'

export default {
  name: 'ClaimVoucherDeal',
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
        const res = await getDealClaimVouchers()
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
    getStatusClass(status) {
      const map = {
        '新创建': 'status-tag status-created',
        '已提交': 'status-tag status-submitted',
        '已审核': 'status-tag status-approved',
        '已打回': 'status-tag status-back',
        '已打款': 'status-tag status-paid',
        '待复审': 'status-tag status-submitted'
      }
      return map[status] || 'status-tag'
    },
    handleDetail(id) {
      this.$router.push(`/claim/detail/${id}`)
    },
    handleCheck(id) {
      this.$router.push(`/claim/check/${id}`)
    }
  }
}
</script>
