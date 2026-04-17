package com.oa.backend.service;

import com.oa.backend.entity.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);

    void update(Employee employee);

    void removeById(String id);

    Employee getById(String id);

    Employee getByIdWithDepartment(String id);

    List<Employee> listAll();

    List<Employee> listByDepartmentAndPost(String departmentId, String post);
}
