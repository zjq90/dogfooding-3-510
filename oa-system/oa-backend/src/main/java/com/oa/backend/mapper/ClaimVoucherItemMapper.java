package com.oa.backend.mapper;

import com.oa.backend.entity.ClaimVoucherItem;
import java.util.List;

public interface ClaimVoucherItemMapper {

    int insert(ClaimVoucherItem item);

    int update(ClaimVoucherItem item);

    int deleteById(Integer id);

    int deleteByClaimVoucherId(Integer claimVoucherId);

    ClaimVoucherItem selectById(Integer id);

    List<ClaimVoucherItem> selectByClaimVoucherId(Integer claimVoucherId);
}
