import request from '@/utils/request'

export function getEmployeeList() {
  return request({
    url: '/employees',
    method: 'get'
  })
}

export function getEmployeeById(id) {
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

export function updateEmployee(id, data) {
  return request({
    url: `/employees/${id}`,
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

export function getEmployeesByDepartmentAndPost(departmentId, post) {
  return request({
    url: '/employees/by-department-post',
    method: 'get',
    params: { departmentId, post }
  })
}
