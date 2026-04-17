package com.oa.backend.enums;

public enum ClaimVoucherStatusEnum {
    CREATED("新创建"),
    SUBMITTED("已提交"),
    APPROVED("已审核"),
    REJECTED("已打回"),
    TERMINATED("已终止"),
    RECHECK("待复审"),
    PAID("已打款");

    private final String desc;

    ClaimVoucherStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
