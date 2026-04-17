<template>
  <div>
    <div class="content-header">
      <h2>个人信息</h2>
    </div>
    <div class="content-card">
      <div class="form-section">
        <div class="form-section-title">基本信息</div>
        <div class="form-row">
          <div class="form-item">
            <label class="form-label">工号</label>
            <div>{{ employee ? employee.id : '' }}</div>
          </div>
          <div class="form-item">
            <label class="form-label">姓名</label>
            <div>{{ employee ? employee.name : '' }}</div>
          </div>
        </div>
        <div class="form-row">
          <div class="form-item">
            <label class="form-label">所属部门</label>
            <div>{{ employee && employee.department ? employee.department.name : '' }}</div>
          </div>
          <div class="form-item">
            <label class="form-label">职务</label>
            <div>{{ employee ? employee.post : '' }}</div>
          </div>
        </div>
      </div>
      <div style="text-align: right; margin-top: 20px;">
        <el-button @click="$router.go(-1)">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Self',
  computed: {
    ...mapGetters(['employee'])
  }
}
</script>
