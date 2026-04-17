<template>
  <div v-if="loaded">
    <el-card shadow="hover" style="margin-bottom: 20px;">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span style="font-size: 16px; font-weight: 600; color: #303133;">报销单详情</span>
          <el-button size="default" @click="$router.back()">
            <el-icon><Back /></el-icon>
            返回
          </el-button>
        </div>
      </template>
      <el-descriptions :column="2" border style="margin-bottom: 25px;" size="default">
        <el-descriptions-item label="编号" align="center">{{ detail.claimVoucher.id }}</el-descriptions-item>
        <el-descriptions-item label="状态" align="center">
          <el-tag :type="getStatusType(detail.claimVoucher.status)" size="small">{{ detail.claimVoucher.status }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="申请人" align="center">{{ detail.claimVoucher.creator?.name }}</el-descriptions-item>
        <el-descriptions-item label="职位" align="center">{{ detail.claimVoucher.creator?.post }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" align="center">{{ detail.claimVoucher.createTime }}</el-descriptions-item>
        <el-descriptions-item label="待处理人" align="center">{{ detail.claimVoucher.dealer?.name }}</el-descriptions-item>
        <el-descriptions-item label="报销事由" :span="2" align="center">{{ detail.claimVoucher.cause }}</el-descriptions-item>
      </el-descriptions>

      <div style="font-size: 15px; font-weight: 600; margin-bottom: 15px; color: #303133;">报销明细</div>
      <el-table :data="detail.items" border style="width: 100%; margin-bottom: 25px;" :cell-style="{ padding: '12px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
        <el-table-column prop="item" label="费用类型" min-width="180" align="center" />
        <el-table-column prop="amount" label="金额" min-width="150" align="center">
          <template #default="{ row }">
            <span style="font-weight: 600; color: #f56c6c;">¥{{ row.amount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="说明" min-width="200" align="center" />
      </el-table>

      <div style="text-align: right; font-size: 22px; font-weight: 700; color: #f56c6c; margin-bottom: 30px; padding-right: 20px;">
        总金额：¥{{ detail.claimVoucher.totalAmount }}
      </div>

      <div style="font-size: 15px; font-weight: 600; margin-bottom: 15px; color: #303133;">处理记录</div>
      <el-timeline>
        <el-timeline-item
          v-for="record in detail.records"
          :key="record.id"
          :timestamp="record.dealTime"
          placement="top"
          size="large"
        >
          <el-card shadow="hover">
            <div style="font-size: 14px; font-weight: 600; margin-bottom: 8px;">{{ record.dealer?.name }} - {{ record.dealType }}</div>
            <p style="margin: 5px 0;">处理结果：{{ record.dealResult }}</p>
            <p v-if="record.comment !== '无'" style="margin: 5px 0;">备注：{{ record.comment }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>

    <el-card shadow="hover" v-if="canDeal">
      <template #header>
        <span style="font-size: 16px; font-weight: 600; color: #303133;">处理报销单</span>
      </template>
      <el-form :model="dealForm" label-width="100px" style="max-width: 600px;" size="default">
        <el-form-item label="处理类型">
          <el-radio-group v-model="dealForm.dealType" size="large">
            <el-radio v-if="userStore.employee?.post === '财务'" value="打款" border>打款</el-radio>
            <el-radio v-else value="审核" border>通过</el-radio>
            <el-radio value="打回" border>打回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="处理意见">
          <el-input v-model="dealForm.comment" type="textarea" :rows="3" style="width: 100%;" />
        </el-form-item>
        <el-form-item style="margin-top: 20px;">
          <el-button type="primary" size="default" :loading="loading" @click="handleDeal">
            <el-icon><Check /></el-icon>
            提交处理
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getClaimVoucherDetail, dealClaimVoucher } from '../api/claimVoucher'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'
import { Back, Check } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loaded = ref(false)
const loading = ref(false)
const detail = ref({
  claimVoucher: {},
  items: [],
  records: []
})
const dealForm = reactive({
  claimVoucherId: null,
  dealType: '',
  comment: ''
})

const canDeal = computed(() => {
  const cv = detail.value.claimVoucher
  return cv.nextDealId === userStore.employee?.id && 
    cv.status !== '已打款'
})

const getStatusType = (status) => {
  const map = {
    '新创建': 'info',
    '已提交': 'warning',
    '已审核': 'success',
    '已打回': 'danger',
    '已打款': 'success'
  }
  return map[status] || ''
}

const loadData = async () => {
  const res = await getClaimVoucherDetail(route.params.id)
  detail.value = res.data
  dealForm.claimVoucherId = res.data.claimVoucher.id
  loaded.value = true
}

const handleDeal = async () => {
  if (!dealForm.dealType) {
    ElMessage.warning('请选择处理类型')
    return
  }
  if (dealForm.dealType === '审核') {
    dealForm.dealType = '通过'
  }
  if (!dealForm.comment) {
    dealForm.comment = '无'
  }
  loading.value = true
  try {
    await dealClaimVoucher(dealForm)
    ElMessage.success('处理成功')
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>
