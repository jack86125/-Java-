import request from './request'

// 获取成绩列表
export const getScoreList = (params) => {
  return request({
    url: '/score/list',
    method: 'get',
    params
  })
}

// 获取成绩详情
export const getScoreById = (id) => {
  return request({
    url: `/score/${id}`,
    method: 'get'
  })
}

// 添加成绩
export const addScore = (data) => {
  return request({
    url: '/score',
    method: 'post',
    data
  })
}

// 更新成绩
export const updateScore = (data, params) => {
  return request({
    url: '/score',
    method: 'put',
    data,
    params
  })
}

// 删除成绩
export const deleteScore = (id) => {
  return request({
    url: `/score/${id}`,
    method: 'delete'
  })
}

// 根据学生ID获取成绩
export const getScoresByStudentId = (studentId) => {
  return request({
    url: `/score/student/${studentId}`,
    method: 'get'
  })
}

// 根据课程ID获取成绩
export const getScoresByCourseId = (courseId) => {
  return request({
    url: `/score/course/${courseId}`,
    method: 'get'
  })
}

// 获取成绩修改历史
export const getScoreHistory = (scoreId) => {
  return request({
    url: `/score/history/${scoreId}`,
    method: 'get'
  })
}
