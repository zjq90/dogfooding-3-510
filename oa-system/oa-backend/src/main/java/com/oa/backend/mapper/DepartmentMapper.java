package com.oa.backend.mapper;

import com.oa.backend.entity.Department;
import java.util.List;

public interface DepartmentMapper {

    int insert(Department department);

    int update(Department department);

    int deleteById(String id);

    Department selectById(String id);

    List<Department> selectAll();
}
