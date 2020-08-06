package com.fh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AddRess {

    private Integer id;

    private Integer vipId;

    private String name;

    private String detailAdd;

    private String areaIds;

    private Integer status;

    private String iphone;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh-mm-ss")
    private Date createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }
}
