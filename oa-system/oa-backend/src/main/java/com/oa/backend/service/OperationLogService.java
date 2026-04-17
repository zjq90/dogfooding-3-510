package com.oa.backend.service;

import com.oa.backend.entity.OperationLog;
import java.util.List;

public interface OperationLogService {

    void addLog(String employeeId, String operation);

    List<OperationLog> getAll();
}
