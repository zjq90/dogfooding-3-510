package com.oa.backend.enums;

public enum DealTypeEnum {
    CREATE("创建"),
    SUBMIT("提交"),
    UPDATE("修改"),
    REJECT("打回"),
    REFUSE("拒绝"),
    PASS("通过"),
    PAY("打款");

    private final String desc;

    DealTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
