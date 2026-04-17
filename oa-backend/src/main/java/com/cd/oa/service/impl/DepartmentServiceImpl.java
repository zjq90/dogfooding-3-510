package com.cd.oa.service.impl;

import com.cd.oa.entity.Department;
import com.cd.oa.mapper.DepartmentMapper;
import com.cd.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void add(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public void edit(Department department) {
        departmentMapper.update(department);
    }

    @Override
    public void remove(String id) {
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
