package com.ross53.cobar.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateUtilTest {

    @Test
    public void validateSignatureTest()
    {
        boolean result =ValidateUtil.validateSignature("D306070C5C3E332E05A15EABA22AE1E4","{\n" +
                "    \"positionType\":1,\n" +
                "    \"positionNumber\":\"11\",\n" +
                "    \"positionStatus\":\"0\"\n" +
                "}");


        if(result){
            System.out.println("validate passing.....");
        }else {
            System.out.println("validate failed.......");
        }
    }

}