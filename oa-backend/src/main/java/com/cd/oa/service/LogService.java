package com.cd.oa.service;

import com.cd.oa.entity.Log;

import java.util.List;

public interface LogService {

    void add(Log log);

    void remove(Integer id);

    Log getById(Integer id);

    List<Log> getAll();

}
