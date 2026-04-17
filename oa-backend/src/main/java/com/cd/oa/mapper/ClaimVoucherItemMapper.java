package com.cd.oa.mapper;

import com.cd.oa.entity.ClaimVoucherItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimVoucherItemMapper {

    int insert(ClaimVoucherItem item);

    int update(ClaimVoucherItem item);

    int deleteById(Integer id);

    int deleteByClaimVoucherId(Integer claimVoucherId);

    ClaimVoucherItem selectById(Integer id);

    List<ClaimVoucherItem> selectByClaimVoucherId(Integer claimVoucherId);

}
