import Vue from 'vue'
import Vuex from 'vuex'
import { getCurrentUser } from '@/api/auth'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null
  },
  getters: {
    isLoggedIn: state => !!state.user,
    currentUser: state => state.user
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user
    },
    CLEAR_USER(state) {
      state.user = null
    }
  },
  actions: {
    async fetchUser({ commit }) {
      try {
        const res = await getCurrentUser()
        commit('SET_USER', res.data)
        return res.data
      } catch (error) {
        commit('CLEAR_USER')
        return null
      }
    },
    logout({ commit }) {
      commit('CLEAR_USER')
    }
  }
})
