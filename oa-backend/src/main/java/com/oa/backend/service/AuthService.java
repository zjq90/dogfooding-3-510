package com.oa.backend.service;

import com.oa.backend.entity.Employee;

public interface AuthService {

    Employee login(String id, String password);

    void changePassword(String employeeId, String newPassword);
}
