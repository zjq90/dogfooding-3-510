<template>
  <div>
    <div class="content-header">
      <h2>填写报销单</h2>
    </div>
    <div class="content-card">
      <el-form :model="form" :rules="rules" ref="claimForm" label-width="100px">
        <el-form-item label="报销事由" prop="cause">
          <el-input v-model="form.claimVoucher.cause" style="width: 400px;"></el-input>
        </el-form-item>
        <el-form-item label="报销条目">
          <el-table :data="form.items" border style="width: 800px;">
            <el-table-column label="费用类型" width="200">
              <template slot-scope="scope">
                <el-select v-model="scope.row.item" placeholder="请选择">
                  <el-option v-for="item in itemTypes" :key="item" :label="item" :value="item"></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="金额" width="200">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.amount" :min="0" :precision="2"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="说明">
              <template slot-scope="scope">
                <el-input v-model="scope.row.comment"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="removeItem(scope.$index)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button type="primary" size="small" style="margin-top: 10px;" @click="addItem">添加条目</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getClaimVoucherItems, addClaimVoucher } from '@/api/claimVoucher'

export default {
  name: 'ClaimVoucherAdd',
  data() {
    return {
      itemTypes: [],
      form: {
        claimVoucher: {
          cause: ''
        },
        items: []
      },
      rules: {
        cause: [{ required: true, message: '请输入报销事由', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadItemTypes()
    this.addItem()
  },
  methods: {
    async loadItemTypes() {
      try {
        const res = await getClaimVoucherItems()
        this.itemTypes = res.data.items
      } catch (error) {
        console.error(error)
      }
    },
    addItem() {
      this.form.items.push({
        item: '',
        amount: 0,
        comment: ''
      })
    },
    removeItem(index) {
      if (this.form.items.length > 1) {
        this.form.items.splice(index, 1)
      } else {
        this.$message.warning('至少保留一条报销条目')
      }
    },
    handleSubmit() {
      this.$refs.claimForm.validate(async valid => {
        if (valid) {
          const totalAmount = this.form.items.reduce((sum, item) => sum + (item.amount || 0), 0)
          this.form.claimVoucher.totalAmount = totalAmount
          try {
            await addClaimVoucher(this.form)
            this.$message.success('保存成功')
            this.$router.push('/claim/deal')
          } catch (error) {
            console.error(error)
          }
        }
      })
    }
  }
}
</script>
