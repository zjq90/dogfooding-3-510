package com.oa.backend.controller;

import com.oa.backend.entity.Employee;
import com.oa.backend.service.DepartmentService;
import com.oa.backend.service.EmployeeService;
import com.oa.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Result<List<Employee>> list() {
        return Result.success(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable String id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return Result.error("员工不存在");
        }
        return Result.success(employee);
    }

    @PostMapping
    public Result<Void> add(@RequestBody Employee employee) {
        Employee exist = employeeService.getById(employee.getId());
        if (exist != null) {
            return Result.error("员工编号已存在");
        }
        employeeService.add(employee);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable String id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.update(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        employeeService.delete(id);
        return Result.success();
    }

    @GetMapping("/by-department-post")
    public Result<List<Employee>> getByDepartmentAndPost(
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) String post) {
        return Result.success(employeeService.getByDepartmentAndPost(departmentId, post));
    }
}
