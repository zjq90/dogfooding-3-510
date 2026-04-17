package com.cd.oa.dto;

import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.ClaimVoucherItem;
import lombok.Data;

import java.util.List;

@Data
public class ClaimVoucherDTO {

    private ClaimVoucher claimVoucher;

    private List<ClaimVoucherItem> items;

}
