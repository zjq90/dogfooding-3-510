package com.cd.oa.mapper;

import com.cd.oa.entity.ClaimVoucher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClaimVoucherMapper {

    int insert(ClaimVoucher claimVoucher);

    int update(ClaimVoucher claimVoucher);

    int deleteById(Integer id);

    ClaimVoucher selectById(Integer id);

    List<ClaimVoucher> selectByCreateId(String createId);

    List<ClaimVoucher> selectByNextDealId(String nextDealId);

}
