<template>
  <div class="detail-container" v-loading="loading">
    <h3 style="margin-bottom: 20px;">报销单详情</h3>

    <div class="detail-section">
      <div class="detail-section-title">基本信息</div>
      <el-row :gutter="20">
        <el-col :span="8">
          <p><strong>编号:</strong> {{ claimVoucher.id }}</p>
        </el-col>
        <el-col :span="8">
          <p><strong>报销事由:</strong> {{ claimVoucher.cause }}</p>
        </el-col>
        <el-col :span="8">
          <p><strong>状态:</strong> <span :class="getStatusClass(claimVoucher.status)">{{ claimVoucher.status }}</span></p>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <p><strong>创建人:</strong> {{ claimVoucher.creator ? claimVoucher.creator.name : '' }}</p>
        </el-col>
        <el-col :span="8">
          <p><strong>创建时间:</strong> {{ claimVoucher.createTime }}</p>
        </el-col>
        <el-col :span="8">
          <p><strong>总金额:</strong> ¥{{ claimVoucher.totalAmount ? claimVoucher.totalAmount.toFixed(2) : '0.00' }}</p>
        </el-col>
      </el-row>
    </div>

    <div class="detail-section">
      <div class="detail-section-title">报销项目</div>
      <el-table :data="items" border>
        <el-table-column type="index" label="序号" width="60"></el-table-column>
        <el-table-column prop="item" label="费用类型" width="120"></el-table-column>
        <el-table-column prop="amount" label="金额" width="120">
          <template slot-scope="scope">
            ¥{{ scope.row.amount ? scope.row.amount.toFixed(2) : '0.00' }}
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="描述"></el-table-column>
      </el-table>
    </div>

    <div class="detail-section">
      <div class="detail-section-title">处理记录</div>
      <el-table :data="records" border>
        <el-table-column prop="dealTime" label="处理时间" width="160"></el-table-column>
        <el-table-column prop="dealer.name" label="处理人" width="120">
          <template slot-scope="scope">
            {{ scope.row.dealer ? scope.row.dealer.name : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="dealType" label="处理类型" width="100"></el-table-column>
        <el-table-column prop="dealResult" label="处理结果" width="100"></el-table-column>
        <el-table-column prop="comment" label="备注"></el-table-column>
      </el-table>
    </div>

    <div style="text-align: center; margin-top: 20px;">
      <el-button @click="$router.back()">返回</el-button>
    </div>
  </div>
</template>

<script>
import { getClaimVoucherById } from '@/api/claimVoucher'

export default {
  name: 'ClaimVoucherDetail',
  data() {
    return {
      loading: false,
      claimVoucher: {},
      items: [],
      records: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      const id = this.$route.params.id
      getClaimVoucherById(id).then(res => {
        this.claimVoucher = res.data.claimVoucher
        this.items = res.data.items
        this.records = res.data.records
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
    }
  }
}
</script>
