package com.ross53.cobar.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * this class is used for encrypt / decrypt / generate signature / compare signature;
 * */
public class ValidateUtil {

    /**
     * setup default key;
     * */
    private  static final String key = "1234567890123456";
    private  static final String initVector = "1234567890123456";


    /***
     * generate local signature based on target and compare with original signature to check if it's equal;
     *
     * */
    public static boolean validateSignature(String originalSignature, String target){

        String encryptTarget = encryptAES(key,initVector,target);

        System.out.println(encryptTarget);

        String targetSignature = generateSignature(encryptTarget);

        System.out.println(targetSignature);

        if(originalSignature.equals(targetSignature)) {
            return true;
        }else {
            return false;
        }
    }


    /**
     * encrypt using AES method
     * */
    public static String encryptAES(String key, String initVector, String encryptValue) {
        try {
            //set encrypt method and private key
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"),"AES");

            //init 向量参数
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));

            //get encrypt instance
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            //Init cipher;
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivParameterSpec);

            //encrypt and return results which byte[]
            byte[] encrypted = cipher.doFinal(encryptValue.getBytes("UTF-8"));

            //encode to result
            String encryptResult = Base64.getEncoder().encodeToString(encrypted);

            return  encryptResult;
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * generate signature using MD5
     * */
    public static String generateSignature(String message){

        String signatureMessage = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            byte[] digest = md.digest();
            signatureMessage = DatatypeConverter.printHexBinary(digest).toUpperCase();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return signatureMessage;
    }

    /**
     * decrypt using by AES
     *
     * */
    public static String decryptAES(String key, String initVector, String encrypted) {

        try {
            //set primary key
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            //set 向量参数
            IvParameterSpec  ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));

            //get instance to decrypt and init parameters;
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            //decrypt and return decrypted return;
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));


            return new String(original, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
