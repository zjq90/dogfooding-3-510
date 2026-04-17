<template>
  <el-card shadow="hover">
    <template #header>
      <span style="font-size: 16px; font-weight: 600; color: #303133;">我的报销单</span>
    </template>
    <el-table :data="list" border stripe style="width: 100%;" :cell-style="{ padding: '12px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
      <el-table-column prop="id" label="编号" min-width="100" align="center" />
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
      <el-table-column prop="dealer.name" label="待处理人" min-width="120" align="center" />
      <el-table-column label="操作" min-width="280" align="center">
        <template #default="{ row }">
          <el-button type="primary" link @click="$router.push(`/claim-voucher/detail/${row.id}`)">
            <el-icon><View /></el-icon>
            查看
          </el-button>
          <el-button v-if="row.status === '新创建' || row.status === '已打回'" type="success" link @click="$router.push(`/claim-voucher/edit/${row.id}`)">
            <el-icon><Edit /></el-icon>
            修改
          </el-button>
          <el-button v-if="row.status === '新创建' || row.status === '已打回'" type="warning" link @click="handleSubmit(row.id)">
            <el-icon><Upload /></el-icon>
            提交
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getSelfClaimVouchers, submitClaimVoucher } from '../api/claimVoucher'
import { ElMessage, ElMessageBox } from 'element-plus'
import { View, Edit, Upload } from '@element-plus/icons-vue'

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
  const res = await getSelfClaimVouchers()
  list.value = res.data
}

const handleSubmit = (id) => {
  ElMessageBox.confirm('确定要提交这张报销单吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await submitClaimVoucher(id)
    ElMessage.success('提交成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
