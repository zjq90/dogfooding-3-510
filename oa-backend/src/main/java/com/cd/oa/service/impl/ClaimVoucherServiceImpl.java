package com.cd.oa.service.impl;

import com.cd.oa.common.Constant;
import com.cd.oa.entity.ClaimVoucher;
import com.cd.oa.entity.ClaimVoucherItem;
import com.cd.oa.entity.DealRecord;
import com.cd.oa.entity.Employee;
import com.cd.oa.mapper.ClaimVoucherItemMapper;
import com.cd.oa.mapper.ClaimVoucherMapper;
import com.cd.oa.mapper.DealRecordMapper;
import com.cd.oa.mapper.EmployeeMapper;
import com.cd.oa.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ClaimVoucherServiceImpl implements ClaimVoucherService {

    @Autowired
    private ClaimVoucherMapper claimVoucherMapper;

    @Autowired
    private ClaimVoucherItemMapper claimVoucherItemMapper;

    @Autowired
    private DealRecordMapper dealRecordMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealId(claimVoucher.getCreateId());
        claimVoucher.setStatus(Constant.CLAIM_VOUCHER_CREATED);
        claimVoucherMapper.insert(claimVoucher);

        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemMapper.insert(item);
        }

        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setDealId(claimVoucher.getCreateId());
        dealRecord.setDealTime(new Date());
        dealRecord.setDealType(Constant.DEAL_TYPE_CREATE);
        dealRecord.setDealResult(Constant.CLAIM_VOUCHER_CREATED);
        dealRecord.setComment("无");
        dealRecordMapper.insert(dealRecord);
    }

    @Override
    public ClaimVoucher getById(Integer id) {
        return claimVoucherMapper.selectById(id);
    }

    @Override
    public List<ClaimVoucherItem> getItemsByClaimVoucherId(Integer claimVoucherId) {
        return claimVoucherItemMapper.selectByClaimVoucherId(claimVoucherId);
    }

    @Override
    public List<DealRecord> getRecordsByClaimVoucherId(Integer claimVoucherId) {
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
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealId(claimVoucher.getCreateId());
        claimVoucher.setStatus(Constant.CLAIM_VOUCHER_CREATED);
        claimVoucherMapper.update(claimVoucher);

        claimVoucherItemMapper.deleteByClaimVoucherId(claimVoucher.getId());
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemMapper.insert(item);
        }

        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(claimVoucher.getId());
        dealRecord.setDealId(claimVoucher.getCreateId());
        dealRecord.setDealTime(new Date());
        dealRecord.setDealType(Constant.DEAL_TYPE_UPDATE);
        dealRecord.setDealResult(Constant.CLAIM_VOUCHER_CREATED);
        dealRecord.setComment("无");
        dealRecordMapper.insert(dealRecord);
    }

    @Override
    public void submit(Integer id) {
        ClaimVoucher claimVoucher = claimVoucherMapper.selectById(id);
        Employee creator = employeeMapper.selectById(claimVoucher.getCreateId());

        List<Employee> managers = employeeMapper.selectByDepartmentAndPost(
            creator.getDepartmentId(), Constant.POST_DEPARTMENT_MANAGER);

        claimVoucher.setStatus(Constant.CLAIM_VOUCHER_SUBMITTED);
        claimVoucher.setNextDealId(managers.get(0).getId());
        claimVoucherMapper.update(claimVoucher);

        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealId(creator.getId());
        dealRecord.setDealTime(new Date());
        dealRecord.setDealType(Constant.DEAL_TYPE_SUBMIT);
        dealRecord.setDealResult(Constant.CLAIM_VOUCHER_SUBMITTED);
        dealRecord.setComment("无");
        dealRecordMapper.insert(dealRecord);
    }

    @Override
    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherMapper.selectById(dealRecord.getClaimVoucherId());
        Employee dealer = employeeMapper.selectById(dealRecord.getDealId());
        dealRecord.setDealTime(new Date());

        if (Constant.DEAL_TYPE_APPROVE.equals(dealRecord.getDealType())) {
            if (claimVoucher.getTotalAmount() < Constant.CLAIM_AMOUNT_THRESHOLD
                || Constant.POST_GENERAL_MANAGER.equals(dealer.getPost())) {
                claimVoucher.setStatus(Constant.CLAIM_VOUCHER_APPROVED);
                List<Employee> cashiers = employeeMapper.selectByDepartmentAndPost(null, Constant.POST_FINANCE);
                claimVoucher.setNextDealId(cashiers.get(0).getId());
                dealRecord.setDealResult(Constant.CLAIM_VOUCHER_APPROVED);
            } else {
                claimVoucher.setStatus(Constant.CLAIM_VOUCHER_SUBMITTED);
                List<Employee> gmList = employeeMapper.selectByDepartmentAndPost(null, Constant.POST_GENERAL_MANAGER);
                claimVoucher.setNextDealId(gmList.get(0).getId());
                dealRecord.setDealResult(Constant.CLAIM_VOUCHER_SUBMITTED);
            }
        } else if (Constant.DEAL_TYPE_REJECT.equals(dealRecord.getDealType())) {
            claimVoucher.setStatus(Constant.CLAIM_VOUCHER_REJECTED);
            claimVoucher.setNextDealId(claimVoucher.getCreateId());
            dealRecord.setDealResult(Constant.CLAIM_VOUCHER_REJECTED);
        } else if (Constant.DEAL_TYPE_PAY.equals(dealRecord.getDealType())) {
            claimVoucher.setStatus(Constant.CLAIM_VOUCHER_PAID);
            claimVoucher.setNextDealId(null);
            dealRecord.setDealResult(Constant.CLAIM_VOUCHER_PAID);
        }

        claimVoucherMapper.update(claimVoucher);
        dealRecordMapper.insert(dealRecord);
    }

}
