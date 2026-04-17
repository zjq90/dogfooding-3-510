<template>
  <div class="form-container">
    <h3 style="margin-bottom: 20px;">{{ isEdit ? '编辑报销单' : '填写报销单' }}</h3>
    <el-form :model="form" :rules="rules" ref="form" label-width="100px">
      <el-form-item label="报销事由" prop="claimVoucher.cause">
        <el-input v-model="form.claimVoucher.cause" placeholder="请输入报销事由"></el-input>
      </el-form-item>

      <div class="detail-section">
        <div class="detail-section-title">
          报销项目
          <el-button type="primary" size="small" style="float: right;" @click="addItem">添加项目</el-button>
        </div>
        <el-table :data="form.items" border>
          <el-table-column label="费用类型" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.item" placeholder="请选择">
                <el-option
                  v-for="type in expenseTypes"
                  :key="type"
                  :label="type"
                  :value="type"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="金额" width="150">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.amount" :min="0" :precision="2" style="width: 100%;"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="描述">
            <template slot-scope="scope">
              <el-input v-model="scope.row.comment" placeholder="请输入描述"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" @click="removeItem(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px; text-align: right; font-size: 16px; font-weight: bold;">
          总金额: ¥{{ totalAmount.toFixed(2) }}
        </div>
      </div>

      <el-form-item style="margin-top: 20px;">
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button @click="$router.back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addClaimVoucher, updateClaimVoucher, getClaimVoucherById } from '@/api/claimVoucher'
import { getExpenseTypes } from '@/api/auth'

export default {
  name: 'ClaimVoucherForm',
  data() {
    return {
      isEdit: false,
      expenseTypes: [],
      form: {
        claimVoucher: {
          cause: ''
        },
        items: []
      },
      rules: {
        'claimVoucher.cause': [{ required: true, message: '请输入报销事由', trigger: 'blur' }]
      }
    }
  },
  computed: {
    totalAmount() {
      return this.form.items.reduce((sum, item) => sum + (item.amount || 0), 0)
    }
  },
  created() {
    this.fetchExpenseTypes()
    const id = this.$route.params.id
    if (id) {
      this.isEdit = true
      this.fetchData(id)
    } else {
      this.addItem()
    }
  },
  methods: {
    fetchExpenseTypes() {
      getExpenseTypes().then(res => {
        this.expenseTypes = res.data
      })
    },
    fetchData(id) {
      getClaimVoucherById(id).then(res => {
        this.form.claimVoucher = res.data.claimVoucher
        this.form.items = res.data.items
      })
    },
    addItem() {
      this.form.items.push({
        item: '',
        amount: 0,
        comment: ''
      })
    },
    removeItem(index) {
      this.form.items.splice(index, 1)
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.items.length === 0) {
            this.$message.error('请至少添加一个报销项目')
            return
          }
          this.form.claimVoucher.totalAmount = this.totalAmount
          const action = this.isEdit
            ? updateClaimVoucher(this.form.claimVoucher.id, this.form)
            : addClaimVoucher(this.form)
          action.then(() => {
            this.$message.success(this.isEdit ? '修改成功' : '保存成功')
            this.$router.push('/claim-vouchers')
          })
        }
      })
    }
  }
}
</script>
