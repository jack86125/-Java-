import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: '仪表盘', icon: 'DataLine' }
      },
      {
        path: '/student',
        name: 'Student',
        component: () => import('../views/Student.vue'),
        meta: { title: '学生管理', icon: 'User', roles: ['admin'] }
      },
      {
        path: '/teacher',
        name: 'Teacher',
        component: () => import('../views/Teacher.vue'),
        meta: { title: '教师管理', icon: 'UserFilled', roles: ['admin'] }
      },
      {
        path: '/course',
        name: 'Course',
        component: () => import('../views/Course.vue'),
        meta: { title: '课程管理', icon: 'Reading', roles: ['admin'] }
      },
      {
        path: '/score',
        name: 'Score',
        component: () => import('../views/Score.vue'),
        meta: { title: '成绩管理', icon: 'Document', roles: ['admin', 'teacher'] }
      },
      {
        path: '/class',
        name: 'Class',
        component: () => import('../views/Class.vue'),
        meta: { title: '班级管理', icon: 'School', roles: ['admin'] }
      },
      {
        path: '/my-score',
        name: 'MyScore',
        component: () => import('../views/MyScore.vue'),
        meta: { title: '我的成绩', icon: 'Tickets', roles: ['student'] }
      },
      {
        path: '/profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue'),
        meta: { title: '个人信息', icon: 'User' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')

  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 学生成绩管理系统` : '学生成绩管理系统'

  // 如果访问登录页，直接放行
  if (to.path === '/login') {
    if (token) {
      next('/')
    } else {
      next()
    }
    return
  }

  // 如果没有token，跳转到登录页
  if (!token) {
    next('/login')
    return
  }

  // 检查角色权限
  if (to.meta.roles && to.meta.roles.length > 0) {
    if (to.meta.roles.includes(userInfo.role)) {
      next()
    } else {
      next('/dashboard')
    }
  } else {
    next()
  }
})

export default router
