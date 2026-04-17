package com.oa.backend.mapper;

import com.oa.backend.entity.OperationLog;
import java.util.List;

public interface OperationLogMapper {

    int insert(OperationLog log);

    List<OperationLog> selectAll();
}
