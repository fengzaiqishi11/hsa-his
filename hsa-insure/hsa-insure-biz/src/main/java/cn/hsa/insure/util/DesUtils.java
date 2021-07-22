package cn.hsa.insure.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: DesUtils
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 11:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class DesUtils {

    public DesUtils() {
    }

    public static void main(String[] args) {
        String str = "12345678901234567890123";
        String password = "phealth8";
        byte[] result = encrypt(str.getBytes(), password);
        System.out.println("加密后,长度：" + result.length + "，内容:" + new String(result));

        try {
            byte[] decryResult = decrypt(result, password);
            System.out.println("解密后：" + new String(decryResult));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static byte[] encrypt(byte[] datasource, String password) {
        if (datasource.length / 8 * 8 != datasource.length) {
            byte[] bTmp = Arrays.copyOf(datasource, (datasource.length / 8 + 1) * 8);

            for(int i = datasource.length; i < bTmp.length; ++i) {
                bTmp[i] = 0;
            }

            datasource = bTmp;
        }

        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, securekey, random);
            return cipher.doFinal(datasource);
        } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] src, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(2, securekey, random);
        return cipher.doFinal(src);
    }

}
