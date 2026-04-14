import request from './request'

// 获取教师列表
export const getTeacherList = (params) => {
  return request({
    url: '/teacher/list',
    method: 'get',
    params
  })
}

// 获取教师详情
export const getTeacherById = (id) => {
  return request({
    url: `/teacher/${id}`,
    method: 'get'
  })
}

// 添加教师
export const addTeacher = (data) => {
  return request({
    url: '/teacher',
    method: 'post',
    data
  })
}

// 更新教师信息
export const updateTeacher = (data) => {
  return request({
    url: '/teacher',
    method: 'put',
    data
  })
}

// 删除教师
export const deleteTeacher = (id) => {
  return request({
    url: `/teacher/${id}`,
    method: 'delete'
  })
}
