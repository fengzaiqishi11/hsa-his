package cn.hsa.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: MD5Utils
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/07/22 19:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class MD5Utils {
    /**
     * 加密盐前缀
     */
    private static final String SALT_PREFIX = "has";
    /**
     * 加密盐后缀
     */
    private static final String SALT_SUFFIX = "his";

    /**
     * md5和sha-512混合加密
     *
     * @param password 要加密的内容
     *
     * @return String md5和sha-512混合加密之后的密码
     */
    public static String getMd5AndSha(String password) {
        return DigestUtils.md5Hex(DigestUtils.sha512Hex(SALT_PREFIX + password + SALT_SUFFIX)).toUpperCase();
    }

    /**
     * 验证加盐后是否和原密码一致
     *
     * @param password 原密码
     * @param md5str 加密之后的密码
     *
     * @return boolean true表示和原密码一致   false表示和原密码不一致
     */
    public static boolean verifyMd5AndSha(String password, String md5str) {
        return getMd5AndSha(password).equals(String.valueOf(md5str));
    }

    /**
     * 验证前端加密后和原密码一致
     *
     * @param password 前端加密后
     * @param md5str 加密之后的密码
     *
     * @return boolean true表示和原密码一致   false表示和原密码不一致
     */
    public static boolean verifySha(String password, String md5str) {
        return password.equals(String.valueOf(md5str));
    }

    /**
     * MD5 32位加密
     *
     * @param encryptStr 加密前
     *
     * @return
     */
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }


}