package com.cd.oa.controller;

import com.cd.oa.common.Result;
import com.cd.oa.entity.Log;
import com.cd.oa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public Result<List<Log>> list() {
        return Result.success(logService.getAll());
    }

    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        logService.remove(id);
        return Result.success();
    }

}
