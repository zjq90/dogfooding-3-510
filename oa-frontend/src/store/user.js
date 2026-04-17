import { defineStore } from 'pinia'
import { login, logout, getInfo, changePassword } from '../api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    employee: null
  }),

  getters: {
    isLoggedIn: state => !!state.employee
  },

  actions: {
    async login(loginForm) {
      const res = await login(loginForm)
      this.employee = res.data.employee
      return res
    },

    async logout() {
      await logout()
      this.employee = null
    },

    async getInfo() {
      try {
        const res = await getInfo()
        this.employee = res.data
        return res
      } catch (error) {
        this.employee = null
        throw error
      }
    },

    async changePassword(passwordForm) {
      return await changePassword(passwordForm)
    }
  }
})
