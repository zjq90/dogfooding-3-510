package com.oa.backend.controller;

import com.oa.backend.dto.ClaimVoucherRequest;
import com.oa.backend.dto.DealRequest;
import com.oa.backend.entity.*;
import com.oa.backend.service.ClaimVoucherService;
import com.oa.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claim-vouchers")
public class ClaimVoucherController {

    @Autowired
    private ClaimVoucherService claimVoucherService;

    @GetMapping
    public Result<List<ClaimVoucher>> list(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(claimVoucherService.getForSelf(employee.getId()));
    }

    @GetMapping("/deal")
    public Result<List<ClaimVoucher>> dealList(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(claimVoucherService.getForDeal(employee.getId()));
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> getById(@PathVariable Integer id) {
        ClaimVoucher claimVoucher = claimVoucherService.getById(id);
        if (claimVoucher == null) {
            return Result.error("报销单不存在");
        }

        List<ClaimVoucherItem> items = claimVoucherService.getItems(id);
        List<DealRecord> records = claimVoucherService.getRecords(id);

        Map<String, Object> result = new HashMap<>();
        result.put("claimVoucher", claimVoucher);
        result.put("items", items);
        result.put("records", records);

        return Result.success(result);
    }

    @PostMapping
    public Result<Void> add(@RequestBody ClaimVoucherRequest request, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }

        request.getClaimVoucher().setCreateId(employee.getId());
        claimVoucherService.save(request.getClaimVoucher(), request.getItems());
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody ClaimVoucherRequest request, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }

        request.getClaimVoucher().setId(id);
        request.getClaimVoucher().setCreateId(employee.getId());
        claimVoucherService.update(request.getClaimVoucher(), request.getItems());
        return Result.success();
    }

    @PostMapping("/{id}/submit")
    public Result<Void> submit(@PathVariable Integer id) {
        claimVoucherService.submit(id);
        return Result.success();
    }

    @PostMapping("/deal")
    public Result<Void> deal(@RequestBody DealRequest request, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return Result.error(401, "未登录");
        }

        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(request.getClaimVoucherId());
        dealRecord.setDealId(employee.getId());
        dealRecord.setDealType(request.getDealType());
        dealRecord.setDealResult(request.getDealResult());
        dealRecord.setComment(request.getComment());

        claimVoucherService.deal(dealRecord);
        return Result.success();
    }
}
