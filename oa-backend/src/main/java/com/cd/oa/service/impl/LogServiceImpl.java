package com.cd.oa.service.impl;

import com.cd.oa.entity.Log;
import com.cd.oa.mapper.LogMapper;
import com.cd.oa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void add(Log log) {
        log.setOperationTime(new Date());
        logMapper.insert(log);
    }

    @Override
    public void remove(Integer id) {
        logMapper.deleteById(id);
    }

    @Override
    public Log getById(Integer id) {
        return logMapper.selectById(id);
    }

    @Override
    public List<Log> getAll() {
        return logMapper.selectAll();
    }

}
