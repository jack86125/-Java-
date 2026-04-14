<template>
  <div class="my-score-container">
    <el-card class="info-card" shadow="hover">
      <div class="student-info">
        <el-avatar :size="80" class="avatar">
          <el-icon><User /></el-icon>
        </el-avatar>
        <div class="info-content">
          <h2>{{ userStore.userInfo.realName }}</h2>
          <p>学号：{{ userStore.userInfo.username }}</p>
        </div>
      </div>
    </el-card>

    <el-card class="stats-card" shadow="hover">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value">{{ totalCourses }}</div>
            <div class="stat-label">总课程数</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value">{{ averageScore }}</div>
            <div class="stat-label">平均分</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value">{{ passRate }}%</div>
            <div class="stat-label">及格率</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-value">{{ gpa }}</div>
            <div class="stat-label">GPA</div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="table-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>我的成绩</span>
          <el-button type="primary" size="small" @click="handleExport">
            <el-icon><Download /></el-icon>
            导出成绩单
          </el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column type="index" label="序号" width="60" align="center" />
        <el-table-column prop="courseId" label="课程" width="150" />
        <el-table-column prop="score" label="成绩" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getScoreType(row.score)" size="large">{{ row.score }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="examType" label="考试类型" width="100" />
        <el-table-column prop="examDate" label="考试日期" width="120" />
        <el-table-column prop="semester" label="学期" width="120" />
        <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getScoresByStudentId } from '../api/score'
import { useUserStore } from '../stores/user'

const userStore = useUserStore()
const loading = ref(false)
const tableData = ref([])

const totalCourses = computed(() => tableData.value.length)

const averageScore = computed(() => {
  if (tableData.value.length === 0) return 0
  const sum = tableData.value.reduce((acc, item) => acc + Number(item.score), 0)
  return (sum / tableData.value.length).toFixed(2)
})

const passRate = computed(() => {
  if (tableData.value.length === 0) return 0
  const passCount = tableData.value.filter(item => Number(item.score) >= 60).length
  return ((passCount / tableData.value.length) * 100).toFixed(2)
})

const gpa = computed(() => {
  if (tableData.value.length === 0) return 0
  const sum = tableData.value.reduce((acc, item) => {
    const score = Number(item.score)
    if (score >= 90) return acc + 4.0
    if (score >= 80) return acc + 3.0
    if (score >= 70) return acc + 2.0
    if (score >= 60) return acc + 1.0
    return acc
  }, 0)
  return (sum / tableData.value.length).toFixed(2)
})

const getScoreType = (score) => {
  if (score >= 90) return 'success'
  if (score >= 80) return 'primary'
  if (score >= 70) return 'warning'
  if (score >= 60) return 'info'
  return 'danger'
}

const getList = async () => {
  loading.value = true
  try {
    const res = await getScoresByStudentId(userStore.userInfo.id)
    tableData.value = res.data
  } catch (error) {
    ElMessage.error('获取成绩失败')
  } finally {
    loading.value = false
  }
}

const handleExport = () => {
  ElMessage.info('导出功能开发中...')
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.my-score-container {
  padding: 0;
}

.info-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.student-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.info-content h2 {
  margin: 0 0 10px 0;
  font-size: 24px;
  color: #303133;
}

.info-content p {
  margin: 0;
  color: #909399;
}

.stats-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.stat-item {
  text-align: center;
  padding: 20px;
  background: linear-gradient(135deg, #f5f7fa 0%, #ffffff 100%);
  border-radius: 8px;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 10px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.table-card {
  border-radius: 12px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
