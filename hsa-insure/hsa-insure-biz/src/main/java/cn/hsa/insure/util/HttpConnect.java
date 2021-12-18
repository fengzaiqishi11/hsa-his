package cn.hsa.insure.util;

import cn.hsa.util.KafkaUtil;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: HttpConnect
 * @Describe(描述): java请求调用服务工具类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/07 15:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class HttpConnect {


    /**
     * @Menthod doPost
     * @Desrciption POST请求
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/7 15:39
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static String doPost(Map<String,Object> param){
        String URL = MapUtils.get(param, "url");
        String paramStr = MapUtils.get(param, "param");
        BufferedReader br = null;
        HttpClient httpclient = null;
        try{
            httpclient = new DefaultHttpClient();
            KeyStore keyStore = KeyStore.getInstance(Constant.Xiangtan.CONFIG.KEY_STORE_TYPE_P12);
            KeyStore trustStore  = KeyStore.getInstance(Constant.Xiangtan.CONFIG.KEY_STORE_TYPE_JKS);
            InputStream ksIn = new FileInputStream(Constant.Xiangtan.CONFIG.KEY_STORE_CLIENT_PATH);
            InputStream tsIn = new FileInputStream(new File(Constant.Xiangtan.CONFIG.KEY_STORE_TRUST_PATH));
            try {
                keyStore.load(ksIn, Constant.Xiangtan.CONFIG.KEY_STORE_PASSWORD.toCharArray());
                trustStore.load(tsIn, Constant.Xiangtan.CONFIG.KEY_STORE_TRUST_PASSWORD.toCharArray());
            } finally {
                try { ksIn.close(); } catch (Exception ignore) {}
                try { tsIn.close(); } catch (Exception ignore) {}
            }
            SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,Constant.Xiangtan.CONFIG.KEY_STORE_PASSWORD,trustStore);
            Scheme sch = new Scheme(Constant.Xiangtan.CONFIG.SCHEME_HTTPS, Constant.Xiangtan.CONFIG.PORT, socketFactory);
            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);//证书的域名不验证
            httpclient.getConnectionManager().getSchemeRegistry().register(sch);
            HttpPost httpPost = new HttpPost(URL);
            String uuid = SnowflakeUtils.getId();
            //参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("uuid",uuid));
            nvps.add(new BasicNameValuePair("time", System.currentTimeMillis()+""));
            nvps.add(new BasicNameValuePair("desStr", UUIDDesUtils.generatKey(uuid)));
            nvps.add(new BasicNameValuePair("ylbxParamJson", paramStr));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            // 响应分析
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                StringBuffer result = new StringBuffer();
                String readLine = br.readLine();
                while (readLine != null) {
                    result.append(readLine);
                    readLine = br.readLine();
                }
                ResultBean resultBean = ResultBean.fromJson(result.toString());
                return (String) resultBean.get("ylbxParam_ret");
            }else{
                throw new SecurityException("调用医保服务失败。");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }finally {
            try{
                if (br != null){
                    br.close();
                }
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
    
    /**
     * @Menthod doGet
     * @Desrciption GET请求
     * @param URL url地址
     * @Author Ou·Mr
     * @Date 2020/11/7 15:44 
     * @Return java.lang.String
     */
    public static String doGet(String URL){
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try{
            //创建远程url连接对象
            java.net.URL url = new URL(URL);
            //通过远程url连接对象打开一个连接，强转成HTTPURLConnection类
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept", "application/json");
            //发送请求
            conn.connect();
            //通过conn取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()){
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null){
                    result.append(line);
                    System.out.println(line);
                }
            }else{
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(br != null){
                    br.close();
                }
                if(is != null){
                    is.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
            conn.disconnect();
        }
        return result.toString();
    }

    // kafka测试程序 - LiaoJiGuang
    public static void main(String args[]) {
        // 1. 创建一个kafka生产者
        String server = "8.136.110.29:9092";
        String topic = "LiaoJiGuang_test_topic";
        KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
        KafkaUtil.sendMessage(kafkaProducer,topic,"测试kafka有没有发送成功！");

        KafkaConsumer<String, String> kafkaConsumer = KafkaUtil.createConsumer(server,topic);
        KafkaUtil.readMessage(kafkaConsumer,1000);
    }


}
