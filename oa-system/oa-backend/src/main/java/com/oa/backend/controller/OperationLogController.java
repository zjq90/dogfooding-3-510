package com.oa.backend.controller;

import com.oa.backend.entity.OperationLog;
import com.oa.backend.service.OperationLogService;
import com.oa.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping
    public Result<List<OperationLog>> list() {
        return Result.success(operationLogService.getAll());
    }
}
