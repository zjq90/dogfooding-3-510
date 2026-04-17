package com.oa.backend.service.impl;

import com.oa.backend.entity.*;
import com.oa.backend.enums.ClaimVoucherStatusEnum;
import com.oa.backend.enums.DealTypeEnum;
import com.oa.backend.enums.PostEnum;
import com.oa.backend.mapper.*;
import com.oa.backend.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ClaimVoucherServiceImpl implements ClaimVoucherService {

    @Autowired
    private ClaimVoucherMapper claimVoucherMapper;
    @Autowired
    private ClaimVoucherItemMapper claimVoucherItemMapper;
    @Autowired
    private DealRecordMapper dealRecordMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    private static final Double CHECK_LIMIT = 5000.00;

    @Override
    @Transactional
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setStatus(ClaimVoucherStatusEnum.CREATED.getDesc());
        claimVoucher.setNextDealId(claimVoucher.getCreateId());
        claimVoucherMapper.insert(claimVoucher);

        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemMapper.insert(item);
        }

        DealRecord record = new DealRecord();
        record.setClaimVoucherId(claimVoucher.getId());
        record.setDealId(claimVoucher.getCreateId());
        record.setDealTime(new Date());
        record.setDealType(DealTypeEnum.CREATE.getDesc());
        record.setDealResult(ClaimVoucherStatusEnum.CREATED.getDesc());
        record.setComment("无");
        dealRecordMapper.insert(record);
    }

    @Override
    public ClaimVoucher getById(Integer id) {
        return claimVoucherMapper.selectById(id);
    }

    @Override
    public List<ClaimVoucherItem> getItems(Integer claimVoucherId) {
        return claimVoucherItemMapper.selectByClaimVoucherId(claimVoucherId);
    }

    @Override
    public List<DealRecord> getRecords(Integer claimVoucherId) {
        return dealRecordMapper.selectByClaimVoucherId(claimVoucherId);
    }

    @Override
    public List<ClaimVoucher> getForSelf(String employeeId) {
        return claimVoucherMapper.selectByCreateId(employeeId);
    }

    @Override
    public List<ClaimVoucher> getForDeal(String employeeId) {
        return claimVoucherMapper.selectByNextDealId(employeeId);
    }

    @Override
    @Transactional
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucherMapper.update(claimVoucher);
        claimVoucherItemMapper.deleteByClaimVoucherId(claimVoucher.getId());
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemMapper.insert(item);
        }
    }

    @Override
    @Transactional
    public void submit(Integer id) {
        ClaimVoucher claimVoucher = claimVoucherMapper.selectById(id);
        Employee employee = employeeMapper.selectById(claimVoucher.getCreateId());

        claimVoucher.setStatus(ClaimVoucherStatusEnum.SUBMITTED.getDesc());

        String nextDealId = getNextDealer(employee);
        claimVoucher.setNextDealId(nextDealId);

        claimVoucherMapper.updateStatus(id, claimVoucher.getStatus(), nextDealId);

        DealRecord record = new DealRecord();
        record.setClaimVoucherId(id);
        record.setDealId(claimVoucher.getCreateId());
        record.setDealTime(new Date());
        record.setDealType(DealTypeEnum.SUBMIT.getDesc());
        record.setDealResult(ClaimVoucherStatusEnum.SUBMITTED.getDesc());
        record.setComment("无");
        dealRecordMapper.insert(record);
    }

    @Override
    @Transactional
    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherMapper.selectById(dealRecord.getClaimVoucherId());
        Employee employee = employeeMapper.selectById(dealRecord.getDealId());

        dealRecord.setDealTime(new Date());
        dealRecord.setDealResult(dealRecord.getDealType());
        dealRecordMapper.insert(dealRecord);

        if (DealTypeEnum.PASS.getDesc().equals(dealRecord.getDealType())) {
            if (claimVoucher.getTotalAmount() >= CHECK_LIMIT || employee.getPost().equals(PostEnum.GENERAL_MANAGER.getDesc())) {
                claimVoucher.setStatus(ClaimVoucherStatusEnum.APPROVED.getDesc());
                claimVoucher.setNextDealId(claimVoucher.getCreateId());

                List<Employee> cashiers = employeeMapper.selectByDepartmentAndPost(null, PostEnum.FINANCE.getDesc());
                if (!cashiers.isEmpty()) {
                    claimVoucher.setNextDealId(cashiers.get(0).getId());
                }
            } else {
                claimVoucher.setStatus(ClaimVoucherStatusEnum.RECHECK.getDesc());
                List<Employee> managers = employeeMapper.selectByDepartmentAndPost(null, PostEnum.GENERAL_MANAGER.getDesc());
                if (!managers.isEmpty()) {
                    claimVoucher.setNextDealId(managers.get(0).getId());
                }
            }
        } else if (DealTypeEnum.REJECT.getDesc().equals(dealRecord.getDealType())) {
            claimVoucher.setStatus(ClaimVoucherStatusEnum.REJECTED.getDesc());
            claimVoucher.setNextDealId(claimVoucher.getCreateId());
        } else if (DealTypeEnum.REFUSE.getDesc().equals(dealRecord.getDealType())) {
            claimVoucher.setStatus(ClaimVoucherStatusEnum.TERMINATED.getDesc());
            claimVoucher.setNextDealId(null);
        } else if (DealTypeEnum.PAY.getDesc().equals(dealRecord.getDealType())) {
            claimVoucher.setStatus(ClaimVoucherStatusEnum.PAID.getDesc());
            claimVoucher.setNextDealId(null);
        }

        claimVoucherMapper.updateStatus(claimVoucher.getId(), claimVoucher.getStatus(), claimVoucher.getNextDealId());
    }

    private String getNextDealer(Employee employee) {
        String nextDealId = null;

        if (employee.getPost().equals(PostEnum.STAFF.getDesc())) {
            List<Employee> managers = employeeMapper.selectByDepartmentAndPost(employee.getDepartmentId(), PostEnum.DEPARTMENT_MANAGER.getDesc());
            if (!managers.isEmpty()) {
                nextDealId = managers.get(0).getId();
            }
        } else if (employee.getPost().equals(PostEnum.DEPARTMENT_MANAGER.getDesc())) {
            List<Employee> managers = employeeMapper.selectByDepartmentAndPost(null, PostEnum.GENERAL_MANAGER.getDesc());
            if (!managers.isEmpty()) {
                nextDealId = managers.get(0).getId();
            }
        } else if (employee.getPost().equals(PostEnum.GENERAL_MANAGER.getDesc())) {
            List<Employee> cashiers = employeeMapper.selectByDepartmentAndPost(null, PostEnum.FINANCE.getDesc());
            if (!cashiers.isEmpty()) {
                nextDealId = cashiers.get(0).getId();
            }
        } else if (employee.getPost().equals(PostEnum.FINANCE.getDesc())) {
            nextDealId = employee.getId();
        }

        return nextDealId;
    }
}
