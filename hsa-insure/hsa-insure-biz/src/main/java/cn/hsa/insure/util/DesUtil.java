package cn.hsa.insure.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: DesUtil
 * @Describe(描述): Des 加密
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/01/27 19:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class DesUtil {

    private final static String DES = "DES";
    private final static String ENCODE = "utf-8";
    private final static String defaultKey = "qwertyuiopasdfghjklzxcvbnm";

    /**
     * @Menthod encrypt
     * @Desrciption  DES加密
     * @param data 加密数据
     * @Author Ou·Mr
     * @Date 2021/1/27 19:05
     * @Return java.lang.String
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(), defaultKey.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * @Menthod decrypt
     * @Desrciption
     * @param data
     * @Author Ou·Mr
     * @Date 2021/1/27 19:02
     * @Return java.lang.String
     */
    public static String decrypt(String data) throws Exception {
        if (data == null)return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * @Menthod encrypt
     * @Desrciption   根据键值进行加密
     * @param data 数据 Base64
     * @param key 键
     * @Author Ou·Mr
     * @Date 2021/1/27 19:09
     * @Return byte[]
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }


    /**
     * @Menthod decrypt
     * @Desrciption 根据键值进行解密
     * @param data 解密byte数据
     * @param key 键
     * @Author Ou·Mr
     * @Date 2021/1/27 19:10
     * @Return byte[]
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
}
