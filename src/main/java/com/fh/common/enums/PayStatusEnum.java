package com.fh.common.enums;

public enum PayStatusEnum {
    PAY_STATUS_INIT(0,"未支付"),
    PAY_STATUS_ING(1,"支付中"),
    PAY_STATUS_SUCCESS(2,"支付成功"),
    PAY_STATUS_Error(3,"支付异常")
    ;
    private Integer status;
    private String message;
    private PayStatusEnum(Integer status,String message){
        this.message=message;
        this.status=status;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
