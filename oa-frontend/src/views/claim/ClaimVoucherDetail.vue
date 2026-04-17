<template>
  <div>
    <div class="content-header">
      <h2>报销单详情</h2>
    </div>
    <div class="content-card">
      <div class="form-section">
        <div class="form-section-title">基本信息</div>
        <div class="form-row">
          <div class="form-item">
            <label class="form-label">编号</label>
            <div>{{ claimVoucher.id }}</div>
          </div>
          <div class="form-item">
            <label class="form-label">事由</label>
            <div>{{ claimVoucher.cause }}</div>
          </div>
        </div>
        <div class="form-row">
          <div class="form-item">
            <label class="form-label">创建人</label>
            <div>{{ claimVoucher.creator ? claimVoucher.creator.name : '' }}</div>
          </div>
          <div class="form-item">
            <label class="form-label">创建时间</label>
            <div>{{ formatDate(claimVoucher.createTime) }}</div>
          </div>
        </div>
        <div class="form-row">
          <div class="form-item">
            <label class="form-label">状态</label>
            <div>
              <span :class="getStatusClass(claimVoucher.status)">{{ claimVoucher.status }}</span>
            </div>
          </div>
          <div class="form-item">
            <label class="form-label">总金额</label>
            <div>{{ claimVoucher.totalAmount }}</div>
          </div>
        </div>
      </div>
      <div class="form-section">
        <div class="form-section-title">报销条目</div>
        <el-table :data="items" border>
          <el-table-column prop="item" label="费用类型"></el-table-column>
          <el-table-column prop="amount" label="金额"></el-table-column>
          <el-table-column prop="comment" label="说明"></el-table-column>
        </el-table>
      </div>
      <div class="form-section">
        <div class="form-section-title">处理记录</div>
        <el-table :data="records" border>
          <el-table-column label="处理人" width="120">
            <template slot-scope="scope">
              {{ scope.row.dealer ? scope.row.dealer.name : '' }}
            </template>
          </el-table-column>
          <el-table-column prop="dealType" label="处理类型" width="100"></el-table-column>
          <el-table-column prop="dealResult" label="处理结果" width="100"></el-table-column>
          <el-table-column label="处理时间" width="180">
            <template slot-scope="scope">
              {{ formatDate(scope.row.dealTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="comment" label="备注"></el-table-column>
        </el-table>
      </div>
      <div style="text-align: right; margin-top: 20px;">
        <el-button @click="$router.go(-1)">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getClaimVoucher } from '@/api/claimVoucher'

export default {
  name: 'ClaimVoucherDetail',
  data() {
    return {
      claimVoucher: {},
      items: [],
      records: []
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        const res = await getClaimVoucher(this.$route.params.id)
        this.claimVoucher = res.data.claimVoucher
        this.items = res.data.items
        this.records = res.data.records
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
    }
  }
}
</script>
