import { defineStore } from 'pinia'
import router from '@/router/index.js'
import { isLoggedIn } from '@/authStatus.js'
import axios from 'axios'

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => {
    return {
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null,
      token: localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')) : '',
      role: localStorage.getItem('role') ? JSON.parse(localStorage.getItem('role')) : null,
      returnUrl: '/'
    }
  },
  actions: {
    async login(email, password) {
      const response = await axios.post('api/token', {
        email: email,
        password: password
      })

      if (response.status == 200) {
        const token = await response.data['token']
        localStorage.setItem('user', JSON.stringify(email))
        localStorage.setItem('token', JSON.stringify(token))
        this.user = email
        this.token = token
        isLoggedIn.value = true
        await this.fetchUser()
        router.push(this.returnUrl || '/')
      }
    },
    async fetchUser() {
      const response = await axios.get('api/users/user')

      if (response.status == 200) {
        const role = response.data['role']
        localStorage.setItem('role', JSON.stringify(role))
        this.role = role
      }
    },
    logout() {
      this.user = null
      this.token = ''
      this.role = ''
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    }
  }
})
