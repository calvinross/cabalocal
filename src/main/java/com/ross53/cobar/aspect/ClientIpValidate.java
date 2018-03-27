package com.ross53.cobar.aspect;

import com.ross53.cobar.exception.IpValidateFailedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * to protect api by validating client IP, only allow ip in whitelist can by pass;
 *
 * **/
@Aspect
@Component
public class ClientIpValidate {

    //should be get whitelist from DB to replace default IP;
    private final List<String> clientIP = Arrays.asList("0:0:0:0:0:0:0:1","127.0.0.1","220.248.29.226",
                                                         "115.159.76.157","202.39.151.119","101.229.71.182",
                                                         "192.168.1.1","192.168.1.2","192.168.1.3","192.168.1.4","192.168.1.5",
                                                         "192.168.1.250","58.211.230.143","116.233.58.69","47.96.136.167");

    private final static Logger logger = LoggerFactory.getLogger(ClientIpValidate.class);

    //@Before("execution(public * com.ross53.cobar.controller.PositionInfoController.*(..))")
    @Before("execution(public * com.ross53.cobar.controller.*.*())")
    public void ClientIpValidate() throws Exception {

         logger.info("ip validating....");

          HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

          String ipAddress = request.getRemoteAddr();

          Integer isValidateIp = 0;
          for(String temp : clientIP) {
              logger.info(temp);
              if (ipAddress.equals(temp)) {
                  logger.info("ipAddress is {}", ipAddress);
                  isValidateIp = 1;
                  return;
              }
          }
          if(isValidateIp !=1){
              logger.info("invalidated ip；{}",ipAddress);
              throw new IpValidateFailedException(101,"ip address is not allowed to call this api.");
          }

    }
}
