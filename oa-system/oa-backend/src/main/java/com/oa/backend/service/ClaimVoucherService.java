package com.oa.backend.service;

import com.oa.backend.entity.ClaimVoucher;
import com.oa.backend.entity.ClaimVoucherItem;
import com.oa.backend.entity.DealRecord;
import java.util.List;

public interface ClaimVoucherService {

    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    ClaimVoucher getById(Integer id);

    List<ClaimVoucherItem> getItems(Integer claimVoucherId);

    List<DealRecord> getRecords(Integer claimVoucherId);

    List<ClaimVoucher> getForSelf(String employeeId);

    List<ClaimVoucher> getForDeal(String employeeId);

    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    void submit(Integer id);

    void deal(DealRecord dealRecord);
}
