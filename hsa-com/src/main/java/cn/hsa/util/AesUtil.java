package cn.hsa.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Base64Utils;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *  Aes加密，解密工具
 */
public class AesUtil {

    private static final String DEFAULT_AES_KEY = "733ace1a6914fba2434791dd607ce180";

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new BigDecimal("119.03").toString());
        String encoded = encodeWithDefaultKey(new BigDecimal("119.03").toString());
        System.out.println("加密后 "+encoded);
        System.out.println("解密后"+decodeWithDefaultKey(encoded));
    }


    /**
     * 生成key
     * @return
     */
    public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            return Hex.encodeHexString(byteKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES加密
     * @param thisKey
     * @param data
     * @return
     */
    public static String encode(String thisKey, String data) {
        try {
            // 转换KEY
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey),"AES");
            //System.out.println(thisKey);

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(data.getBytes());
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES解密
     * @param thisKey
     * @param data
     * @return
     */
    public static String decode(String thisKey, String data) {
        try {
            // 转换KEY
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey),"AES");
            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Hex.decodeHex(data));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES加密
     * @param data  需要被加密的数据
     * @return 加密后的字符串
     */
    public static String encodeWithDefaultKey(String data) {
        return encode(DEFAULT_AES_KEY,data);
    }

    /**
     * AES解密
     * @param data  需要解密的数据
     * @return 解密后的字符串数据
     */
    public static String decodeWithDefaultKey(String data) {
        return decode(DEFAULT_AES_KEY,data);
    }
}