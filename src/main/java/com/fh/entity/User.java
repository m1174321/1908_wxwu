package com.fh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {

    private Integer id;

    private String phone;

    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
