package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpConnectUtil {
    private static Logger logger = LoggerFactory.getLogger("HttpConnectUtil");
	@SuppressWarnings("unchecked")
	public static String doPost(Map<String,Object> param){
		String URL = MapUtils.get(param, "url");
		String paramStr = MapUtils.get(param, "param");
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        Exception exception = null;
        try{
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(600000);
            conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            //获取输出流
            out = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
            out.write(paramStr);
            out.flush();
            out.close();
            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null){
                    result.append(line);
                }
            }else{
                throw new RuntimeException("接口地址："+URL+", http返回码为："+conn.getResponseCode());
            }
        }catch (Exception e){
            exception = e;
            throw new RuntimeException(e.getMessage());
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }finally {
                if (exception != null) {
                    JSONObject errorMsg = new JSONObject();
                    errorMsg.put("exception",exception.getMessage());
                    result.append(errorMsg.toJSONString());
                }
                return result.toString();
			}
        }
    }

    public static Map<String,Object> sendPost(String urlS, String param) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urlS);
        String result = null;
        Map resultMap = new HashMap<>();
        try {
            StringEntity s = new StringEntity(param, Charset.forName("UTF-8"));
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.addHeader("Content-type","application/json; charset=utf-8");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity);
                resultMap = JSONObject.parseObject(result,Map.class);
                logger.info("调用成功，返回参数为： " + resultMap.get("code"));
            }
        } catch (Exception e) {
            logger.error("http 调用发生了异常: 请求路径：{}，请求数据：{},详细堆栈日志：{}",urlS,param, ExceptionUtil.stacktraceToString(e));

            throw new RuntimeException(e);
        }
        return resultMap;
    }

    public static String doGet(String URL){
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try{
            //创建远程url连接对象
            URL url = new URL(URL);
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


    public static String postMethod(String url,String param){
        // 结果值
        StringBuffer rest=new StringBuffer();

        HttpURLConnection conn=null;
        OutputStream out=null;
        BufferedReader br=null;

        try {
            // 创建 URL
            URL restUrl = new URL(url);
            // 打开连接
            conn= (HttpURLConnection) restUrl.openConnection();
            // 设置请求方式为 POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection","keep-Alive");
            // 设置接收文件类型
//            conn.setRequestProperty("Accept","application/json");
            //设置发送文件类型
            /**
             这里注意  传递JSON数据的话 就要设置
             普通参数的话 就要注释掉
             */
            conn.setRequestProperty("Content-Type","application/json");
            // 输入 输出 都打开
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //开始连接
            conn.connect();

            // 传递参数  流的方式
            out=conn.getOutputStream();
            out.write(param.getBytes());
            out.flush();

//            if (200 == conn.getResponseCode()){
            // 读取数据
            br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line=null;
            while (null != (line=br.readLine())){
                rest.append(line);
            }
//            }
        } catch (Exception e) {
            logger.error("http 调用发生了异常: 请求路径：{}，请求数据：{},详细堆栈日志：{}",url,param, ExceptionUtil.stacktraceToString(e));

            throw new AppException("请求失败:"+e.getMessage());
        }finally {
            // 关闭所有通道
            try {
                if (br!=null) {
                    br.close();
                }
                if (out!=null) {
                    out.close();
                }
                if (conn!=null) {
                    conn.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rest.toString();
    }

    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String getMethod(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("http 调用发生了异常: 请求路径：{}，请求数据：{},详细堆栈日志：{}",url,param, ExceptionUtil.stacktraceToString(e));

            throw new AppException("请求失败:"+e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @Description: post方式调用统一支付平台接口，入参是json格式
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/18 17:33
     * @Return
     */
    public static String unifiedPayPostUtil(String path,String data){
        // 结果值
        StringBuffer rest=new StringBuffer();
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //请求方式
            conn.setRequestMethod("POST");
//           //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type","application/json");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF8");
            //发送请求参数即数据
            out.write(data);
            //缓冲数据
            out.flush();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF8"));
            String str = "";
            while ((str = br.readLine()) != null) {
                rest.append(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
        } catch (Exception e) {
            logger.error("http 调用发生了异常: 请求路径：{}，请求数据：{},详细堆栈日志：{}",path,data, ExceptionUtil.stacktraceToString(e));
            throw new AppException("doPost异常：" + e.getMessage());
        }

        return rest.toString();
    }

    /**向服务器发送Post请求
    * @Method sendPostToCall
    * @Desrciption 
    * @param path
    * @param data
    * @Author liuqi1
    * @Date   2021/4/16 14:52
    * @Return java.lang.String
    **/
    public static String sendPostToCall(String path,String data) {
        String resultStr = sendToCall(path,data,"POST");
        return resultStr;
    }

    /**向服务器发送Get请求
    * @Method sendGetToCall
    * @Desrciption
    * @param path
    * @param data
    * @Author liuqi1
    * @Date   2021/4/16 14:58
    * @Return java.lang.String
    **/
    public static String sendGetToCall(String path,String data) {
        String resultStr = sendToCall(path,data,"GET");
        return resultStr;
    }

    /**向服务器发送指定方式请求
    * @Method sendToCall
    * @Desrciption
    * @param path
    * @param data
    * @param sendType
    * @Author liuqi1
    * @Date   2021/4/16 14:58
    * @Return java.lang.String
    **/
    private static String sendToCall(String path,String data,String sendType){
        if (data == null || data.trim().length() == 0) {
            logger.error("请求参数为空!");
            return getErrorResult("请求参数为空!");
        }

        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(sendType);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(90000);
            // 设置文件长度
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.write(data);
            out.flush();
            out.close();

            // 请求返回的状态
            if (connection.getResponseCode() == 200) {
                // 读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"),1024);
                String lines = null;
                while ((lines = reader.readLine()) != null) {
                    sb.append(lines);
                }
                reader.close();
                // 断开连接
                connection.disconnect();
            }
        } catch (Exception e) {
            logger.error("调用接口失败,请求路径：{}, 请求数据：{},请求方法：{}, 详细堆栈：{}", path,data,sendType,ExceptionUtil.stacktraceToString(e));
            return getErrorResult("调用接口失败,原因：" + e.getMessage());
        }

        logger.info("调用接口完成：返回参数：" + sb.toString());

        if(StringUtils.isEmpty(sb.toString())){
            return getErrorResult("调用接口失败,原因：" + sb.toString());
        }

        return sb.toString();
    }

    //错误封装返回
    private static String getErrorResult(String message) {
        return "{\"infcode\":\"-1\", \"err_msg\":\"" + message + "\"}";
    }

    public static String doPostByXXX(String URL,Map<String,Object> params){
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        Exception exception = null;
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sbParams.append(e.getKey());
                sbParams.append("=");
                sbParams.append(e.getValue());
                sbParams.append("&");
            }
        }
            try{
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(600000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //获取输出流
            out = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
            if (sbParams != null && sbParams.length() > 0) {
                // 发送请求参数
                out.write(sbParams.substring(0, sbParams.length() - 1));
                // flush输出流的缓冲
                out.flush();
            }
            out.close();
            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null){
                    result.append(line);
                }
            }else{
                throw new RuntimeException("接口地址："+URL+", http返回码为："+conn.getResponseCode());
            }
        }catch (Exception e){
            exception = e;
            throw new RuntimeException(e.getMessage());
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }finally {
                if (exception != null) {
                    JSONObject errorMsg = new JSONObject();
                    errorMsg.put("exception",exception.getMessage());
                    result.append(errorMsg.toJSONString());
                }
                return result.toString();
            }
        }
    }


}
