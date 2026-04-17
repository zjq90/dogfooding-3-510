package com.oa.backend.service.impl;

import com.oa.backend.entity.OperationLog;
import com.oa.backend.mapper.OperationLogMapper;
import com.oa.backend.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void addLog(String employeeId, String operation) {
        OperationLog log = new OperationLog();
        log.setEmployeeId(employeeId);
        log.setOperationTime(new Date());
        log.setOperation(operation);
        operationLogMapper.insert(log);
    }

    @Override
    public List<OperationLog> getAll() {
        return operationLogMapper.selectAll();
    }
}
