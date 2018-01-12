package com.ross53.cobar.exception;


import com.ross53.cobar.enums.ResultEnum;

public class SignatureValidateFailedException extends RuntimeException{

    private Integer code;

    public  SignatureValidateFailedException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public  SignatureValidateFailedException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }
}
