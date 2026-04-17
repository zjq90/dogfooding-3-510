package com.oa.backend.enums;

public enum ExpenseTypeEnum {
    TRAFFIC("交通"),
    ACCOMMODATION("住宿"),
    MEAL("饮食"),
    OFFICE("办公");

    private final String desc;

    ExpenseTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
