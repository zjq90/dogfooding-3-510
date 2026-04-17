package com.oa.backend.dto;

import com.oa.backend.entity.ClaimVoucher;
import com.oa.backend.entity.ClaimVoucherItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ClaimVoucherDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ClaimVoucher claimVoucher;
    private List<ClaimVoucherItem> items;
}
