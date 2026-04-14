import request from './request'

// 获取班级列表
export const getClassList = (params) => {
  return request({
    url: '/class/list',
    method: 'get',
    params
  })
}

// 获取班级详情
export const getClassById = (id) => {
  return request({
    url: `/class/${id}`,
    method: 'get'
  })
}

// 添加班级
export const addClass = (data) => {
  return request({
    url: '/class',
    method: 'post',
    data
  })
}

// 更新班级信息
export const updateClass = (data) => {
  return request({
    url: '/class',
    method: 'put',
    data
  })
}

// 删除班级
export const deleteClass = (id) => {
  return request({
    url: `/class/${id}`,
    method: 'delete'
  })
}
