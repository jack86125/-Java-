import request from './request'

// 获取课程列表
export const getCourseList = (params) => {
  return request({
    url: '/course/list',
    method: 'get',
    params
  })
}

// 获取课程详情
export const getCourseById = (id) => {
  return request({
    url: `/course/${id}`,
    method: 'get'
  })
}

// 添加课程
export const addCourse = (data) => {
  return request({
    url: '/course',
    method: 'post',
    data
  })
}

// 更新课程信息
export const updateCourse = (data) => {
  return request({
    url: '/course',
    method: 'put',
    data
  })
}

// 删除课程
export const deleteCourse = (id) => {
  return request({
    url: `/course/${id}`,
    method: 'delete'
  })
}
