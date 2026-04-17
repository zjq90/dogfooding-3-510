package com.cd.oa.mapper;

import com.cd.oa.entity.DealRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DealRecordMapper {

    int insert(DealRecord dealRecord);

    DealRecord selectById(Integer id);

    List<DealRecord> selectByClaimVoucherId(Integer claimVoucherId);

}
