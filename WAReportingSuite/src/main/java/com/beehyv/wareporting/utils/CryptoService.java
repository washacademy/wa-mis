package com.beehyv.wareporting.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoService {

    public static String decrypt(LoginUser loginUser) throws Exception {

        String password = "ABCD123";
        int keySize = 8; // 8 words = 256-bit
        int ivSize = 4; // 4 words = 128-bit
        String cipherTextHex="";
        String saltHex="";
        if(loginUser.getCipherTextHex()==null){
            String mistoken = loginUser.getPassword();
            mistoken = mistoken + "=";
            byte[] decoded = Base64.decodeBase64(mistoken);
            mistoken = new String(decoded, "UTF-8");
            String[] tokenItems = mistoken.split("\\|\\|");

            cipherTextHex = tokenItems[0];
            saltHex = tokenItems[1];
        }
        else{
            cipherTextHex = loginUser.getCipherTextHex();
            saltHex = loginUser.getSaltHex();
        }


        byte[] salt = hexStringToByteArray(saltHex);
        byte[] cipherText = hexStringToByteArray(cipherTextHex);

        byte[] javaKey = new byte[keySize * 4];
        byte[] javaIv = new byte[ivSize * 4];
        evpKDF(password.getBytes("UTF-8"), keySize, ivSize, salt, javaKey, javaIv);

        Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!

        IvParameterSpec ivSpec = new IvParameterSpec(javaIv);
        aesCipherForEncryption.init(Cipher.DECRYPT_MODE, new SecretKeySpec(javaKey, "AES"), ivSpec);

        byte[] byteMsg = aesCipherForEncryption.doFinal(cipherText);

        return new String(byteMsg);

    }

    private static byte[] evpKDF(byte[] password, int keySize, int ivSize, byte[] salt, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        return evpKDF(password, keySize, ivSize, salt, 1, "MD5", resultKey, resultIv);
    }

    private static byte[] evpKDF(byte[] password, int keySize, int ivSize, byte[] salt, int iterations, String hashAlgorithm, byte[] resultKey, byte[] resultIv) throws NoSuchAlgorithmException {
        int targetKeySize = keySize + ivSize;
        byte[] derivedBytes = new byte[targetKeySize * 4];
        int numberOfDerivedWords = 0;
        byte[] block = null;
        MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
        while (numberOfDerivedWords < targetKeySize) {
            if (block != null) {
                hasher.update(block);
            }
            hasher.update(password);
            block = hasher.digest(salt);
            hasher.reset();

            // Iterations
            for (int i = 1; i < iterations; i++) {
                block = hasher.digest(block);
                hasher.reset();
            }

            System.arraycopy(block, 0, derivedBytes, numberOfDerivedWords * 4,
                    Math.min(block.length, (targetKeySize - numberOfDerivedWords) * 4));

            numberOfDerivedWords += block.length/4;
        }

        System.arraycopy(derivedBytes, 0, resultKey, 0, keySize * 4);
        System.arraycopy(derivedBytes, keySize * 4, resultIv, 0, ivSize * 4);

        return derivedBytes; // key + iv
    }

    /**
     * Copied from http://stackoverflow.com/a/140861
     * */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}