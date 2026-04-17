package com.oa.backend.mapper;

import com.oa.backend.entity.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmployeeMapper {

    int insert(Employee employee);

    int update(Employee employee);

    int deleteById(String id);

    Employee selectById(String id);

    List<Employee> selectAll();

    List<Employee> selectByDepartmentAndPost(@Param("departmentId") String departmentId, @Param("post") String post);

    Employee selectByIdAndPassword(@Param("id") String id, @Param("password") String password);
}
