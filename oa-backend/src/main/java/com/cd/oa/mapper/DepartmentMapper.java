package com.cd.oa.mapper;

import com.cd.oa.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    int insert(Department department);

    int update(Department department);

    int deleteById(String id);

    Department selectById(String id);

    List<Department> selectAll();

}
