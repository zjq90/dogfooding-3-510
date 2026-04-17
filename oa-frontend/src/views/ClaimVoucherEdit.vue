<template>
  <el-card shadow="hover" v-if="loaded">
    <template #header>
      <span style="font-size: 16px; font-weight: 600; color: #303133;">修改报销单</span>
    </template>
    <el-form :model="form" label-width="120px" style="max-width: 900px;" size="default">
      <el-form-item label="报销事由">
        <el-input v-model="form.claimVoucher.cause" type="textarea" :rows="3" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="报销明细">
        <el-table :data="form.items" border style="width: 100%;" :cell-style="{ padding: '8px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
          <el-table-column label="费用类型" min-width="180" align="center">
            <template #default="{ row }">
              <el-select v-model="row.item" style="width: 95%;">
                <el-option label="交通" value="交通" />
                <el-option label="住宿" value="住宿" />
                <el-option label="餐饮" value="餐饮" />
                <el-option label="通讯" value="通讯" />
                <el-option label="办公" value="办公" />
                <el-option label="其他" value="其他" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="金额" min-width="180" align="center">
            <template #default="{ row }">
              <el-input-number v-model="row.amount" :min="0" :precision="2" style="width: 95%;" />
            </template>
          </el-table-column>
          <el-table-column label="说明" min-width="200" align="center">
            <template #default="{ row }">
              <el-input v-model="row.comment" style="width: 95%;" />
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="120" align="center">
            <template #default="{ $index }">
              <el-button type="danger" link @click="removeItem($index)">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 15px;">
          <el-button type="primary" @click="addItem">
            <el-icon><Plus /></el-icon>
            添加明细
          </el-button>
        </div>
      </el-form-item>
      <el-form-item label="总金额">
        <span style="font-size: 22px; font-weight: 700; color: #f56c6c;">¥{{ totalAmount }}</span>
      </el-form-item>
      <el-form-item style="margin-top: 20px;">
        <el-button type="primary" size="default" :loading="loading" @click="handleSave">
          <el-icon><Check /></el-icon>
          保存
        </el-button>
        <el-button size="default" @click="$router.back()">
          <el-icon><Back /></el-icon>
          返回
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getClaimVoucherDetail, updateClaimVoucher } from '../api/claimVoucher'
import { ElMessage } from 'element-plus'
import { Delete, Plus, Check, Back } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const loaded = ref(false)
const loading = ref(false)
const form = reactive({
  claimVoucher: {
    id: null,
    cause: ''
  },
  items: []
})

const totalAmount = computed(() => {
  return form.items.reduce((sum, item) => sum + (item.amount || 0), 0).toFixed(2)
})

const addItem = () => {
  form.items.push({ item: '', amount: 0, comment: '' })
}

const removeItem = (index) => {
  if (form.items.length > 1) {
    form.items.splice(index, 1)
  } else {
    ElMessage.warning('至少保留一条明细')
  }
}

const loadData = async () => {
  const res = await getClaimVoucherDetail(route.params.id)
  form.claimVoucher.id = res.data.claimVoucher.id
  form.claimVoucher.cause = res.data.claimVoucher.cause
  form.items = res.data.items
  loaded.value = true
}

const handleSave = async () => {
  if (!form.claimVoucher.cause) {
    ElMessage.warning('请填写报销事由')
    return
  }
  form.claimVoucher.totalAmount = parseFloat(totalAmount.value)
  loading.value = true
  try {
    await updateClaimVoucher(form.claimVoucher.id, form)
    ElMessage.success('保存成功')
    router.push('/claim-voucher/self')
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
