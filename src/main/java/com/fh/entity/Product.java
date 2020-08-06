package com.fh.entity;

public class Product {

    private Integer id;

    private String productname;

    private String productdesc;

    private String brandid;

    private String typeid;

    private String sellcount;

    private Double price;

    private Integer productcount;

    private boolean issell;

    private boolean issku;

    private String attrList;

    private String attrlistjsonStr;

    private Integer inventory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getSellcount() {
        return sellcount;
    }

    public void setSellcount(String sellcount) {
        this.sellcount = sellcount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProductcount() {
        return productcount;
    }

    public void setProductcount(Integer productcount) {
        this.productcount = productcount;
    }

    public boolean isIssell() {
        return issell;
    }

    public void setIssell(boolean issell) {
        this.issell = issell;
    }

    public boolean isIssku() {
        return issku;
    }

    public void setIssku(boolean issku) {
        this.issku = issku;
    }

    public String getAttrList() {
        return attrList;
    }

    public void setAttrList(String attrList) {
        this.attrList = attrList;
    }

    public String getAttrlistjsonStr() {
        return attrlistjsonStr;
    }

    public void setAttrlistjsonStr(String attrlistjsonStr) {
        this.attrlistjsonStr = attrlistjsonStr;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}
