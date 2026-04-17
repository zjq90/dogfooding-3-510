<template>
  <div class="form-container">
    <h3 style="margin-bottom: 20px;">个人信息</h3>
    <el-form label-width="100px" v-if="user">
      <el-form-item label="工号">
        <el-input v-model="user.id" disabled></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="user.name" disabled></el-input>
      </el-form-item>
      <el-form-item label="部门">
        <el-input v-model="departmentName" disabled></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="user.post" disabled></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Self',
  computed: {
    user() {
      return this.$store.state.user
    },
    departmentName() {
      return this.user && this.user.department ? this.user.department.name : ''
    }
  }
}
</script>
