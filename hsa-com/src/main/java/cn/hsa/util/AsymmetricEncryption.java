package cn.hsa.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @ClassName: AsymmetricEncryptionExample
 * @Description:
 * @author: marong
 * @date: 2021-06-10 09:16
 */
public class AsymmetricEncryption {


    //private static PrivateKey privateKey = SecureUtil.generatePrivateKey("RSA", Base64.decode(pri));
    //private static PublicKey publicKey  = SecureUtil.generatePublicKey("RSA", Base64.decode(pub));

    private  static String pri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMjN1v/ZCJ0UDhtXcSV5o5A4qSarJhfoU6bYGkA3BIWUab5YIBHm5sP+WPQa9j7+JjbXMZbH38fyS9Yuenb6MeZfkf/Kq2R9ElSu4Hv6MA22W0iLBsN8usCld3iZFi8hedDuUYa5zot3JdjWTE7qrAVPiZfbnWdyiL9DoaJarp7RAgMBAAECgYAyCLYYcHH4v8FHF0nqWk2C11dweudGQD2tvj2GQB84kJMrmkY9Z7Ea53KvOlOevRF1CzhAN4PfTJSQZOrfX/aUHSOer981jkz+gBrddbXM6tcWEdYvltv/B52VtKtlfZ0FFrD5E+AUhtNl+sPQW/rAPvzAhagi9GUxTyIeku/XBQJBAO1EFDUN4iT1RHPQn0Q/Bvg6NpRTYJ1KINjQKfOT0A32+ypeRDbWOXKO87C0lMyqmTdk4Vu8Qvues390+MV2IwUCQQDYqL9FFal7P6VXDyHVO4JeW+ArzWAy/hsryHx8WRJtgBKdr5QQtNU0KoYQV4o2OYK8YfBcSlcwyQLyZSCd5S5dAkAxaQwMQGfn6mvm8ns4aye4aecT3IkyOE2+2RrjbtTVE6oamKb8BIDSKpL1KaYTOTPPJWMJB5uratFWqbVk6cVBAkAOoO4xMvI7kSOPykTFifwWdOug6YAKUV/yR9on5ze4TwJyxS0hJ6SRniFpLw/081Mhdxdsk9RpRdk+FpfqMVQ9AkAJe540Bh/BSpRdKiEsWJP9ymytOLhv8Q8/ZCuzrh363q0d+Bf59ThmQo7vR/7A78w95Q+g+TuqaQdts9T1UocT";

    private  static String pub  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIzdb/2QidFA4bV3EleaOQOKkmqyYX6FOm2BpANwSFlGm+WCAR5ubD/lj0GvY+/iY21zGWx9/H8kvWLnp2+jHmX5H/yqtkfRJUruB7+jANtltIiwbDfLrApXd4mRYvIXnQ7lGGuc6LdyXY1kxO6qwFT4mX251ncoi/Q6GiWq6e0QIDAQAB";

    private static RSA prirsa = new RSA();

    private static RSA pubrsa = new RSA();


    public static String priencrypt (String data) throws UnsupportedEncodingException {
        prirsa.setPrivateKey(SecureUtil.generatePrivateKey("RSA", Base64.decode(pri)));
        data = URLEncoder.encode(data,"UTF-8");
        //私钥加密
        byte[] encrypt = prirsa.encrypt(StrUtil.bytes(data, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        String str = HexUtil.encodeHexStr(encrypt);
        return str;
    }

    public static String pubdecrypt(String data) throws UnsupportedEncodingException {
        pubrsa.setPublicKey(SecureUtil.generatePublicKey("RSA", Base64.decode(pub)));
        // 公钥解密
        byte[] decrypt = pubrsa.decrypt(HexUtil.decodeHex(data), KeyType.PublicKey);
        String  enstr = new String(decrypt,  CharsetUtil.CHARSET_UTF_8);
        String decode = URLDecoder.decode(enstr, "UTF-8");
        return  decode;
    }



    public static String pubencrypt (String data) throws UnsupportedEncodingException {
        pubrsa.setPublicKey(SecureUtil.generatePublicKey("RSA", Base64.decode(pub)));
        data = URLEncoder.encode(data,"UTF-8");
        //公钥加密
        byte[] encrypt = pubrsa.encrypt(StrUtil.bytes(data, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String str = HexUtil.encodeHexStr(encrypt);
        return str;
    }

    public static String pridecrypt(String data) throws UnsupportedEncodingException {
        prirsa.setPrivateKey(SecureUtil.generatePrivateKey("RSA", Base64.decode(pri)));
        // 私钥解密
        byte[] decrypt = prirsa.decrypt(HexUtil.decodeHex(data), KeyType.PrivateKey);
        String  enstr = new String(decrypt,  CharsetUtil.CHARSET_UTF_8);
        String decode = URLDecoder.decode(enstr, "UTF-8");
        return  decode;
    }

    public static Object getJson(Object object){
        Object o = JSON.toJSON(object);
        return o;
    }

    public static void main(String[] args) {
        try {
            // 私钥解密
            String data = "";
            String bb = pridecrypt(data);
            System.out.println("bb = " + JSON.parse(bb));

            // 私钥加密
           /* Map map = new HashMap();
            map.put("hospCode", "1000001");
            map.put("certNo", "133213199008090321");
            Object json = getJson(map);
            System.out.println("json = " + json);*/

            // 公钥解密
           /* String g = "";
            String gg = pubdecrypt(g);
            System.out.println("gg = " + gg);*/
            // 公钥加密

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
