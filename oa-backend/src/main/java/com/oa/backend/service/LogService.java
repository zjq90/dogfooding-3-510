package com.oa.backend.service;

import com.oa.backend.entity.Log;

import java.util.List;

public interface LogService {

    void save(Log log);

    void removeById(Integer id);

    List<Log> listByEmployeeId(String employeeId);
}
