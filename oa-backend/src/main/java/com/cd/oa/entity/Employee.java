package com.cd.oa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Employee {

    private String id;

    @JsonIgnore
    private String password;

    private String name;

    private String departmentId;

    private String post;

    private Department department;

}
