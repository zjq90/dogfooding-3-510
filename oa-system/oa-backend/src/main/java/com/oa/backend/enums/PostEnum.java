package com.oa.backend.enums;

public enum PostEnum {
    STAFF("员工"),
    DEPARTMENT_MANAGER("部门经理"),
    GENERAL_MANAGER("总经理"),
    FINANCE("财务");

    private final String desc;

    PostEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
