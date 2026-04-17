package com.cd.oa.service.impl;

import com.cd.oa.entity.Employee;
import com.cd.oa.mapper.EmployeeMapper;
import com.cd.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(String id, String password) {
        Employee employee = employeeMapper.selectById(id);
        Assert.notNull(employee, "用户不存在");
        Assert.isTrue(password.equals(employee.getPassword()), "密码错误");
        return employee;
    }

    @Override
    public void changePassword(String id, String oldPassword, String newPassword) {
        Employee employee = employeeMapper.selectById(id);
        Assert.isTrue(oldPassword.equals(employee.getPassword()), "原密码错误");
        employee.setPassword(newPassword);
        employeeMapper.update(employee);
    }

    @Override
    public void add(Employee employee) {
        employee.setPassword("123456");
        employeeMapper.insert(employee);
    }

    @Override
    public void edit(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void remove(String id) {
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

}
