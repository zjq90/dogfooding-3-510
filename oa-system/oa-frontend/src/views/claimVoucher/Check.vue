<template>
  <div class="form-container" v-loading="loading">
    <h3 style="margin-bottom: 20px;">审核报销单</h3>

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

    <div class="detail-section">
      <div class="detail-section-title">审核操作</div>
      <el-form :model="form" label-width="100px">
        <el-form-item label="处理方式">
          <el-radio-group v-model="form.dealType">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="打回">打回</el-radio>
            <el-radio label="拒绝">拒绝</el-radio>
            <el-radio label="打款" v-if="canPay">打款</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.comment" rows="3"></el-input>
        </el-form-item>
      </el-form>
    </div>

    <div style="text-align: center; margin-top: 20px;">
      <el-button type="primary" @click="handleSubmit">提交</el-button>
      <el-button @click="$router.back()">返回</el-button>
    </div>
  </div>
</template>

<script>
import { getClaimVoucherById, dealClaimVoucher } from '@/api/claimVoucher'

export default {
  name: 'ClaimVoucherCheck',
  data() {
    return {
      loading: false,
      claimVoucher: {},
      items: [],
      records: [],
      form: {
        claimVoucherId: null,
        dealType: '通过',
        dealResult: '',
        comment: ''
      }
    }
  },
  computed: {
    canPay() {
      return this.claimVoucher.status === '已审核'
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      const id = this.$route.params.id
      this.form.claimVoucherId = parseInt(id)
      getClaimVoucherById(id).then(res => {
        this.claimVoucher = res.data.claimVoucher
        this.items = res.data.items
        this.records = res.data.records
      }).finally(() => {
        this.loading = false
      })
    },
    handleSubmit() {
      this.form.dealResult = this.form.dealType
      dealClaimVoucher(this.form).then(() => {
        this.$message.success('处理成功')
        this.$router.push('/claim-vouchers/deal')
      })
    }
  }
}
</script>
