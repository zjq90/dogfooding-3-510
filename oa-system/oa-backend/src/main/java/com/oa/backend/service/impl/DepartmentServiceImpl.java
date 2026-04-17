package com.oa.backend.service.impl;

import com.oa.backend.entity.Department;
import com.oa.backend.mapper.DepartmentMapper;
import com.oa.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    @Transactional
    public void add(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    @Transactional
    public void update(Department department) {
        departmentMapper.update(department);
    }

    @Override
    @Transactional
    public void delete(String id) {
        departmentMapper.deleteById(id);
    }

    @Override
    public Department getById(String id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentMapper.selectAll();
    }
}
