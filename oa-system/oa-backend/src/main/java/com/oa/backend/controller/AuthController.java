package com.oa.backend.controller;

import com.oa.backend.dto.ChangePasswordRequest;
import com.oa.backend.dto.LoginRequest;
import com.oa.backend.entity.Employee;
import com.oa.backend.service.EmployeeService;
import com.oa.backend.service.OperationLogService;
import com.oa.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OperationLogService operationLogService;

    @PostMapping("/login")
    public Result<Employee> login(@RequestBody LoginRequest request, HttpSession session) {
        Employee employee = employeeService.login(request.getId(), request.getPassword());
        if (employee == null) {
            return Result.error("工号或密码错误");
        }
        session.setAttribute("employee", employee);
        operationLogService.addLog(employee.getId(), "login");
        return Result.success(employee);
    }

    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @GetMapping("/current")
    public Result<Employee> getCurrentUser(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(employee);
    }

    @PostMapping("/change-password")
    public Result<Void> changePassword(@RequestBody ChangePasswordRequest request, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }

        if (!employee.getPassword().equals(request.getOldPassword())) {
            return Result.error("原密码错误");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return Result.error("两次输入的新密码不一致");
        }

        employee.setPassword(request.getNewPassword());
        employeeService.update(employee);
        session.setAttribute("employee", employee);
        return Result.success();
    }

    @GetMapping("/posts")
    public Result<List<String>> getPosts() {
        return Result.success(Arrays.asList("员工", "部门经理", "总经理", "财务"));
    }

    @GetMapping("/expense-types")
    public Result<List<String>> getExpenseTypes() {
        return Result.success(Arrays.asList("交通", "住宿", "饮食", "办公"));
    }
}
