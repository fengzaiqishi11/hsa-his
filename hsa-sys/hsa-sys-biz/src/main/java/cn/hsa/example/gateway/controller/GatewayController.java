//package cn.hsa.example.gateway.controller;
//
//import cn.hsa.hsaf.core.framework.web.WrapperResponse;
//import cn.hsa.hsaf.core.gateway.*;
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping(value = "/web/gateway")
//@Slf4j
//public class GatewayController {
//
//    /**
//     * 通过配置切换ApiCaller，即可实现一套代码，切换网关
//     */
//    @Value("${gateway.apicaller:csbcaller4channelA}")
//    private String apiCallerName;
//
//    @Autowired(required = false)
//    private ApiCallerManager apiCallerManager;
//
//    @GetMapping(value = "/get")
//    public WrapperResponse<String> doGet() {
//
//        //例如，这里调用阿里网关，参考gateway.xml配置
//        ApiCaller apiCaller = apiCallerManager.getApiCaller(apiCallerName);
//
//        HttpParameters hp = HttpParameters.newBuilder()
//                .api("getSomething")
//                .version("1.0.0")
//                .method("GET")  //支持GET、POST两种
//                .putParamsMap("paramA", "valueAAA")  //这里设置的请求参数，跟URL后面加 “?xxx=xxx&yyy=yyy”效果是一样的
//                .putParamsMap("paramB", "valueBBB")
//                .putHeaderParamsMap("Content-Type", "application/json;charset=utf-8")
//                .putHeaderParamsMap("Accept", "application/json")
//                .putHeaderParamsMap("kkk", "vvvv")//可设置其他http头部
//                .build();
//
//        //添加额外的附加参数
//        Map<String, String> extMap = new HashMap<String, String>();
//        extMap.put("k1", "v1");
//        extMap.put("k2", "v2");
//
//        //进行调用 返回结果
//        String result = null;
//        try {
//            result = apiCaller.invoke(hp, extMap);//如果没有附加参数，extMap可以为null
//            //{"code":200,"message":"成功","body":{"userName":"newbies","userAge":"123","othermsg":"xxxxxxx"}}
//        } catch (ApiCallerException e) {
//            //处理异常
//            log.error("Error", e);
//            return WrapperResponse.error(123456, "失败", null);
//        }
//
//        return WrapperResponse.success(result);
//    }
//
//    @GetMapping(value = "/getWarpperResult")
//    public WrapperResponse<String> doGetWarpperResult() {
//
//        //例如，这里调用阿里网关，参考gateway.xml配置
//        ApiCaller apiCaller = apiCallerManager.getApiCaller(apiCallerName);
//
//        HttpParameters hp = HttpParameters.newBuilder()
//                .api("getSomething")
//                .version("1.0.0")
//                .method("GET")  //支持GET、POST两种
//                .putParamsMap("paramA", "valueAAA")  //这里设置的请求参数，跟URL后面加 “?xxx=xxx&yyy=yyy”效果是一样的
//                .putParamsMap("paramB", "valueBBB")
//                .putHeaderParamsMap("Content-Type", "application/json;charset=utf-8")
//                .putHeaderParamsMap("Accept", "application/json")
//                .putHeaderParamsMap("kkk", "vvvv")//可设置其他http头部
//                .build();
//
//        //添加额外的附加参数
//        Map<String, String> extMap = new HashMap<String, String>();
//        extMap.put("k1", "v1");
//        extMap.put("k2", "v2");
//
//        //进行调用 返回结果
//        WrapperResponse<String> result = null;
//        try {
//            result = apiCaller.invokeWR(hp, extMap);//如果没有附加参数，extMap可以为null
//            //{"code":200,"message":"成功","body":{"userName":"newbies","userAge":"123","othermsg":"xxxxxxx"}}
//        } catch (ApiCallerException e) {
//            //处理异常
//            log.error("Error", e);
//            return WrapperResponse.error(123456, "失败", null);
//        }
//
//        return result;
//    }
//
//    @GetMapping(value = "/post")
//    public WrapperResponse<String> doPost() {
//        //例如，这里调用腾讯里约网关，参考gateway.xml配置
//        ApiCaller apiCaller = apiCallerManager.getApiCaller(apiCallerName);
//
//        //设置请求参数（json格式)
//        String jsonData = "{\"new-node\":\"test\"}";
//        ContentBody cb = new ContentBody(jsonData);
//        //ContentBody cb = new ContentBody(new File("xxx.doc"));//附件上传
//
//        HttpParameters hp = HttpParameters.newBuilder()
//                .api("postSomething")
//                .version("1.0.0")
//                .method("POST")  //支持GET、POST两种
//                //.putParamsMap("paramA", "valueAAA")  //这里设置的请求参数，跟URL后面加 “?xxx=xxx&yyy=yyy”效果是一样的
//                //.putParamsMap("paramB", "valueBBB")
//                //.putHeaderParamsMap("Content-Type", "application/json;charset=utf-8")
//                //.putHeaderParamsMap("Accept", "application/json")
//                //.putHeaderParamsMap("kkk", "vvvv")//可设置其他http头部
//                .contentBody(cb) //如果是get请求，参数直接通过putParamsMap设置，不需要ContentBody
//                .build();
//
//        //添加额外的附加参数
//        Map<String, String> extMap = new HashMap<String, String>();
//        //extMap.put("k1", "v1");
//        //extMap.put("k2", "v2");
//
//        //进行调用 返回结果
//        String result = null;
//        try {
//            result = apiCaller.invoke(hp, extMap);//如果没有附加参数，extMap可以为null
//            //{"code":200,"message":"成功","body":{"userName":"newbies","userAge":"123","othermsg":"xxxxxxx"}}
//        } catch (ApiCallerException e) {
//            //处理异常
//            log.error("Error", e);
//            return WrapperResponse.error(123456, "失败", null);
//        }
//
//        return WrapperResponse.success(result);
//    }
//
//    @GetMapping(value = "/add")
//    public WrapperResponse<String> add(Integer va, Integer vb) {
//        ApiCaller apiCaller = apiCallerManager.getApiCaller(apiCallerName);
//        HttpParameters hp = HttpParameters.newBuilder()
//                .api("siniu_px_math_add")
//                .version("1.0.0")
//                .method("GET")  //支持GET、POST两种
//                .putParamsMap("va", String.valueOf(va))  //这里设置的请求参数，跟URL后面加 “?xxx=xxx&yyy=yyy”效果是一样的
//                .putParamsMap("vb", String.valueOf(vb))
//                .build();
//
//        //添加额外的附加参数
//        Map<String, String> extMap = new HashMap<String, String>();
//
//        //进行调用 返回结果
//        String result = null;
//        try {
//            result = apiCaller.invoke(hp, extMap);//如果没有附加参数，extMap可以为null
//            //返回的result内容参考如下（不同的服务网关产品，返回的字符串格式是不一样的）：
//            //{"code":200,"message":"成功","body":{"userName":"newbies","userAge":"123","othermsg":"xxxxxxx"}}
//        } catch (ApiCallerException e) {
//            e.printStackTrace();
//        }
//        return WrapperResponse.success(JSON.parseObject(result).getString("body"));
//    }
//
////    @GetMapping(value = "/insertuser")
////    public WrapperResponse<String> insertUser(UserDTO user) {
////        ApiCaller apiCaller = apiCallerManager.getApiCaller(apiCallerName);
////        HttpParameters hp = HttpParameters.newBuilder()
////                .service("siniu_px_math_insertuser")
////                .version("1.0.0")
////                .method("POST")  //支持GET、POST两种
////                .putParamsMap("user", JSON.toJSONString(user))
////                .build();
////
////        //添加额外的附加参数
////        Map<String, String> extMap = new HashMap<String, String>();
////
////        //进行调用 返回结果
////        String result = null;
////        try {
////            result = apiCaller.invoke(hp, extMap);//如果没有附加参数，extMap可以为null
////            //返回的result内容参考如下（不同的服务网关产品，返回的字符串格式是不一样的）：
////            //{"code":200,"message":"成功","body":{"userName":"newbies","userAge":"123","othermsg":"xxxxxxx"}}
////        } catch (ApiCallerException e) {
////            e.printStackTrace();
////        }
////        return WrapperResponse.success(JSON.parseObject(result).getString("body"));
////    }
//
//}
