import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    employee: JSON.parse(localStorage.getItem('employee') || 'null'),
    token: localStorage.getItem('token') || ''
  },
  getters: {
    employee: state => state.employee,
    token: state => state.token,
    isLoggedIn: state => !!state.token
  },
  mutations: {
    SET_EMPLOYEE(state, employee) {
      state.employee = employee
      localStorage.setItem('employee', JSON.stringify(employee))
    },
    SET_TOKEN(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
    LOGOUT(state) {
      state.employee = null
      state.token = ''
      localStorage.removeItem('employee')
      localStorage.removeItem('token')
    }
  },
  actions: {
    login({ commit }, { employee, token }) {
      commit('SET_EMPLOYEE', employee)
      commit('SET_TOKEN', token)
    },
    logout({ commit }) {
      commit('LOGOUT')
    }
  }
})
