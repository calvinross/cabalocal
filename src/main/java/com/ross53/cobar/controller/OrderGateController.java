package com.ross53.cobar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ross53.cobar.EntityWrapper.OrderGateWrapper;
import com.ross53.cobar.domain.OrderGate;
import com.ross53.cobar.domain.OrderInfo;
import com.ross53.cobar.domain.Result;
import com.ross53.cobar.exception.SignatureValidateFailedException;
import com.ross53.cobar.service.OrderGateService;
import com.ross53.cobar.utils.ResultUtil;
import com.ross53.cobar.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import java.sql.Timestamp;


@RestController
@RequestMapping("/OrderGate")
public class OrderGateController {


    @Autowired
    private OrderGateService orderGateService;

    /**
     * for cloud to close order gate
     * update close flag in local
     *
     *
     * {
     "gateStatus":1,
     "operatorName": "calvin"
     }
     * */
    @PostMapping(value="/updateGate")
    public Result<OrderGate> postOrderGate(@RequestBody final String gateFlag,
                                        @RequestHeader(value = "signature") String signature,
                                        @RequestHeader(value="timestamp") String timestamp) {

        try {
            if (!ValidateUtil.validateSignature(signature, gateFlag.toString() + ";" + timestamp.toString())) {
                throw new SignatureValidateFailedException(100, "signature validated failed");
            } else {
                ObjectMapper m = new ObjectMapper();
                OrderGateWrapper orderGateWrapper = m.readValue(gateFlag, OrderGateWrapper.class);

                OrderGate orderGate = orderGateService.getOrderGate();
                orderGate.setGateStatus(orderGateWrapper.getGateStatus());
                orderGate.setOperatorName(orderGateWrapper.getOperatorName());
                orderGate.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

                return ResultUtil.successful(orderGateService.saveOrderGate(orderGate));
            }

        } catch (Exception e) {

            return ResultUtil.failled(-1, "save ordergate failed : " + e.getMessage());
        }

    }
    /**
     * for local to get  order gate closed
     *  1 means close, 0 means open
     * */
    @GetMapping(value = "/getOrderGateStatus")
    public Result<OrderGate> getOrderGateStatus(){

        return ResultUtil.successful(orderGateService.getOrderGate());

    }
}
