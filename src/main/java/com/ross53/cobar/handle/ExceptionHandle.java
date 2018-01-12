package com.ross53.cobar.handle;

import com.ross53.cobar.domain.Result;
import com.ross53.cobar.exception.IpValidateFailedException;
import com.ross53.cobar.exception.SignatureValidateFailedException;
import com.ross53.cobar.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandle{

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof SignatureValidateFailedException){
            SignatureValidateFailedException signatureValidateFailedException = (SignatureValidateFailedException) e;
            logger.error("businessError:{}",signatureValidateFailedException);
            return ResultUtil.failled(signatureValidateFailedException.getCode(),signatureValidateFailedException.getMessage());
        }
        if(e instanceof IpValidateFailedException){
            IpValidateFailedException ipValidateFailedException = (IpValidateFailedException) e;
            logger.error("businessError:{}",ipValidateFailedException);
            return ResultUtil.failled(ipValidateFailedException.getCode(),ipValidateFailedException.getMessage());
        }
        logger.error("systemError:{}",e.getStackTrace());
        return ResultUtil.failled(-1, e.getMessage());
    }
}
