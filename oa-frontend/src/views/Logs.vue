<template>
  <el-card shadow="hover">
    <template #header>
      <span style="font-size: 16px; font-weight: 600; color: #303133;">操作日志</span>
    </template>
    <el-table :data="logList" border stripe style="width: 100%;" :cell-style="{ padding: '12px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
      <el-table-column prop="id" label="编号" min-width="100" align="center" />
      <el-table-column prop="employee.name" label="操作人" min-width="150" align="center" />
      <el-table-column prop="operationTime" label="操作时间" min-width="180" align="center" />
      <el-table-column prop="operation" label="操作内容" min-width="250" show-overflow-tooltip align="center" />
      <el-table-column label="操作" min-width="150" align="center">
        <template #default="{ row }">
          <el-button type="danger" link @click="handleDelete(row.id)">
            <el-icon><Delete /></el-icon>
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getLogList, deleteLog } from '../api/log'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'

const logList = ref([])

const loadData = async () => {
  const res = await getLogList()
  logList.value = res.data
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这条日志吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteLog(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
