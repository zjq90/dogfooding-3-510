package com.cd.oa.controller;

import com.cd.oa.common.Result;
import com.cd.oa.entity.Employee;
import com.cd.oa.entity.Log;
import com.cd.oa.service.EmployeeService;
import com.cd.oa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LogService logService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params, HttpSession session) {
        String id = params.get("id");
        String password = params.get("password");
        Employee employee = employeeService.login(id, password);

        Log log = new Log();
        log.setEmployeeId(id);
        log.setOperation("login");
        logService.add(log);

        session.setAttribute("employee", employee);

        Map<String, Object> result = new HashMap<>();
        result.put("employee", employee);
        result.put("sessionId", session.getId());
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @GetMapping("/info")
    public Result<Employee> getInfo(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(employee);
    }

    @PostMapping("/change-password")
    public Result<Void> changePassword(@RequestBody Map<String, String> params, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        employeeService.changePassword(employee.getId(), oldPassword, newPassword);
        return Result.success();
    }

}
