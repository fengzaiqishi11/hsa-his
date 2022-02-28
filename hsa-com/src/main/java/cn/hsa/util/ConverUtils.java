package cn.hsa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConverUtils
 * @Deacription 获取流 转换流
 * @Author liuzhoting
 * @Date 2022-02-18
 * @Version 3.0
 */
public class ConverUtils {
    /**
     * 根据文件url获取文件并转换为base64编码
     *
     * @param srcUrl        文件地址
     * @param requestMethod 请求方式（"GET","POST"）
     * @return 文件base64编码
     */
    public static String netSourceToBase64(String srcUrl, String requestMethod, String param) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024 * 8];
        try {
            // 创建URL
            URL url = new URL(srcUrl);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setConnectTimeout(10 * 1000);
            // 设置是否从HttpURLConnection输入，默认值为 true
            conn.setDoInput(true);
            //设置是否使用HttpURLConnection进行输出，默认值为 false
            conn.setDoOutput(true);
            conn.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
            // 得到请求的输出流对象,requestbody参数设置
            if (param != null) {
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                writer.write(param);
                writer.flush();
            }
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("获取模板文件流失败:地址错误url=" + srcUrl);
            }
            InputStream inStream = conn.getInputStream();
            int len;
            while (-1 != (len = inStream.read(data))) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (Exception e) {
            throw new RuntimeException("获取模板文件流失败:" + e.getMessage());
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outPut.toByteArray());
    }

    /**
     * 把base64转化文件流
     *
     * @param base64 base64
     * @return byte[] 文件流
     */
    public static byte[] decryptByBase64(String base64) {
        if (StringUtils.isEmpty(base64)) {
            return null;
        }
        return Base64.decodeBase64(base64.substring(base64.indexOf(",") + 1));
    }

    /**
     * inputStream转化为byte[]数组
     *
     * @param input InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static String getUrl(Map<String, Object> params, String fileName, String port, String contextPath) {
        //拼接报表设计器，获取对应的模板文件的url地址
        StringBuffer url = new StringBuffer();
        url.append("http://127.0.0.1:");
        url.append(port);
        url.append(contextPath);
        url.append("/ureport/pdf/show");
        url.append("?_u=file:");
        url.append(fileName);
        // 添加url参数,这里的参数是为了获取模板数据需要
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (params.get(key) == null) {
                    break;
                }
                String value = String.valueOf(params.get(key));
                url.append("&");
                url.append(key);
                url.append("=");
                url.append(value);
            }
        }
        return url.toString();
    }

    public static String getParamsToString(Map<String, Object> params) {
        StringBuffer param = new StringBuffer();
        int i = 0;
        String value;
        for (String key : params.keySet()) {
            if (i != 0) {
                param.append("&");
            }
            if (params.get(key) instanceof Map) {
                value = JSONObject.toJSONString(params.get(key));
            } else if (params.get(key) instanceof List) {
                value = JSONArray.toJSONString(params.get(key));
            } else {
                value = params.get(key).toString();
            }
            try {
                param.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
            }catch ( UnsupportedEncodingException e){

            }
            i++;
        }
        return param.toString();
    }

}


