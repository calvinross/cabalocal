package com.ross53.cobar.enums;

public enum ResultEnum{
    UNKNOWN_ERROR(-1,"unknown error"),
    SUCCESS(0,"successful"),
    SIGNATURE_FAILED(100,"signature validated failed"),
    IP_VALIDATE_FAILED(101,"your request is not allowed"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
