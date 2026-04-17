import request from './request'

export function getEmployeeList() {
  return request({
    url: '/employees',
    method: 'get'
  })
}

export function getEmployee(id) {
  return request({
    url: `/employees/${id}`,
    method: 'get'
  })
}

export function addEmployee(data) {
  return request({
    url: '/employees',
    method: 'post',
    data
  })
}

export function updateEmployee(data) {
  return request({
    url: '/employees',
    method: 'put',
    data
  })
}

export function deleteEmployee(id) {
  return request({
    url: `/employees/${id}`,
    method: 'delete'
  })
}

export function getPosts() {
  return request({
    url: '/employees/posts',
    method: 'get'
  })
}
