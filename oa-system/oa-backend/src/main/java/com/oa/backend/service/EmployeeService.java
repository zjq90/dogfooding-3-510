package com.oa.backend.service;

import com.oa.backend.entity.Employee;
import java.util.List;

public interface EmployeeService {

    void add(Employee employee);

    void update(Employee employee);

    void delete(String id);

    Employee getById(String id);

    List<Employee> getAll();

    List<Employee> getByDepartmentAndPost(String departmentId, String post);

    Employee login(String id, String password);
}
