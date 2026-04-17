package com.oa.backend.mapper;

import com.oa.backend.entity.ClaimVoucher;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ClaimVoucherMapper {

    int insert(ClaimVoucher claimVoucher);

    int update(ClaimVoucher claimVoucher);

    int updateStatus(@Param("id") Integer id, @Param("status") String status, @Param("nextDealId") String nextDealId);

    ClaimVoucher selectById(Integer id);

    List<ClaimVoucher> selectByCreateId(String createId);

    List<ClaimVoucher> selectByNextDealId(String nextDealId);
}
