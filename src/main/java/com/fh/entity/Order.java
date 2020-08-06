package com.fh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private Integer id;
    private Integer addressId;
    private Integer payType;
    private Integer proTypeCount;
    private BigDecimal totalmoney;
    private Integer payStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
    private Integer vipId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getProTypeCount() {
        return proTypeCount;
    }

    public void setProTypeCount(Integer proTypeCount) {
        this.proTypeCount = proTypeCount;
    }

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }
}
