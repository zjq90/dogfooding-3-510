package com.cd.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DealRecord {

    private Integer id;

    private Integer claimVoucherId;

    private String dealId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dealTime;

    private String dealType;

    private String dealResult;

    private String comment;

    private Employee dealer;

}
