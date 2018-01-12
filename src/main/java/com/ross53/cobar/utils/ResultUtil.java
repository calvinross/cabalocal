package com.ross53.cobar.utils;

import com.ross53.cobar.domain.Result;

public class ResultUtil{
    public static Result successful(Object object){
        Result result = new Result();
        result.setErrorCode(0);
        result.setMsg("done successfully");
        result.setData(object);
        return result;
    }

    public static Result failled(Integer errorCode,String msg){
        Result result = new Result();
        result.setErrorCode(errorCode);
        result.setMsg(msg);
        return result;
    }
}
