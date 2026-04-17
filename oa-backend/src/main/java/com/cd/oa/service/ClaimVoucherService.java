package com.cd.oa.service;

import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.ClaimVoucherItem;
import com.cd.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherService {

    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    ClaimVoucher getById(Integer id);

    List<ClaimVoucherItem> getItemsByClaimVoucherId(Integer claimVoucherId);

    List<DealRecord> getRecordsByClaimVoucherId(Integer claimVoucherId);

    List<ClaimVoucher> getForSelf(String employeeId);

    List<ClaimVoucher> getForDeal(String employeeId);

    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    void submit(Integer id);

    void deal(DealRecord dealRecord);

}
