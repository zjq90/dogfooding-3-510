import request from '../utils/request'

export function saveClaimVoucher(data) {
  return request({
    url: '/claim-vouchers',
    method: 'post',
    data
  })
}

export function updateClaimVoucher(id, data) {
  return request({
    url: `/claim-vouchers/${id}`,
    method: 'put',
    data
  })
}

export function getClaimVoucherDetail(id) {
  return request({
    url: `/claim-vouchers/${id}`,
    method: 'get'
  })
}

export function getSelfClaimVouchers() {
  return request({
    url: '/claim-vouchers/self',
    method: 'get'
  })
}

export function getDealClaimVouchers() {
  return request({
    url: '/claim-vouchers/deal',
    method: 'get'
  })
}

export function submitClaimVoucher(id) {
  return request({
    url: `/claim-vouchers/${id}/submit`,
    method: 'post'
  })
}

export function dealClaimVoucher(data) {
  return request({
    url: '/claim-vouchers/deal',
    method: 'post',
    data
  })
}
