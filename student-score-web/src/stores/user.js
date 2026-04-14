import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '../api/auth'
import router from '../router'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

  // 登录
  const login = async (loginForm) => {
    try {
      const res = await loginApi(loginForm)
      if (res.code === 200) {
        token.value = res.data.token
        userInfo.value = {
          id: res.data.id,
          username: res.data.username,
          realName: res.data.realName,
          role: res.data.role
        }

        // 保存到本地存储
        localStorage.setItem('token', token.value)
        localStorage.setItem('userInfo', JSON.stringify(userInfo.value))

        return { success: true }
      } else {
        return { success: false, message: res.message }
      }
    } catch (error) {
      return { success: false, message: error.message || '登录失败' }
    }
  }

  // 登出
  const logout = () => {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    router.push('/login')
  }

  // 获取用户角色
  const getRole = () => {
    return userInfo.value.role || ''
  }

  // 检查是否有权限
  const hasRole = (roles) => {
    if (!roles || roles.length === 0) return true
    return roles.includes(userInfo.value.role)
  }

  return {
    token,
    userInfo,
    login,
    logout,
    getRole,
    hasRole
  }
})
