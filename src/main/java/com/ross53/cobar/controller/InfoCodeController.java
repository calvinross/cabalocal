package com.ross53.cobar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ross53.cobar.EntityWrapper.InfoCodeWrapper;
import com.ross53.cobar.EntityWrapper.OrderGateWrapper;
import com.ross53.cobar.domain.InfoCode;
import com.ross53.cobar.domain.OrderGate;
import com.ross53.cobar.domain.Result;
import com.ross53.cobar.exception.SignatureValidateFailedException;
import com.ross53.cobar.service.InfoCodeService;
import com.ross53.cobar.utils.BuildRequestWithValidateHeaderUtil;
import com.ross53.cobar.utils.ResultUtil;
import com.ross53.cobar.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sound.sampled.Line;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Device")
public class InfoCodeController {

    @Autowired
    InfoCodeService infoCodeService;


    @Value("${clouduri.infocodeupdateuri}")
    private String infocodeupdateuri;

    /**
     * FOR Local to generate devices info code
     * and then save code into DB
     * and meanwhile to send code with id and infoCode to cloud to processing;
     *
     * if already existed unprocess,processing code, not save code into DB;
     */
    @PostMapping("/generateInfoCode/{code}")
    public Result<InfoCode> putInfoCode(@PathVariable String code){

        try {
            if(!infoCodeService.getUnprocessedCode(code)) {
                InfoCode infoCode = new InfoCode();
                infoCode.setInfoCode(code);
                infoCode.setInfoStatus(0);

                List<InfoCode> infoCodes = new ArrayList<>();
                infoCodes.add(infoCode);

                InfoCodeWrapper infoCodeWrapper = new InfoCodeWrapper();

                infoCodeWrapper.setInfoCodes(infoCodes);
                infoCodeWrapper.setStoreID(2);//need to configration in DB

                /**
                 * call cloud api to send info code to cloud
                 * */
                HttpEntity<String> request =
                        BuildRequestWithValidateHeaderUtil.getinfoCodeRestTemplate(infoCodeWrapper);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

                final String uri = infocodeupdateuri;//"http://brand.fugumobile.cn/q/cobarapi/order/update";

                ResponseEntity<String> resp= restTemplate.postForEntity(uri, request, String.class);


                if (resp.getStatusCode() != HttpStatus.OK) {
                    return ResultUtil.failled(-1,"infoCode Update to Cloud Failed;");
                }

                return ResultUtil.successful(infoCodeService.saveInfoCode(infoCode));
            }else
            {
                return ResultUtil.failled(-1,"this issue already reported and waiting for processing");
            }
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
    @PostMapping("/resolve/{code}")
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
    @PostMapping("/updateInfoCodes}")
    public Result<String> updateInfoCodes(@RequestBody final String infoCodes,
                                                  @RequestHeader(value = "signature") String signature,
                                                  @RequestHeader(value="timestamp") String timestamp){
        try{

            if(!ValidateUtil.validateSignature(signature,infoCodes.toString() + ";" + timestamp.toString())){
                throw new SignatureValidateFailedException(100, "signature validated failed");
            }else {
                ObjectMapper m = new ObjectMapper();
                InfoCodeWrapper infoCodeWrapper = m.readValue(infoCodes, InfoCodeWrapper.class);

                for(InfoCode code : infoCodeWrapper.getInfoCodes())
                {
                    infoCodeService.saveInfoCode(code);
                }

                return ResultUtil.successful("updated done");
            }

        }catch (Exception e){

            return ResultUtil.failled(-1,e.getMessage());
        }
    }
}
