import request from './request'

export function getDepartmentList() {
  return request({
    url: '/departments',
    method: 'get'
  })
}

export function getDepartment(id) {
  return request({
    url: `/departments/${id}`,
    method: 'get'
  })
}

export function addDepartment(data) {
  return request({
    url: '/departments',
    method: 'post',
    data
  })
}

export function updateDepartment(data) {
  return request({
    url: '/departments',
    method: 'put',
    data
  })
}

export function deleteDepartment(id) {
  return request({
    url: `/departments/${id}`,
    method: 'delete'
  })
}
