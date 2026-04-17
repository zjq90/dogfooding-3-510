<template>
  <el-card shadow="hover">
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <span style="font-size: 16px; font-weight: 600; color: #303133;">部门管理</span>
        <el-button type="primary" @click="openDialog()">
          <el-icon><Plus /></el-icon>
          新增部门
        </el-button>
      </div>
    </template>
    <el-table :data="departmentList" border stripe style="width: 100%;" :cell-style="{ padding: '12px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
      <el-table-column prop="id" label="部门编号" min-width="150" align="center" />
      <el-table-column prop="name" label="部门名称" min-width="200" align="center" />
      <el-table-column prop="address" label="部门地址" min-width="200" align="center" />
      <el-table-column label="操作" min-width="200" align="center">
        <template #default="{ row }">
          <el-button type="primary" link @click="openDialog(row)">
            <el-icon><Edit /></el-icon>
            编辑
          </el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">
            <el-icon><Delete /></el-icon>
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="550px" top="15vh">
    <el-form :model="form" label-width="100px" size="default">
      <el-form-item label="部门编号">
        <el-input v-model="form.id" :disabled="isEdit" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="部门名称">
        <el-input v-model="form.name" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="部门地址">
        <el-input v-model="form.address" style="width: 100%;" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div style="text-align: right;">
        <el-button size="default" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" size="default" :loading="loading" @click="handleSubmit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getDepartmentList, addDepartment, updateDepartment, deleteDepartment } from '../api/department'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'

const departmentList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const loading = ref(false)
const form = reactive({
  id: '',
  name: '',
  address: ''
})

const loadData = async () => {
  const res = await getDepartmentList()
  departmentList.value = res.data
}

const openDialog = (row = null) => {
  isEdit.value = !!row
  dialogTitle.value = row ? '编辑部门' : '新增部门'
  if (row) {
    form.id = row.id
    form.name = row.name
    form.address = row.address
  } else {
    form.id = ''
    form.name = ''
    form.address = ''
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.id || !form.name) {
    ElMessage.warning('请填写完整信息')
    return
  }
  loading.value = true
  try {
    if (isEdit.value) {
      await updateDepartment(form.id, form)
    } else {
      await addDepartment(form)
    }
    ElMessage.success(isEdit.value ? '修改成功' : '新增成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除这个部门吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteDepartment(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
