import request from '@/utils/request'

export function getLogList() {
  return request({
    url: '/logs',
    method: 'get'
  })
}
