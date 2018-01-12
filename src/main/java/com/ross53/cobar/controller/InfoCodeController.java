package com.ross53.cobar.controller;


import com.ross53.cobar.domain.InfoCode;
import com.ross53.cobar.domain.Result;
import com.ross53.cobar.service.InfoCodeService;
import com.ross53.cobar.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Device")
public class InfoCodeController {

    @Autowired
    InfoCodeService infoCodeService;

    /**
     * FOR Local to generate devices info code
     * and then save code into DB
     * and meanwhile to send code with id and infoCode to cloud to processing;
     *
     */
    @PostMapping("/generateInfoCode/{code}")
    public Result<InfoCode> putInfoCode(@PathVariable String code){

        try {
            InfoCode infoCode = new InfoCode();
            infoCode.setInfoCode(code);
            infoCode.setInfoStatus(0);

            /**
             * call cloud api to send info code to cloud
             * */

            return ResultUtil.successful(infoCodeService.saveInfoCode(infoCode));

        }catch (Exception e){
            return ResultUtil.failled(-1,e.getMessage());
        }
    }

    /**
     * For local to get infoCode feedback
     * get latest infoCode status back to local
     *
     * */
    @GetMapping("/getInfoCodes")
    public Result<List<InfoCode>> getInfoCodeFeedBack(){
        try{
            List<InfoCode> infoCodes = infoCodeService.getAllProcessedInfoCode();

            if(infoCodes.size()>0) {
                for (InfoCode code : infoCodes) {
                    code.setInfoStatus(3); ////3 means send status back to local;
                    infoCodeService.saveInfoCode(code);
                }

                return ResultUtil.successful(infoCodes);
            }else {
                return ResultUtil.failled(-1,"there is no resolved issue by now;");
            }
        }catch (Exception e){

            return ResultUtil.failled(-1,e.getMessage());
        }
    }

    /**
     * for cloud to put infoCode back
     *
     * */
    @GetMapping("/putInfoCodeBack/{id}/{code}")
    public Result<InfoCode> putInfoCodeBackFromCloud(@PathVariable Integer id,@PathVariable String code){
        return null;
    }

    /**
     * for cloud to put infoCode back
     *
     * */
    @GetMapping("/resolve/{code}")
    public Result<InfoCode> updateInfoCode(@PathVariable String code){
        try{
            List<InfoCode> infoCodes = infoCodeService.getInfoCode(code);
            if(infoCodes.size()>0){
                InfoCode infoCode = infoCodes.get(0);
                infoCode.setInfoStatus(2);
                return ResultUtil.successful(infoCodeService.saveInfoCode(infoCode));
            }else {
                return ResultUtil.failled(-1,"can not found this code need to be resolved");
            }
        }catch (Exception e){
            return ResultUtil.failled(-1,e.getMessage());
        }
    }

    /**
     * for cloud to put infoCodes back
     *
     * */
    @GetMapping("/updateInfoCodes}")
    public Result<List<InfoCode>> updateInfoCodes(@PathVariable Integer id,@PathVariable String code){
        return null;
    }
}
