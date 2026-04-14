<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '240px'" class="layout-aside">
      <div class="logo-container">
        <el-icon class="logo-icon" v-if="!isCollapse"><School /></el-icon>
        <el-icon class="logo-icon-small" v-else><School /></el-icon>
        <transition name="fade">
          <span class="logo-text" v-if="!isCollapse">成绩管理系统</span>
        </transition>
      </div>

      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :unique-opened="true"
        class="sidebar-menu"
        router
      >
        <template v-for="route in menuRoutes" :key="route.path">
          <el-menu-item
            v-if="!route.meta.roles || hasRole(route.meta.roles)"
            :index="route.path"
          >
            <el-icon>
              <component :is="route.meta.icon" />
            </el-icon>
            <template #title>{{ route.meta.title }}</template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <!-- 主内容区 -->
    <el-container class="main-container">
      <!-- 顶部导航 -->
      <el-header class="layout-header">
        <div class="header-left">
          <el-icon class="collapse-icon" @click="toggleCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentRoute.meta.title">
              {{ currentRoute.meta.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <el-tooltip content="刷新页面" placement="bottom">
            <el-icon class="header-icon" @click="refreshPage">
              <Refresh />
            </el-icon>
          </el-tooltip>

          <el-tooltip content="全屏" placement="bottom">
            <el-icon class="header-icon" @click="toggleFullscreen">
              <FullScreen />
            </el-icon>
          </el-tooltip>

          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="36" class="user-avatar">
                <el-icon><User /></el-icon>
              </el-avatar>
              <span class="user-name">{{ userStore.userInfo.realName }}</span>
              <el-icon class="dropdown-icon"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>
                  个人信息
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区 -->
      <el-main class="layout-main">
        <transition name="fade-transform" mode="out-in">
          <router-view v-slot="{ Component }">
            <keep-alive>
              <component :is="Component" />
            </keep-alive>
          </router-view>
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const isCollapse = ref(false)
const activeMenu = ref(route.path)
const currentRoute = computed(() => route)

// 获取菜单路由
const menuRoutes = computed(() => {
  const routes = router.getRoutes()
  return routes
    .filter(r => r.path !== '/login' && r.path !== '/' && r.meta?.title)
    .sort((a, b) => {
      // 按照特定顺序排序
      const order = {
        '/dashboard': 1,
        '/student': 2,
        '/teacher': 3,
        '/course': 4,
        '/score': 5,
        '/class': 6,
        '/my-score': 7,
        '/profile': 8
      }
      return (order[a.path] || 99) - (order[b.path] || 99)
    })
})

// 监听路由变化
watch(() => route.path, (newPath) => {
  activeMenu.value = newPath
})

// 切换侧边栏折叠
const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

// 检查角色权限
const hasRole = (roles) => {
  return userStore.hasRole(roles)
}

// 刷新页面
const refreshPage = () => {
  router.go(0)
}

// 全屏切换
const toggleFullscreen = () => {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
  } else {
    document.exitFullscreen()
  }
}

// 处理下拉菜单命令
const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      userStore.logout()
      ElMessage.success('退出登录成功')
    }).catch(() => {})
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
  width: 100vw;
}

.layout-aside {
  background: linear-gradient(180deg, #304156 0%, #1f2d3d 100%);
  transition: width 0.3s ease;
  overflow-x: hidden;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
}

.logo-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  padding: 0 20px;
  background: rgba(0, 0, 0, 0.2);
  color: white;
  font-size: 18px;
  font-weight: bold;
  gap: 10px;
}

.logo-icon {
  font-size: 32px;
  color: #409EFF;
}

.logo-icon-small {
  font-size: 28px;
  color: #409EFF;
}

.logo-text {
  white-space: nowrap;
}

.sidebar-menu {
  border: none;
  background: transparent;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.sidebar-menu::-webkit-scrollbar {
  width: 6px;
}

.sidebar-menu::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

.sidebar-menu :deep(.el-menu-item) {
  color: rgba(255, 255, 255, 0.8);
  transition: all 0.3s ease;
}

.sidebar-menu :deep(.el-menu-item:hover) {
  background: rgba(64, 158, 255, 0.2) !important;
  color: white;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  background: linear-gradient(90deg, #409EFF 0%, #66b1ff 100%) !important;
  color: white;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.3);
}

.main-container {
  background: #f5f7fa;
}

.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: white;
  padding: 0 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.collapse-icon {
  font-size: 24px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.collapse-icon:hover {
  color: #409EFF;
  transform: scale(1.1);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-icon {
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.header-icon:hover {
  color: #409EFF;
  transform: scale(1.1);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.user-info:hover {
  background: #f5f7fa;
}

.user-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.user-name {
  font-size: 14px;
  color: #303133;
  font-weight: 500;
}

.dropdown-icon {
  font-size: 12px;
  color: #909399;
}

.layout-main {
  padding: 20px;
  overflow-y: auto;
  height: calc(100vh - 60px);
}

.layout-main::-webkit-scrollbar {
  width: 8px;
}

.layout-main::-webkit-scrollbar-thumb {
  background: #dcdfe6;
  border-radius: 4px;
}

.layout-main::-webkit-scrollbar-thumb:hover {
  background: #c0c4cc;
}

/* 动画效果 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.3s ease;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>
