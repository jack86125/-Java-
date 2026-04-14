import request from './request'

// 获取学生列表
export const getStudentList = (params) => {
  return request({
    url: '/student/list',
    method: 'get',
    params
  })
}

// 获取学生详情
export const getStudentById = (id) => {
  return request({
    url: `/student/${id}`,
    method: 'get'
  })
}

// 添加学生
export const addStudent = (data) => {
  return request({
    url: '/student',
    method: 'post',
    data
  })
}

// 更新学生信息
export const updateStudent = (data) => {
  return request({
    url: '/student',
    method: 'put',
    data
  })
}

// 删除学生
export const deleteStudent = (id) => {
  return request({
    url: `/student/${id}`,
    method: 'delete'
  })
}
