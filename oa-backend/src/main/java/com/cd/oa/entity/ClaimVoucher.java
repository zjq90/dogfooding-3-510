package com.cd.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClaimVoucher {

    private Integer id;

    private String cause;

    private String createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String nextDealId;

    private Double totalAmount;

    private String status;

    private Employee creator;

    private Employee dealer;

    private List<ClaimVoucherItem> items;

}
