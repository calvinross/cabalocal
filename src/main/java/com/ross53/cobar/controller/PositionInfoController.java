package com.ross53.cobar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ross53.cobar.EntityWrapper.PositionWrapper;
import com.ross53.cobar.domain.PositionInfo;
import com.ross53.cobar.domain.Result;
import com.ross53.cobar.exception.SignatureValidateFailedException;
import com.ross53.cobar.service.PositionInfoService;
import com.ross53.cobar.utils.ResultUtil;
import com.ross53.cobar.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positionInfo")
public class PositionInfoController {

    private final static Logger logger = LoggerFactory.getLogger(PositionInfoController.class);

    @Autowired
    PositionInfoService positionInfoService;

    @GetMapping("/list")
    public List<PositionInfo> getPositionAll(){
        return positionInfoService.getPositionAll();
    }

    @PostMapping("releaseAllPosition")
    public Result<String> releasePositionAll(){

        try{

            positionInfoService.updatePositionAll();

            return ResultUtil.successful("release position done!");

        }catch (Exception e){
            return ResultUtil.failled(-1,"release position failed!");
        }

    }

    @GetMapping("/getPosition/{orderNumber}/{itemId}")
    public Result<PositionInfo> getPositionByOrderNumberItemId(@PathVariable("orderNumber") String orderNumber,
                                                               @PathVariable("itemId") Integer itemId){

        PositionInfo psInfo = positionInfoService.getPositionByOrderNumberItemId(orderNumber,itemId);

        if(psInfo != null) {
            psInfo.setPositionStatus(1);
            return ResultUtil.successful(psInfo);
        }else {
            return ResultUtil.failled(-1,"release failed,not found this position");

        }
    }

    @PostMapping("/releasePosition/{positionType}/{positionNumber}")
    public Result<PositionInfo> localReleasePosition(@PathVariable("positionType") Integer positionType
                                        , @PathVariable("positionNumber") String positionNumber){

        PositionInfo psInfo = positionInfoService.releasePosition(positionType,positionNumber);

        if(psInfo != null) {
            psInfo.setPositionStatus(0);
            return ResultUtil.successful(psInfo);
        }else {
            return ResultUtil.failled(-1,"release failed,not found this position");
        }

    }

    /**
     * for cloud to release position
     * input is positionType,positionNumber list
     * positions:[
     * {
     *     positionType:
     *     positionNumber:
     * }]
     * */
    @PostMapping(value = "/releaseV2")
    public Result<PositionWrapper> releasePosition(@RequestBody final String positions,
                                                @RequestHeader(value = "signature") String signature,
                                                @RequestHeader(value = "timestamp") String timestamp){

        try {
            //validate client signature, if failed throw exception;
            if(!ValidateUtil.validateSignature(signature,positions.toString()+";"+timestamp.toString())){
                throw new SignatureValidateFailedException(100,"signature validated failed");
            }else {
                        ObjectMapper objectMapper = new ObjectMapper();

                        PositionWrapper positionWrapper = objectMapper.readValue(positions,PositionWrapper.class);

                        for(PositionInfo psInfo : positionWrapper.getPositions())
                        {
                            positionInfoService.releasePosition(psInfo.getPositionType(),psInfo.getPositionNumber());
                            psInfo.setPositionStatus(0);
                            psInfo.setOrderNumber("");
                            psInfo.setItemId(0);
                        }
                        return ResultUtil.successful(positionWrapper);
                   }
            }catch (Exception e)
            {
                return ResultUtil.failled(-1,"release position failed;");
            }
    }

    /***
     *
     * */
    @RequestMapping(value = "/release",method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public Result<PositionWrapper> releasePosition(@RequestBody PositionWrapper  positions){

        try {
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                PositionWrapper positionWrapper = objectMapper.readValue(positions,PositionWrapper.class);

            for(PositionInfo psInfo : positions.getPositions())
            {
                positionInfoService.releasePosition(psInfo.getPositionType(),psInfo.getPositionNumber());
                psInfo.setPositionStatus(0);
                //positionInfoService.
            }
            return ResultUtil.successful(positions);
        }catch (Exception e)
        {
            return ResultUtil.failled(-1,"release position failed;");
        }
    }


    /**
     * by using validateSignature method to validate whether this call can be passed;
     *
     * */
    @RequestMapping(value = "/createPosition",method = {RequestMethod.POST,RequestMethod.PUT})
    public Result<PositionInfo> createPosition(@RequestBody PositionInfo positionInfo,
                                               @RequestHeader(value = "signature") String signature,
                                               @RequestHeader(value="timestamp") String timestamp) throws Exception {

        //validate client signature, if failed throw exception;
        if(!ValidateUtil.validateSignature(signature,positionInfo.toString()+timestamp.toString())){
            throw new SignatureValidateFailedException(100,"signature validated failed");
        }else{
            positionInfoService.savePosition(positionInfo);
        }

        return ResultUtil.successful(positionInfo);
    }
}
