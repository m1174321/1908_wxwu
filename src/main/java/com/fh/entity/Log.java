package com.fh.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Log {

    private Integer id;

    private String operator;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date logDate;

    private String requestParam;

    private Integer isStart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }
}
