import request from './request'

export function getLogList() {
  return request({
    url: '/logs',
    method: 'get'
  })
}

export function deleteLog(id) {
  return request({
    url: `/logs/${id}`,
    method: 'delete'
  })
}
