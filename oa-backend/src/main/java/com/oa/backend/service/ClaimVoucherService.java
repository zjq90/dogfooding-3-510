package com.oa.backend.service;

import com.oa.backend.dto.ClaimVoucherDTO;
import com.oa.backend.entity.ClaimVoucher;
import com.oa.backend.entity.ClaimVoucherItem;
import com.oa.backend.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherService {

    void save(ClaimVoucherDTO dto);

    ClaimVoucher getById(Integer id);

    List<ClaimVoucherItem> listItemsByClaimVoucherId(Integer claimVoucherId);

    List<DealRecord> listRecordsByClaimVoucherId(Integer claimVoucherId);

    List<ClaimVoucher> listByCreateId(String createId);

    List<ClaimVoucher> listByNextDealId(String nextDealId);

    void update(ClaimVoucherDTO dto);

    void submit(Integer id);

    void deal(DealRecord dealRecord);
}
