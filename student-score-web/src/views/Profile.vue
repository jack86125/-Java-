<template>
  <div class="profile-container">
    <el-card class="profile-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
        </div>
      </template>

      <div class="profile-content">
        <div class="avatar-section">
          <el-avatar :size="120" class="avatar">
            <el-icon><User /></el-icon>
          </el-avatar>
          <h2>{{ userStore.userInfo.realName }}</h2>
          <el-tag :type="getRoleType(userStore.userInfo.role)" size="large">
            {{ getRoleText(userStore.userInfo.role) }}
          </el-tag>
        </div>

        <el-divider />

        <div class="info-section">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="用户名">
              {{ userStore.userInfo.username }}
            </el-descriptions-item>
            <el-descriptions-item label="真实姓名">
              {{ userStore.userInfo.realName }}
            </el-descriptions-item>
            <el-descriptions-item label="角色">
              <el-tag :type="getRoleType(userStore.userInfo.role)">
                {{ getRoleText(userStore.userInfo.role) }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="用户ID">
              {{ userStore.userInfo.id }}
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <el-divider />

        <div class="action-section">
          <el-button type="primary" @click="handleChangePassword">
            <el-icon><Lock /></el-icon>
            修改密码
          </el-button>
          <el-button type="danger" @click="handleLogout">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 修改密码对话框 -->
    <el-dialog v-model="dialogVisible" title="修改密码" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="form.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const dialogVisible = ref(false)
const formRef = ref(null)

const form = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== form.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const getRoleType = (role) => {
  const types = {
    admin: 'danger',
    teacher: 'warning',
    student: 'success'
  }
  return types[role] || 'info'
}

const getRoleText = (role) => {
  const texts = {
    admin: '管理员',
    teacher: '教师',
    student: '学生'
  }
  return texts[role] || '未知'
}

const handleChangePassword = () => {
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate((valid) => {
    if (valid) {
      ElMessage.info('修改密码功能开发中...')
      dialogVisible.value = false
    }
  })
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    ElMessage.success('退出登录成功')
  })
}
</script>

<style scoped>
.profile-container {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.profile-card {
  width: 100%;
  max-width: 800px;
  border-radius: 12px;
}

.card-header {
  font-size: 18px;
  font-weight: 500;
}

.profile-content {
  padding: 20px;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.avatar-section h2 {
  margin: 0;
  font-size: 24px;
  color: #303133;
}

.info-section {
  margin: 20px 0;
}

.action-section {
  display: flex;
  justify-content: center;
  gap: 20px;
}
</style>
