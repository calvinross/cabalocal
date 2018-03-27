package com.ross53.cobar.service;


import com.ross53.cobar.domain.InfoCode;
import com.ross53.cobar.repository.InfoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoCodeService {

    @Autowired
    private InfoCodeRepository infoCodeRepository;

    public InfoCode saveInfoCode(InfoCode infoCode){

        return infoCodeRepository.save(infoCode);
    }

    public InfoCode getInfoCode(Integer id, String code){

        return  null;
    }

    public List<InfoCode> getInfoCode(String code){


        return infoCodeRepository.getInfoCodeByCode(code);
    }

    public InfoCode getInfoCode(String code, Integer status){

        return null;
    }

    public List<InfoCode> getAllProcessedInfoCode(){

        Integer codeStatus = 2; //2 means processed by Cloud;
        return infoCodeRepository.getInfoCodes(codeStatus);
    }

    public boolean getUnprocessedCode(String code){

         List<InfoCode> infoCodes = infoCodeRepository.getUnprocessedCode(code);

         if(infoCodes.size()>0){
             System.out.println(infoCodes.get(0).getInfoCode());
             return true;
         }else {
             return false;
         }
    }
}
