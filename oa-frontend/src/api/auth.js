import request from './request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function changePassword(data) {
  return request({
    url: '/auth/change-password',
    method: 'post',
    data
  })
}

export function getEmployeeInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}
