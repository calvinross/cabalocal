package com.ross53.cobar.exception;

import com.ross53.cobar.enums.ResultEnum;

public class IpValidateFailedException extends RuntimeException{

    private Integer code;

    public IpValidateFailedException(Integer code, String message){
        super(message);
        this.code =  code;
    }

    public IpValidateFailedException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
