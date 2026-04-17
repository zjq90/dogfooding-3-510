package com.cd.oa.mapper;

import com.cd.oa.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    int insert(Employee employee);

    int update(Employee employee);

    int deleteById(String id);

    Employee selectById(String id);

    List<Employee> selectAll();

    List<Employee> selectByDepartmentAndPost(@Param("departmentId") String departmentId, @Param("post") String post);

}
