<template>
  <el-card shadow="hover">
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <span style="font-size: 16px; font-weight: 600; color: #303133;">员工管理</span>
        <el-button type="primary" @click="openDialog()">
          <el-icon><Plus /></el-icon>
          新增员工
        </el-button>
      </div>
    </template>
    <el-table :data="employeeList" border stripe style="width: 100%;" :cell-style="{ padding: '12px 0', textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">
      <el-table-column prop="id" label="工号" min-width="120" align="center" />
      <el-table-column prop="name" label="姓名" min-width="120" align="center" />
      <el-table-column prop="department.name" label="部门" min-width="180" align="center" />
      <el-table-column prop="post" label="职位" min-width="150" align="center">
        <template #default="{ row }">
          <el-tag :type="getPostType(row.post)" size="small">{{ row.post }}</el-tag>
        </template>
      </el-table-column>
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
      <el-form-item label="工号">
        <el-input v-model="form.id" :disabled="isEdit" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" style="width: 100%;" />
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="form.departmentId" style="width: 100%;">
          <el-option v-for="dept in departmentList" :key="dept.id" :label="dept.name" :value="dept.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="职位">
        <el-select v-model="form.post" style="width: 100%;">
          <el-option label="总经理" value="总经理" />
          <el-option label="部门经理" value="部门经理" />
          <el-option label="财务" value="财务" />
          <el-option label="员工" value="员工" />
        </el-select>
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
import { getEmployeeList, addEmployee, updateEmployee, deleteEmployee } from '../api/employee'
import { getDepartmentList } from '../api/department'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'

const employeeList = ref([])
const departmentList = ref([])

const getPostType = (post) => {
  const map = {
    '总经理': 'danger',
    '部门经理': 'warning',
    '财务': 'success',
    '员工': 'info'
  }
  return map[post] || ''
}
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const loading = ref(false)
const form = reactive({
  id: '',
  name: '',
  departmentId: '',
  post: ''
})

const loadData = async () => {
  const [empRes, deptRes] = await Promise.all([
    getEmployeeList(),
    getDepartmentList()
  ])
  employeeList.value = empRes.data
  departmentList.value = deptRes.data
}

const openDialog = (row = null) => {
  isEdit.value = !!row
  dialogTitle.value = row ? '编辑员工' : '新增员工'
  if (row) {
    form.id = row.id
    form.name = row.name
    form.departmentId = row.departmentId
    form.post = row.post
  } else {
    form.id = ''
    form.name = ''
    form.departmentId = ''
    form.post = ''
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.id || !form.name || !form.departmentId || !form.post) {
    ElMessage.warning('请填写完整信息')
    return
  }
  loading.value = true
  try {
    if (isEdit.value) {
      await updateEmployee(form.id, form)
    } else {
      await addEmployee(form)
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
  ElMessageBox.confirm('确定要删除这个员工吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteEmployee(id)
    ElMessage.success('删除成功')
    loadData()
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>
