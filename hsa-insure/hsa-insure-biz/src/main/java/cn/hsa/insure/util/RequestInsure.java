package cn.hsa.insure.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.entity.InsureConfigurationDO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: RequestInsure
 * @Describe(描述): 医保请求公共方法
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/10/29 14:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class RequestInsure {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    private static int timeOut = 60 * 60 - 500;

    private static final String producerTopicKey = "_insure_producer";
    private static final String consumerTopicKey = "_insure_consumer";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;


    /**
     * @Menthod toLogin
     * @Desrciption 医保登录平台
     * @param hospCode 医院编码
     * @param insureRegCode 医保注册编码
     * @Author Ou·Mr
     * @Date 2020/10/29 14:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    public String toLogin(String hospCode, String insureRegCode) throws Exception {
        if (StringUtils.isEmpty(hospCode) || StringUtils.isEmpty(insureRegCode)){
            throw new AppException("缺少必要参数。");
        }
        String sessionKey = new StringBuffer(hospCode).append(insureRegCode).append(Constants.INSURE.SESSION_KEY).toString();
        String sessionid = redisUtils.get(sessionKey);
        if (StringUtils.isNotEmpty(sessionid)){
            return sessionid;
        }
        InsureConfigurationDO insureConfigurationDO = toConfig(hospCode,insureRegCode);
        //获取到配置信息，封装配置信息
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("function_id",Constant.Xiangtan.RESTS.LOGIN);//登录功能号
        param.put("userid",insureConfigurationDO.getUsername());//用户名
        param.put("password",insureConfigurationDO.getPassword());//密码
        //param.put("currenttime", DateUtils.format(new Date(),DateUtils.YMDHMS));//当前时间
        //解析医保可识别的XML格式编码字符串
        Map<String,Object> httpParam = new HashMap<String,Object>();

        JSONObject paramObj = new JSONObject();
        paramObj.put("url",insureConfigurationDO.getUrl());
        paramObj.put("charset","UTF-8");//编码方式
        paramObj.put("param",HygeiaUtil.map2Xml("UTF-8",param));
        httpParam.put("data", paramObj);
        httpParam.put("attrCode",insureConfigurationDO.getAttrCode());
        String activityCode = SnowflakeUtils.getId();//交易号
        httpParam.put("activityCode",activityCode);
        Map<String,Object> resultObj = this.sendMessage(insureConfigurationDO.getRemark(),hospCode,httpParam,activityCode);
        Integer code = (Integer) resultObj.get("code");
        if (code < 0) {
            throw new AppException((String) resultObj.get("msg"));
        }
        String data = MapUtils.get(resultObj,"data");
        if (StringUtils.isNotEmpty(data)) {
            Map<String,Object> resultData = HygeiaUtil.xml2map(data);
            int return_code = Integer.parseInt((String) resultData.get("return_code"));
            if (return_code < 0){
                throw new AppException(resultData.get("return_code_message").toString());
            }
            sessionid = resultData.get("session_id").toString();
        }
        redisUtils.set(sessionKey,sessionid,timeOut);
        return sessionid;
    }

    /**
     * @Menthod toConfig
     * @Desrciption  获取医保配置信息
     * @param hospCode 医院编码
     * @param insureRegCode 医保注册编码
     * @Author Ou·Mr
     * @Date 2020/10/29 19:54
     * @Return cn.hsa.module.insure.insureConfiguration.entity.InsureConfigurationDO
     */
    public InsureConfigurationDO toConfig(String hospCode, String insureRegCode){
        //根据医院编码 + 医保注册编码 查找医保配置信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);//医院编码
        insureConfigurationDTO.setRegCode(insureRegCode);//医保注册机构编码
        List<InsureConfigurationDTO> insureConfigurationDTOList = insureConfigurationDAO.findByCondition(insureConfigurationDTO);
        if (insureConfigurationDTOList.isEmpty()){
            //数据库没有找到该配置信息
            throw new AppException("未找到医保配置信息；请联系管理员。");
        }
        return insureConfigurationDTOList.get(0);
    }

    /**
     * @Menthod call
     * @Desrciption
     * @param hospCode 医院编码
     * @param insureRegCode 医保机构编码
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/29 19:05
     * @Return java.util.Map 返回参数
     */
    public Map call(String hospCode,String insureRegCode,Map<String,Object> param){
        try {
            String sessionId = toLogin(hospCode,insureRegCode);
            param.put("session_id",sessionId);
            InsureConfigurationDO insureConfigurationDO = toConfig(hospCode,insureRegCode);

            Map<String,Object> httpParam = new HashMap<>();
            JSONObject paramObj = new JSONObject();
            paramObj.put("url",insureConfigurationDO.getUrl());
            paramObj.put("charset","UTF-8");//编码方式
            paramObj.put("param",HygeiaUtil.map2Xml("UTF-8",param));
            httpParam.put("data", paramObj);
            httpParam.put("attrCode",insureConfigurationDO.getAttrCode());
            String activityCode = SnowflakeUtils.getId();//交易号
            httpParam.put("activityCode",activityCode);
            Map<String,Object> resultObj = this.sendMessage(insureConfigurationDO.getRemark(),hospCode,httpParam,activityCode);
            Integer code = (Integer) resultObj.get("code");
            if (code < 0) {
                throw new AppException((String) resultObj.get("msg"));
            }
            String data = MapUtils.get(resultObj,"data");
            Map<String,Object> resultData = HygeiaUtil.xml2map(data);
            int return_code = Integer.parseInt((String) resultData.get("return_code"));
            if (return_code < 0){
                throw new AppException(resultData.get("return_code_message").toString());
            }
            return resultData;
        } catch (Exception e) {
            throw new AppException("调用医保提示【"+e.getMessage()+"】");
        }
    }

    /**
     * @Menthod callNeusoft
     * @Desrciption  调用东软医保工具类
     * @param hospCode 医院编码
     * @param insureRegCode 医保机构code
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/21 19:17
     * @Return java.lang.String
     */
    public Map callNeusoft(String hospCode,String insureRegCode,String param) {
        try {
            InsureConfigurationDO insureConfigurationDO = toConfig(hospCode, insureRegCode);
            Map<String, Object> httpParam = new HashMap<String, Object>();
            httpParam.put("attrCode",insureConfigurationDO.getAttrCode());
            String activityCode = SnowflakeUtils.getId();//交易号
            httpParam.put("activityCode",activityCode);
            logger.info("入参:[" + param + "]");
            httpParam.put("data", param);
            Map<String,Object> resultObj = this.sendMessage(insureConfigurationDO.getRemark(),hospCode,httpParam,activityCode);
            logger.info("出参:[" + resultObj.toString() + "]");
            Integer code = (Integer) resultObj.get("code");
            if (code < 0) {
                throw new AppException((String) resultObj.get("msg"));
            }
            return resultObj;
        }catch (Exception e){
            throw new AppException("调用医保提示【"+e.getMessage()+"】");
        }
    }

    /**
     * @Menthod toLoginHNS
     * @Desrciption 省医保登录平台
     * @param hospCode 医院编码
     * @param insureRegCode 医保注册编码
     * @Author Ou·Mr
     * @Date 2020/10/29 14:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    public String toLoginHNS(String hospCode, String insureRegCode) throws Exception {
        if (StringUtils.isEmpty(hospCode) || StringUtils.isEmpty(insureRegCode)){
            throw new AppException("缺少必要参数。");
        }
        String sessionKey = new StringBuffer(hospCode).append(insureRegCode).append(Constants.INSURE.SESSION_KEY).toString();
        String sessionid = redisUtils.get(sessionKey);
        if (StringUtils.isNotEmpty(sessionid)){
            return sessionid;
        }
        InsureConfigurationDO insureConfigurationDO = toConfig(hospCode,insureRegCode);
        //获取到配置信息，封装配置信息
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("FunctionID",Constant.HuNanSheng.RESTS.LOGIN);//登录功能号
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("login_id",insureConfigurationDO.getUsername());
        parameters.put("login_password",insureConfigurationDO.getUsername());
        param.put("parameters",parameters);//参数
        JSONObject paramObj = new JSONObject();
        paramObj.put("url",insureConfigurationDO.getUrl());
        paramObj.put("charset","gbk");//编码方式
        paramObj.put("param",HygeiaUtil.map2Xml("utf-8",param));//请求医保参数

        Map<String,Object> resultData = new HashMap<>();
        // 获取系统参数是直连医保还是走消息队列连接，默认消息队列
        SysParameterDTO sysParamDTO = this.getSysParamDTO(hospCode, "DIRECT_OR_QUEUE");

        if (sysParamDTO != null && StringUtils.isNotEmpty(sysParamDTO.getValue()) && "1".equals(sysParamDTO.getValue())) {
            // 直连医保
            logger.debug("*****开始【湖南省医保登录】直连方法*****");
            logger.info("【湖南省医保登录】直连入参：" + JSON.toJSONString(paramObj));
            String doPost = HttpConnectUtil.doPost(paramObj);
            logger.info("【湖南省医保登录】直连返参字符串：" + doPost);
            resultData = HygeiaUtil.xml2map(doPost);
            logger.info("【湖南省医保登录】直连返参XML解析后：" + resultData);
            logger.debug("*****结束【湖南省医保登录】直连方法*****");
        } else {
            //解析医保可识别的XML格式编码字符串
            Map<String,Object> resultObj = new HashMap<>();
            Map<String,Object> httpParam = new HashMap<String,Object>();
            httpParam.put("attrCode",insureConfigurationDO.getAttrCode());
            String activityCode = SnowflakeUtils.getId();//交易号
            httpParam.put("activityCode",activityCode);
            httpParam.put("data", paramObj);
            resultObj = this.sendMessage(insureConfigurationDO.getRemark(),hospCode,httpParam,activityCode);
            Integer code = (Integer) resultObj.get("code");
            if (code < 0) {
                throw new AppException((String) resultObj.get("msg"));
            }
            resultData = HygeiaUtil.xml2map((String) resultObj.get("data"));
        }
        int errortype = Integer.parseInt((String) resultData.get("errortype"));
        if (errortype != 0){
            throw new AppException((Integer) resultData.get("errormessage"));
        }
        List<Map<String,String>> addition = (List<Map<String, String>>) resultData.get("addition-xml");
        Map<String,String> userObj = addition.get(0);
        sessionid = userObj.get("identify");
        redisUtils.set(sessionKey,sessionid,timeOut);
        return sessionid;
    }

    /**
     * @Menthod callHNS
     * @Desrciption 调用湖南省医保
     * @param hospCode 医院编码
     * @param insureRegCode 医保机构编码
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/2/1 14:19
     * @Return java.util.Map
     */
    public Map callHNS(String hospCode,String insureRegCode,Map<String,Object> param){
          //封装医保接口日志表数据
          Map<String, Object> params = new HashMap<>();
        try{
            params.put("hospCode", hospCode);
            // 定点医药机构编号
            params.put("medisCode", MapUtils.get(param,"oper_hospitalid"));
            // 医保中心编码
            params.put("regCode", MapUtils.get(param,"oper_centerid"));
            // 就医地医保区划
            params.put("mdtrtareaAdmvs", "0000");
            // 交易编号
            params.put("infno", MapUtils.get(param,"function_id"));
            params.put("msgId", StringUtils.createMsgId(MapUtils.get(param,"oper_hospitalid")));
            params.put("msgInfo", MapUtils.get(param,"function_id"));
            //表要求必填，目前接口都未传 ，直接传id
            params.put("msgName", MapUtils.get(param,"function_id"));
            params.put("crteId", MapUtils.get(param,"staff_id"));
            params.put("crteName", MapUtils.get(param,"staff_name"));
            params.put("visitId", MapUtils.get(param,"visitId"));
            params.put("isHospital", "0");

            toLoginHNS(hospCode,insureRegCode);
            InsureConfigurationDO insureConfigurationDO = toConfig(hospCode, insureRegCode);
            //请求参数
            Map<String, Object> parameters = new HashMap<String,Object>();
            parameters.put("FunctionID",param.get("function_id"));//功能号
            param.remove("function_id");
            param.put("hospital_id",insureConfigurationDO.getOrgCode());//医疗机构编码
            parameters.put("parameters",param);//参数值
            JSONObject paramObj = new JSONObject();
            paramObj.put("url",insureConfigurationDO.getUrl());
            paramObj.put("charset","gbk");//编码方式
            paramObj.put("param",HygeiaUtil.map2Xml("utf-8",parameters));//请求医保参数

            Map<String,Object> resultData = new HashMap<>();
            // 获取系统参数是直连医保还是走消息队列连接，默认消息队列
            SysParameterDTO sysParamDTO = this.getSysParamDTO(hospCode, "DIRECT_OR_QUEUE");
          //请求省工伤医保接口日志记录
          logger.info("医保业务功能号 {},请求参数-{}", param.get("function_id"), JSON.toJSONString(paramObj));
          if (sysParamDTO != null && StringUtils.isNotEmpty(sysParamDTO.getValue()) && "1".equals(sysParamDTO.getValue())) {
              // 直连医保
                logger.info("*****开始【湖南省医保调用】直连方法*****");
                logger.info("【湖南省医保调用】直连入参：" + JSON.toJSONString(paramObj));
                String paramMapJson = JSON.toJSONString(paramObj);
                params.put("paramMapJson", paramMapJson == null ? "null" : paramMapJson.length() > 4000 ? paramMapJson.substring(0, 4000) : paramMapJson);
                String doPost = HttpConnectUtil.doPost(paramObj);
                logger.info("【湖南省医保调用】直连返参字符串：" + doPost);
                logger.info("开始解析xml");
                resultData = HygeiaUtil.xml2map(doPost);
                String resultStr = JSONObject.toJSONString(resultData);
                params.put("resultStr", resultStr == null ? "null" : resultStr.length() > 4000 ? resultStr.substring(0, 4000) : resultStr);
                params.put("infcode", "1");
                logger.info("【湖南省医保调用】直连返参XML解析：" + resultData);
                logger.info("*****结束【湖南省医保调用】直连方法*****");
            } else {
                // 默认消息队列
                Map<String,Object> resultObj = new HashMap<>();
                Map<String,Object> httpParam = new HashMap<String,Object>();
                httpParam.put("attrCode",insureConfigurationDO.getAttrCode());
                String activityCode = SnowflakeUtils.getId();//交易号
                httpParam.put("activityCode",activityCode);
                httpParam.put("data", paramObj);
                String paramMapJson = JSON.toJSONString(httpParam);
                params.put("paramMapJson", paramMapJson == null ? "null" : paramMapJson.length() > 4000 ? paramMapJson.substring(0, 4000) : paramMapJson);
                resultObj = this.sendMessage(insureConfigurationDO.getRemark(),hospCode,httpParam,
                    activityCode);
                String resultStr = JSONObject.toJSONString(resultObj);
                params.put("resultStr", resultStr == null ? "null" : resultStr.length() > 4000 ? resultStr.substring(0, 4000) : resultStr);
                params.put("infcode", "1");
                Integer code = (Integer) resultObj.get("code");
                if (code < 0) {
                    params.put("infcode", "0");
                    throw new AppException((String) resultObj.get("msg"));
                }
                resultData = HygeiaUtil.xml2map((String) resultObj.get("data"));
              logger.info("调用工商医保出参数：{}",resultData);
            }
            int errortype = Integer.parseInt((String) resultData.get("errortype"));
            if (errortype != 0){
                throw new   AppException("【" + resultData.get("errorno") + "】" +  resultData.get("errormessage"));
            }
            return resultData;
        } catch (Exception e){
          //调接口后，请求失败插入医保人员信息获取日志
           params.put("resultStr", e.getMessage() == null ? "null" : e.getMessage().length() > 4000 ?
              e.getMessage().substring(0, 4000) : e.getMessage());
            logger.error("call insure HUNAN province error: ",e);
            throw new AppException("调用医保提示【"+e.getMessage()+"】",e);
        }finally {
          logger.info("*****调用省工伤接口保存日志记录开始*****");
          insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
          logger.info("*****调用省工伤接口保存日志记录结束*****");
        }
    }

    // 获取系统参数
    private SysParameterDTO getSysParamDTO(String hospCode, String code) {
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", hospCode);
        sysMap.put("code", code);
        return sysParameterService_consumer.getParameterByCode(sysMap).getData();
    }

    /**
     * @Menthod
     * @Desrciption 海南省电子凭证
     * @param hospCode 医院编码
     * @param insureRegCode 医保机构编码
     * @param url 请求地址
     * @param paramObj 入参参数
     * @Author Ou·Mr
     * @Date 2021/3/8 15:41
     * @return: null
     */
    public Map<String,Object> callHaiNan(String hospCode,String insureRegCode,String url,Map<String,Object> paramObj){
        try{
            InsureConfigurationDO insureConfigurationDO = toConfig(hospCode, insureRegCode);
            Map<String,Object> httpParam = new HashMap<String,Object>();
            String activityCode = SnowflakeUtils.getId();//交易号
            httpParam.put("activityCode",activityCode);
            httpParam.put("url",url);
            httpParam.put("param",paramObj);
            System.out.println(JSON.toJSONString(paramObj));
            Map<String,Object> httpResult = this.sendMessage(insureConfigurationDO.getRemark(),hospCode,httpParam,activityCode);
            Integer code = (Integer) httpResult.get("code");
            if (code < 0){
                throw new AppException((String) httpResult.get("msg"));
            }
            Map<String,Object> data = (Map<String, Object>) httpResult.get("data");
            if (data.containsKey(code) && 0 != (int)data.get("code")){
                throw new AppException((String) data.get("msg"));
            }
            return data;
        }catch (Exception e){
            throw new AppException("调用医保提示【"+e.getMessage()+"】");
        }
    }
    /**
     * @Description: 保存农合患者的住院信息到医院本地数据库
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/19 19:35
     * @Return
     */
    public Map<String,Object> saveNHPatientToLocal(String hospCode,String kafkaIp,String url,Map<String,Object> paramObj){
        try{
            Map<String,Object> httpParam = new HashMap<String,Object>();
            String activityCode = SnowflakeUtils.getId();//交易号
            httpParam.put("activityCode",activityCode);
            httpParam.put("url",url);
            httpParam.put("param",paramObj);
            Map<String,Object> httpResult = this.sendMessage(kafkaIp,hospCode,httpParam,activityCode);
            Integer code = (Integer) httpResult.get("code");
            if (code < 0){
                throw new AppException((String) httpResult.get("msg"));
            }
            return null;
        }catch (Exception e){
            throw new AppException("农合医保患者数据下发提示【"+e.getMessage()+"】");
        }
    }

    /**
     * @Menthod sendMessage
     * @Desrciption 发送消息
     * @param servers 消息服务地址
     * @param hospCode 医院编码
     * @param param 请求参数
     * @param activityCode 本次交易号
     * @Author Ou·Mr
     * @Date 2021/2/20 14:41
     * @return: String
     */
    public Map<String,Object> sendMessage(String servers, String hospCode, Map<String, Object> param, String activityCode) throws Exception {
        KafkaConsumer<String, String> consumer = null;
        try {
            // KAFKA医保
            logger.info("*****开始【医保调用】KAFKA方法*****");
            logger.info("【医保调用】入参：" + JSON.toJSONString(param));
            String producerTopic = hospCode + producerTopicKey;//生产者消息推送Topic
            String consumerTopic = hospCode + consumerTopicKey;//消费者消费信息Topic
            consumer = KafkaUtil.createConsumer(servers,consumerTopic);//消费处理结果消息
            KafkaProducer<String, String> producer = KafkaUtil.createProducer(servers);//推送需处理的消息
            KafkaUtil.sendMessage(producer, producerTopic, DesUtil.encrypt(JSON.toJSONString(param)).replaceAll("[\\s*\t\n\r]", ""));
            producer.close();
            int count = 0;
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    logger.error("call kafka consumer receive msg from server: {}",value);
                    if (StringUtils.isNotEmpty(value)){
                        if (value.indexOf("activityCode") == -1 || value.indexOf("code") == -1){
                            value = DesUtil.decrypt(value); }
                        Map<String,Object> result = JSON.parseObject(value,HashMap.class);
                        if (activityCode.equals(result.get("activityCode"))) {
                            consumer.commitAsync();
                            logger.info("【医保调用】KAFKA返参字符串：" + JSON.toJSONString(result));
                            logger.info("*****结束【医保调用】KAFKA方法*****");
                            return result;
                        }else{
                            logger.warn("发送的交易号：activityCode: {},接收的交易号：result.get('activityCode'){},不一致，完整数据包：{}",activityCode,result.get("activityCode"),JSON.toJSONString(result));
                        }
                    }
                }
                count ++ ;
                if (count > 500*3) {
                    throw new AppException("医保访问超时");
                }
            }
        } catch (Exception e){
            throw e;
        } finally {
            if (consumer != null){ consumer.close(); }
        }
    }
}