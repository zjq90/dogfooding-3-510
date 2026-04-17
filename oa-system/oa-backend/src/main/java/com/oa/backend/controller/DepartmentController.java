package com.oa.backend.controller;

import com.oa.backend.entity.Department;
import com.oa.backend.service.DepartmentService;
import com.oa.backend.utils.Result;
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
        Department department = departmentService.getById(id);
        if (department == null) {
            return Result.error("部门不存在");
        }
        return Result.success(department);
    }

    @PostMapping
    public Result<Void> add(@RequestBody Department department) {
        Department exist = departmentService.getById(department.getId());
        if (exist != null) {
            return Result.error("部门编号已存在");
        }
        departmentService.add(department);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable String id, @RequestBody Department department) {
        department.setId(id);
        departmentService.update(department);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        departmentService.delete(id);
        return Result.success();
    }
}
