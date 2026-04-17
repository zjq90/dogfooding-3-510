package com.cd.oa.controller;

import com.cd.oa.common.Result;
import com.cd.oa.entity.Employee;
import com.cd.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Result<List<Employee>> list() {
        return Result.success(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable String id) {
        return Result.success(employeeService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Employee employee) {
        employeeService.add(employee);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> edit(@PathVariable String id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.edit(employee);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable String id) {
        employeeService.remove(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<List<Employee>> search(
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) String post) {
        return Result.success(employeeService.getByDepartmentAndPost(departmentId, post));
    }

}
