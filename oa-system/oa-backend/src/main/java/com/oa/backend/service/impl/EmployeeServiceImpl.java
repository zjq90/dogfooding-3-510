package com.oa.backend.service.impl;

import com.oa.backend.entity.Employee;
import com.oa.backend.mapper.EmployeeMapper;
import com.oa.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public void add(Employee employee) {
        employee.setPassword("123456");
        employeeMapper.insert(employee);
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    @Transactional
    public void delete(String id) {
        employeeMapper.deleteById(id);
    }

    @Override
    public Employee getById(String id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public List<Employee> getByDepartmentAndPost(String departmentId, String post) {
        return employeeMapper.selectByDepartmentAndPost(departmentId, post);
    }

    @Override
    public Employee login(String id, String password) {
        return employeeMapper.selectByIdAndPassword(id, password);
    }
}
