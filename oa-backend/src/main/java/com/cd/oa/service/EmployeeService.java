package com.cd.oa.service;

import com.cd.oa.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee login(String id, String password);

    void changePassword(String id, String oldPassword, String newPassword);

    void add(Employee employee);

    void edit(Employee employee);

    void remove(String id);

    Employee getById(String id);

    List<Employee> getAll();

    List<Employee> getByDepartmentAndPost(String departmentId, String post);

}
