<template>
  <div class="login-container">
    <div class="login-background">
      <div class="gradient-bg"></div>
      <div class="particles"></div>
    </div>

    <div class="login-content">
      <div class="login-left">
        <div class="system-info">
          <h1 class="system-title">
            <el-icon class="title-icon"><School /></el-icon>
            学生成绩管理系统
          </h1>
          <p class="system-desc">现代化的教育管理平台</p>
          <div class="features">
            <div class="feature-item" v-for="(item, index) in features" :key="index">
              <el-icon><Check /></el-icon>
              <span>{{ item }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="login-right">
        <el-card class="login-card" shadow="hover">
          <div class="card-header">
            <h2>欢迎登录</h2>
            <p>请输入您的账号和密码</p>
          </div>

          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="rules"
            class="login-form"
            @keyup.enter="handleLogin"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                size="large"
                clearable
              >
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                show-password
                clearable
              >
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                size="large"
                :loading="loading"
                class="login-button"
                @click="handleLogin"
              >
                {{ loading ? '登录中...' : '登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <div class="login-tips">
            <el-divider>测试账号</el-divider>
            <div class="test-accounts">
              <el-tag type="success" size="small">管理员: admin / 123456</el-tag>
              <el-tag type="primary" size="small">教师: teacher001 / 123456</el-tag>
              <el-tag type="warning" size="small">学生: student001 / 123456</el-tag>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const features = [
  '智能化成绩管理',
  '多角色权限控制',
  '数据可视化分析',
  '便捷的操作体验'
]

const handleLogin = async () => {
  if (!loginFormRef.value) return

  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const result = await userStore.login(loginForm)
        if (result.success) {
          ElMessage.success('登录成功')
          router.push('/')
        } else {
          ElMessage.error(result.message || '登录失败')
        }
      } catch (error) {
        ElMessage.error('登录失败，请检查网络连接')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.gradient-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  animation: gradientShift 15s ease infinite;
}

@keyframes gradientShift {
  0%, 100% {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }
  50% {
    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  }
}

.particles {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image:
    radial-gradient(circle, rgba(255, 255, 255, 0.1) 1px, transparent 1px),
    radial-gradient(circle, rgba(255, 255, 255, 0.1) 1px, transparent 1px);
  background-size: 50px 50px, 80px 80px;
  background-position: 0 0, 40px 40px;
  animation: particlesMove 20s linear infinite;
}

@keyframes particlesMove {
  0% {
    background-position: 0 0, 40px 40px;
  }
  100% {
    background-position: 50px 50px, 90px 90px;
  }
}

.login-content {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 40px;
  z-index: 1;
}

.login-left {
  flex: 1;
  color: white;
  padding-right: 60px;
}

.system-info {
  animation: fadeInLeft 1s ease;
}

@keyframes fadeInLeft {
  from {
    opacity: 0;
    transform: translateX(-50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.system-title {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
}

.title-icon {
  font-size: 56px;
}

.system-desc {
  font-size: 24px;
  margin-bottom: 40px;
  opacity: 0.9;
}

.features {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 18px;
  opacity: 0.95;
}

.feature-item .el-icon {
  font-size: 24px;
  color: #67C23A;
}

.login-right {
  width: 450px;
  animation: fadeInRight 1s ease;
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translateX(50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.login-card {
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 48px rgba(0, 0, 0, 0.15);
}

.card-header {
  text-align: center;
  margin-bottom: 30px;
}

.card-header h2 {
  font-size: 28px;
  color: #303133;
  margin-bottom: 10px;
}

.card-header p {
  color: #909399;
  font-size: 14px;
}

.login-form {
  margin-top: 30px;
}

.login-form :deep(.el-form-item) {
  margin-bottom: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 10px;
  padding: 12px 15px;
}

.login-button {
  width: 100%;
  height: 48px;
  border-radius: 10px;
  font-size: 16px;
  font-weight: bold;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s ease;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

.login-tips {
  margin-top: 20px;
}

.test-accounts {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
}

.test-accounts .el-tag {
  width: 100%;
  justify-content: center;
  padding: 8px;
  font-size: 13px;
}

@media (max-width: 768px) {
  .login-content {
    flex-direction: column;
    justify-content: center;
    padding: 20px;
  }

  .login-left {
    display: none;
  }

  .login-right {
    width: 100%;
    max-width: 400px;
  }
}
</style>
