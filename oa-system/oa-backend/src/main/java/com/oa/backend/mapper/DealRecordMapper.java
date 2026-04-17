package com.oa.backend.mapper;

import com.oa.backend.entity.DealRecord;
import java.util.List;

public interface DealRecordMapper {

    int insert(DealRecord dealRecord);

    DealRecord selectById(Integer id);

    List<DealRecord> selectByClaimVoucherId(Integer claimVoucherId);
}
