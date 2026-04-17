package com.oa.backend.service;

import com.oa.backend.entity.Department;
import java.util.List;

public interface DepartmentService {

    void add(Department department);

    void update(Department department);

    void delete(String id);

    Department getById(String id);

    List<Department> getAll();
}
