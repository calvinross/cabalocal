package com.ross53.cobar.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.ross53.cobar.EntityWrapper.CompleteItemWrapper;
import com.ross53.cobar.EntityWrapper.InfoCodeWrapper;
import com.ross53.cobar.controller.OrderInfoController;
import com.ross53.cobar.domain.CompleteItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

public class BuildRequestWithValidateHeaderUtil {


    static Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    public static HttpEntity<String> getItemCompleteUpdateRestTemplate(CompleteItem completeItem){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));

        //get timestamp;
        long unixTime = System.currentTimeMillis() / 1000L;
        headers.add("timestamp", Long.toString(unixTime));

        //
        ObjectMapper m = new ObjectMapper();
        m.registerModule(new Hibernate5Module());
        String strComplete = "";
        try {
            CompleteItemWrapper w = new CompleteItemWrapper();
            w.setProductupdate(completeItem);
            strComplete = m.writeValueAsString(w);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

        //get signature
        String target = strComplete + ";" + unixTime;
        String key = "1234567890123456"; // 128 bit key
        String initVector = "1234567890123456"; // 16 bytes IV
        String base64 = ValidateUtil.encryptAES(key, initVector, target);
        String completeHash = "";
        completeHash = ValidateUtil.generateSignature(base64);
        headers.add("signature", completeHash);

        logger.info("complete update = " + target + " signature :" + completeHash + " timestamp : " + unixTime);

        HttpEntity<String> request = new HttpEntity<String>(strComplete, headers);

        return request;
    }

    public static HttpEntity<String> getinfoCodeRestTemplate(InfoCodeWrapper infoCodeWrapper){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));

        //get timestamp;
        long unixTime = System.currentTimeMillis() / 1000L;
        headers.add("timestamp", Long.toString(unixTime));

        //
        ObjectMapper m = new ObjectMapper();
        m.registerModule(new Hibernate5Module());
        String strComplete = "";
        try {
            strComplete = m.writeValueAsString(infoCodeWrapper);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

        //get signature
        String target = strComplete + ";" + unixTime;
        String key = "1234567890123456"; // 128 bit key
        String initVector = "1234567890123456"; // 16 bytes IV
        String base64 = ValidateUtil.encryptAES(key, initVector, target);
        String completeHash = "";
        completeHash = ValidateUtil.generateSignature(base64);
        headers.add("signature", completeHash);

        logger.info("complete update = " + target + " signature :" + completeHash + " timestamp : " + unixTime);

        HttpEntity<String> request = new HttpEntity<String>(strComplete, headers);

        return request;
    }
}
