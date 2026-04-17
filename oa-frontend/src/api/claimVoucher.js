import request from './request'

export function getClaimVoucherItems() {
  return request({
    url: '/claim-vouchers/items',
    method: 'get'
  })
}

export function addClaimVoucher(data) {
  return request({
    url: '/claim-vouchers',
    method: 'post',
    data
  })
}

export function getClaimVoucher(id) {
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

export function updateClaimVoucher(data) {
  return request({
    url: '/claim-vouchers',
    method: 'put',
    data
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
