package com.cd.oa.controller;

import com.cd.oa.common.Result;
import com.cd.oa.dto.ClaimVoucherDTO;
import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.ClaimVoucherItem;
import com.cd.oa.entity.DealRecord;
import com.cd.oa.entity.Employee;
import com.cd.oa.service.ClaimVoucherService;
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

    @PostMapping
    public Result<Void> save(@RequestBody ClaimVoucherDTO dto, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        dto.getClaimVoucher().setCreateId(employee.getId());
        claimVoucherService.save(dto.getClaimVoucher(), dto.getItems());
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody ClaimVoucherDTO dto) {
        dto.getClaimVoucher().setId(id);
        claimVoucherService.update(dto.getClaimVoucher(), dto.getItems());
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> getDetail(@PathVariable Integer id) {
        ClaimVoucher claimVoucher = claimVoucherService.getById(id);
        List<ClaimVoucherItem> items = claimVoucherService.getItemsByClaimVoucherId(id);
        List<DealRecord> records = claimVoucherService.getRecordsByClaimVoucherId(id);

        Map<String, Object> result = new HashMap<>();
        result.put("claimVoucher", claimVoucher);
        result.put("items", items);
        result.put("records", records);
        return Result.success(result);
    }

    @GetMapping("/self")
    public Result<List<ClaimVoucher>> getSelfList(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        return Result.success(claimVoucherService.getForSelf(employee.getId()));
    }

    @GetMapping("/deal")
    public Result<List<ClaimVoucher>> getDealList(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        return Result.success(claimVoucherService.getForDeal(employee.getId()));
    }

    @PostMapping("/{id}/submit")
    public Result<Void> submit(@PathVariable Integer id) {
        claimVoucherService.submit(id);
        return Result.success();
    }

    @PostMapping("/deal")
    public Result<Void> deal(@RequestBody DealRecord dealRecord, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        dealRecord.setDealId(employee.getId());
        claimVoucherService.deal(dealRecord);
        return Result.success();
    }

}
