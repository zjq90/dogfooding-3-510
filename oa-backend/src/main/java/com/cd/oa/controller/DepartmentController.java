package com.cd.oa.controller;

import com.cd.oa.common.Result;
import com.cd.oa.entity.Department;
import com.cd.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Result<List<Department>> list() {
        return Result.success(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public Result<Department> getById(@PathVariable String id) {
        return Result.success(departmentService.getById(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Department department) {
        departmentService.add(department);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> edit(@PathVariable String id, @RequestBody Department department) {
        department.setId(id);
        departmentService.edit(department);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable String id) {
        departmentService.remove(id);
        return Result.success();
    }

}
