<template>
  <el-card shadow="hover">
    <template #header>
      <span style="font-size: 16px; font-weight: 600; color: #303133;">待处理报销单</span>
    </template>
    <el-table :data="list" border stripe style="width: 100%;" :cell-style="{ padding: '12px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
      <el-table-column prop="id" label="编号" min-width="100" align="center" />
      <el-table-column prop="creator.name" label="申请人" min-width="120" align="center" />
      <el-table-column prop="cause" label="事由" min-width="180" show-overflow-tooltip align="center" />
      <el-table-column prop="createTime" label="创建时间" min-width="180" align="center" />
      <el-table-column prop="totalAmount" label="金额" min-width="120" align="center">
        <template #default="{ row }">
          <span style="font-weight: 600; color: #f56c6c;">¥{{ row.totalAmount }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" min-width="120" align="center">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)" size="small">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="150" align="center">
        <template #default="{ row }">
          <el-button type="primary" link @click="$router.push(`/claim-voucher/detail/${row.id}`)">
            <el-icon><Ticket /></el-icon>
            处理
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDealClaimVouchers } from '../api/claimVoucher'
import { Ticket } from '@element-plus/icons-vue'

const list = ref([])

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
  const res = await getDealClaimVouchers()
  list.value = res.data
}

onMounted(() => {
  loadData()
})
</script>
