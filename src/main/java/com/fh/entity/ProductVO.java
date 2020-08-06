package com.fh.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ProductVO {

    private Integer id;

    private String productName;

    private Double price;

    private Integer status;

    private String productImg;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private Integer ishot;

    private String areaIds;

    private String typeIds;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds;
    }
}
