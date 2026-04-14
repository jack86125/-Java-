<template>
  <div class="dashboard-container">
    <div class="welcome-card">
      <div class="welcome-content">
        <h2>欢迎回来，{{ userStore.userInfo.realName }}！</h2>
        <p>{{ getGreeting() }}，祝您工作愉快！</p>
      </div>
      <div class="welcome-icon">
        <el-icon><Sunny /></el-icon>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6" v-for="(stat, index) in stats" :key="index">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-icon" :style="{ background: stat.color }">
              <el-icon>
                <component :is="stat.icon" />
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stat.value }}</div>
              <div class="stat-label">{{ stat.label }}</div>
            </div>
          </div>
          <div class="stat-footer">
            <span class="stat-trend" :class="stat.trend > 0 ? 'up' : 'down'">
              <el-icon v-if="stat.trend > 0"><CaretTop /></el-icon>
              <el-icon v-else><CaretBottom /></el-icon>
              {{ Math.abs(stat.trend) }}%
            </span>
            <span class="stat-desc">较上周</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :xs="24" :md="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>成绩分布统计</span>
              <el-button type="primary" size="small" text>查看详情</el-button>
            </div>
          </template>
          <div ref="scoreChartRef" class="chart-container"></div>
        </el-card>
      </el-col>

      <el-col :xs="24" :md="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>学生人数统计</span>
              <el-button type="primary" size="small" text>查看详情</el-button>
            </div>
          </template>
          <div ref="studentChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷操作 -->
    <el-card class="quick-actions-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>快捷操作</span>
        </div>
      </template>
      <el-row :gutter="20">
        <el-col :xs="12" :sm="6" v-for="(action, index) in quickActions" :key="index">
          <div class="action-item" @click="handleAction(action.path)">
            <el-icon class="action-icon" :style="{ color: action.color }">
              <component :is="action.icon" />
            </el-icon>
            <div class="action-label">{{ action.label }}</div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import * as echarts from 'echarts'

const router = useRouter()
const userStore = useUserStore()

const scoreChartRef = ref(null)
const studentChartRef = ref(null)

// 统计数据
const stats = ref([
  {
    label: '学生总数',
    value: '1,234',
    icon: 'User',
    color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    trend: 12
  },
  {
    label: '教师总数',
    value: '89',
    icon: 'UserFilled',
    color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    trend: 5
  },
  {
    label: '课程总数',
    value: '156',
    icon: 'Reading',
    color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    trend: -3
  },
  {
    label: '成绩记录',
    value: '8,567',
    icon: 'Document',
    color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
    trend: 18
  }
])

// 快捷操作
const quickActions = ref([
  { label: '学生管理', icon: 'User', path: '/student', color: '#667eea' },
  { label: '教师管理', icon: 'UserFilled', path: '/teacher', color: '#f5576c' },
  { label: '课程管理', icon: 'Reading', path: '/course', color: '#00f2fe' },
  { label: '成绩管理', icon: 'Document', path: '/score', color: '#38f9d7' },
  { label: '班级管理', icon: 'School', path: '/class', color: '#fa709a' },
  { label: '我的成绩', icon: 'Tickets', path: '/my-score', color: '#30cfd0' },
  { label: '个人信息', icon: 'User', path: '/profile', color: '#a8edea' },
  { label: '系统设置', icon: 'Setting', path: '/settings', color: '#fed6e3' }
])

// 获取问候语
const getGreeting = () => {
  const hour = new Date().getHours()
  if (hour < 6) return '凌晨好'
  if (hour < 9) return '早上好'
  if (hour < 12) return '上午好'
  if (hour < 14) return '中午好'
  if (hour < 17) return '下午好'
  if (hour < 19) return '傍晚好'
  if (hour < 22) return '晚上好'
  return '夜深了'
}

// 初始化成绩分布图表
const initScoreChart = () => {
  const chart = echarts.init(scoreChartRef.value)
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: ['0-59', '60-69', '70-79', '80-89', '90-100'],
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      }
    },
    yAxis: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      }
    },
    series: [
      {
        name: '人数',
        type: 'bar',
        data: [45, 123, 456, 789, 234],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#667eea' },
            { offset: 1, color: '#764ba2' }
          ])
        },
        barWidth: '60%'
      }
    ]
  }
  chart.setOption(option)

  // 响应式
  window.addEventListener('resize', () => {
    chart.resize()
  })
}

// 初始化学生人数统计图表
const initStudentChart = () => {
  const chart = echarts.init(studentChartRef.value)
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      right: 10,
      top: 'center'
    },
    series: [
      {
        name: '学生分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 335, name: '计算机学院', itemStyle: { color: '#667eea' } },
          { value: 310, name: '软件学院', itemStyle: { color: '#f5576c' } },
          { value: 234, name: '数学学院', itemStyle: { color: '#00f2fe' } },
          { value: 135, name: '物理学院', itemStyle: { color: '#38f9d7' } },
          { value: 220, name: '其他学院', itemStyle: { color: '#fa709a' } }
        ]
      }
    ]
  }
  chart.setOption(option)

  // 响应式
  window.addEventListener('resize', () => {
    chart.resize()
  })
}

// 处理快捷操作
const handleAction = (path) => {
  router.push(path)
}

onMounted(() => {
  nextTick(() => {
    initScoreChart()
    initStudentChart()
  })
})
</script>

<style scoped>
.dashboard-container {
  padding: 0;
}

.welcome-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  padding: 30px;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: white;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.welcome-content h2 {
  font-size: 28px;
  margin-bottom: 10px;
}

.welcome-content p {
  font-size: 16px;
  opacity: 0.9;
}

.welcome-icon {
  font-size: 80px;
  opacity: 0.3;
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  border-radius: 12px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: white;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.stat-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 15px;
  border-top: 1px solid #f0f0f0;
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  font-weight: 500;
}

.stat-trend.up {
  color: #67C23A;
}

.stat-trend.down {
  color: #F56C6C;
}

.stat-desc {
  font-size: 12px;
  color: #909399;
}

.charts-row {
  margin-bottom: 20px;
}

.chart-card {
  border-radius: 12px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 500;
}

.chart-container {
  width: 100%;
  height: 350px;
}

.quick-actions-card {
  border-radius: 12px;
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: #f5f7fa;
}

.action-item:hover {
  background: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-3px);
}

.action-icon {
  font-size: 36px;
  margin-bottom: 10px;
}

.action-label {
  font-size: 14px;
  color: #303133;
  font-weight: 500;
}

@media (max-width: 768px) {
  .welcome-card {
    flex-direction: column;
    text-align: center;
  }

  .welcome-icon {
    margin-top: 20px;
  }

  .chart-container {
    height: 250px;
  }
}
</style>
