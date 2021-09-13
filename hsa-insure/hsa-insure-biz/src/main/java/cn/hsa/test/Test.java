package cn.hsa.test;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.DesUtil;
import cn.hsa.util.HygeiaUtil;
import cn.hsa.util.KafkaUtil;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.HashMap;
import java.util.Map;

public class Test {
    // private static final String XT_Url = "http://10.136.6.103:7011/PowerSICP/ProcessAll"; // 湘潭工伤
    // private static final String XT_Url =  "http://10.143.2.133:7001/Insur_HHYB/ProcessAll"; // 怀化医保
    // private static final String XT_Url = "http://10.143.2.204:7001/Insur_HHYB_Test/ProcessAll"; // 怀化医保测试
    // private static final String XT_Url = "http://10.137.8.151:7001/Insur_YYCJ/ProcessAll"; // 益阳
       private static final String XT_Url = "http://10.136.6.20:7001/Insur_YB/ProcessAll"; // 湖南省医保
    // private static final String XT_Url = "http://10.140.136.62:7001/Insur_CJTEST/ProcessAll";
    // private static final String XT_Url = "http://10.139.3.208:8889/powersicard_cs/ProcessAll"; //长沙市异地
    // private static final String XT_Url = "http://10.137.67.246:20001/hygeia_esb/api/call.action"; // 湘潭核三

    private static final String hospCode = "1000004";
    private static final String login_admin = "43990011077";
    private static final String login_password = "43990011077";
    private static final String insureRegCode = "43990011077";
    private static final String insureCenterId = "439900";
    private static final String service_url = "8.136.110.29:9092";

    private static final String XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "\n" +
            "<Program>\n" +
            "  <FunctionID>Remote_BIZC131252</FunctionID>\n" +
            "  <parameters>\n" +
            "    <input_staff>admin</input_staff>\n" +
            "    <indi_id>43000011100000344176</indi_id>\n" +
            "    <input_man>管理员</input_man>\n" +
            "    <biz_type>12</biz_type>\n" +
            "    <doctor_no/>\n" +
            "    <card_iden/>\n" +
            "    <oper_centerid>439900</oper_centerid>\n" +
            "    <hospital_id>43990011077</hospital_id>\n" +
            "    <recipe_no/>\n" +
            "    <oper_hospitalid>43990011077</oper_hospitalid>\n" +
            "    <oper_staffid>admin</oper_staffid>\n" +
            "    <doctor_name/>\n" +
            "    <serial_no>yd20210907194645</serial_no>\n" +
            "  </parameters>\n" +
            "  <feeinfo>\n" +
            "    <row1>\n" +
            "      <standard/>\n" +
            "      <his_item_code>XM00799</his_item_code>\n" +
            "      <item_code>N250302001_13</item_code>\n" +
            "      <factory>null</factory>\n" +
            "      <dosage>-1.0000</dosage>\n" +
            "      <opp_serial_fee>1435511547964674048</opp_serial_fee>\n" +
            "      <stat_type>0</stat_type>\n" +
            "      <drug_standard_code>null</drug_standard_code>\n" +
            "      <make_flag/>\n" +
            "      <item_name>葡萄糖测定（床边血糖仪检测）</item_name>\n" +
            "      <remark/>\n" +
            "      <usage_flag>0</usage_flag>\n" +
            "      <medi_item_type>0</medi_item_type>\n" +
            "      <usage_days/>\n" +
            "      <unit/>\n" +
            "      <money>-9.00</money>\n" +
            "      <price>9.0000</price>\n" +
            "      <fee_date>2021-09-08 15:52:46</fee_date>\n" +
            "      <his_item_name>葡萄糖测定（床旁血糖仪测定）</his_item_name>\n" +
            "      <model>null</model>\n" +
            "      <hos_serial>1437231779153010688</hos_serial>\n" +
            "    </row1>\n" +
            "    <row2>\n" +
            "      <standard>null</standard>\n" +
            "      <his_item_code>XM00799</his_item_code>\n" +
            "      <item_code>N250302001_13</item_code>\n" +
            "      <factory>null</factory>\n" +
            "      <dosage>-4.0000</dosage>\n" +
            "      <opp_serial_fee>1436725192357404672</opp_serial_fee>\n" +
            "      <stat_type>0</stat_type>\n" +
            "      <drug_standard_code>null</drug_standard_code>\n" +
            "      <make_flag/>\n" +
            "      <item_name>葡萄糖测定（床边血糖仪检测）</item_name>\n" +
            "      <remark/>\n" +
            "      <usage_flag>0</usage_flag>\n" +
            "      <medi_item_type>0</medi_item_type>\n" +
            "      <usage_days/>\n" +
            "      <unit/>\n" +
            "      <money>-36.00</money>\n" +
            "      <price>9.0000</price>\n" +
            "      <fee_date>2021-09-12 00:15:22</fee_date>\n" +
            "      <his_item_name>葡萄糖测定（床旁血糖仪测定）</his_item_name>\n" +
            "      <model>null</model>\n" +
            "      <hos_serial>1437213671373758464</hos_serial>\n" +
            "    </row2>\n" +
            "    <row3>\n" +
            "      <standard>null</standard>\n" +
            "      <his_item_code>XM00799</his_item_code>\n" +
            "      <item_code>N250302001_13</item_code>\n" +
            "      <factory>null</factory>\n" +
            "      <dosage>-4.0000</dosage>\n" +
            "      <opp_serial_fee>1436362831381487616</opp_serial_fee>\n" +
            "      <stat_type>0</stat_type>\n" +
            "      <drug_standard_code>null</drug_standard_code>\n" +
            "      <make_flag/>\n" +
            "      <item_name>葡萄糖测定（床边血糖仪检测）</item_name>\n" +
            "      <remark/>\n" +
            "      <usage_flag>0</usage_flag>\n" +
            "      <medi_item_type>0</medi_item_type>\n" +
            "      <usage_days/>\n" +
            "      <unit/>\n" +
            "      <money>-36.00</money>\n" +
            "      <price>9.0000</price>\n" +
            "      <fee_date>2021-09-11 00:15:24</fee_date>\n" +
            "      <his_item_name>葡萄糖测定（床旁血糖仪测定）</his_item_name>\n" +
            "      <model>null</model>\n" +
            "      <hos_serial>1437213734800023552</hos_serial>\n" +
            "    </row3>\n" +
            "    <row4>\n" +
            "      <standard>null</standard>\n" +
            "      <his_item_code>XM00799</his_item_code>\n" +
            "      <item_code>N250302001_13</item_code>\n" +
            "      <factory>null</factory>\n" +
            "      <dosage>-4.0000</dosage>\n" +
            "      <opp_serial_fee>1436096094404698112</opp_serial_fee>\n" +
            "      <stat_type>0</stat_type>\n" +
            "      <drug_standard_code>null</drug_standard_code>\n" +
            "      <make_flag/>\n" +
            "      <item_name>葡萄糖测定（床边血糖仪检测）</item_name>\n" +
            "      <remark/>\n" +
            "      <usage_flag>0</usage_flag>\n" +
            "      <medi_item_type>0</medi_item_type>\n" +
            "      <usage_days/>\n" +
            "      <unit/>\n" +
            "      <money>-36.00</money>\n" +
            "      <price>9.0000</price>\n" +
            "      <fee_date>2021-09-10 06:35:18</fee_date>\n" +
            "      <his_item_name>葡萄糖测定（床旁血糖仪测定）</his_item_name>\n" +
            "      <model>null</model>\n" +
            "      <hos_serial>1437213860318765056</hos_serial>\n" +
            "    </row4>\n" +
            "    <row5>\n" +
            "      <standard>null</standard>\n" +
            "      <his_item_code>XM00799</his_item_code>\n" +
            "      <item_code>N250302001_13</item_code>\n" +
            "      <factory>null</factory>\n" +
            "      <dosage>-4.0000</dosage>\n" +
            "      <opp_serial_fee>1435638084969943040</opp_serial_fee>\n" +
            "      <stat_type>0</stat_type>\n" +
            "      <drug_standard_code>null</drug_standard_code>\n" +
            "      <make_flag/>\n" +
            "      <item_name>葡萄糖测定（床边血糖仪检测）</item_name>\n" +
            "      <remark/>\n" +
            "      <usage_flag>0</usage_flag>\n" +
            "      <medi_item_type>0</medi_item_type>\n" +
            "      <usage_days/>\n" +
            "      <unit/>\n" +
            "      <money>-36.00</money>\n" +
            "      <price>9.0000</price>\n" +
            "      <fee_date>2021-09-09 00:15:24</fee_date>\n" +
            "      <his_item_name>葡萄糖测定（床旁血糖仪测定）</his_item_name>\n" +
            "      <model>null</model>\n" +
            "      <hos_serial>1437213941730205696</hos_serial>\n" +
            "    </row5>\n" +
            "  </feeinfo>\n" +
            "</Program>";

    public static void main (String args[]) throws Exception {


        Map<String,Object> httpParam = new HashMap();

        //InsureConfigurationDO insureConfigurationDO =  requestInsure.toConfig(hospCode,insureRegCode);

        httpParam.put("oper_hospitalid", insureRegCode);
        httpParam.put("oper_centerid", insureCenterId);
        httpParam.put("oper_staffid", "000");

        loginInsure_test(hospCode);

        Map<String,Object> map = get_down_insure_Info(httpParam);
 //       Map<String,Object> map = testYPPP();
        Map<String,Object> resultMap = toInsureInfo_test(hospCode,insureRegCode,map);
        //List<Map<String,Object>> arrayresultMap.get("data");
        if (1==1) {
            System.out.println("nice!");
        }



    }

    private static Map<String,Object> get_down_insure_Info(Map<String, Object> httpParam) {
        httpParam.put("function_id",Constant.HuNanSheng.MATCH.BIZC110118);
        String condition = "bs_catalog_match";
        httpParam.put("type", "version");
        httpParam.put("once_find", "0");
        httpParam.put("first_version_id","1");
        httpParam.put("last_version_id","1000000000");
        httpParam.put("first_row","1");
        httpParam.put("last_row", "2000");
        switch (condition) {
            case "bs_medi" :
                httpParam.put("condition", "bs_medi");
                break;
            case "bs_item" :
                httpParam.put("condition", "bs_item");
            case "bs_catalog_match":
                httpParam.put("condition", "bs_catalog_match");
            case "disease":
                //httpParam.put("function_id",Constant.HuNanSheng.MATCH.BIZC110125);
                break;
        }
        return httpParam;
    }

    private static Map<String,Object> testYPPP() {
        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("oper_staffid","000");//员工号
        httpParam.put("oper_centerid",insureCenterId);//中心编码
        httpParam.put("oper_hospitalid",insureRegCode);//医疗机构编码
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC110101);//功能号
        httpParam.put("center_id",insureCenterId);//医保中心编码
        httpParam.put("Indi_id","10198910");//个人电脑号
        httpParam.put("hospital_id",insureRegCode);//医疗机构编码
        httpParam.put("hosp_code","XM01016");//医院目录编码
        httpParam.put("match_type","2");//匹配类型
        httpParam.put("query_date",null);//费用发生时间
        httpParam.put("hospital_price",null);//单价
        httpParam.put("hospital_dosage",null);//数量
        httpParam.put("hospital_money",null);//金额
        httpParam.put("treatment_type","120");//待遇类型
        return httpParam;
    }


    private static Map<String,Object> get_GSKB_insure_Info(Map<String, Object> httpParam) {
        httpParam.put("function_id","BIZC580015");
        httpParam.put("type","indi_id");
        httpParam.put("value","629299");
        httpParam.put("corp_id","2475");
        return httpParam;
    }



    // 登录到平台
    private static void loginInsure_test(String hospCode) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("FunctionID", Constant.HuNanSheng.RESTS.LOGIN);//登录功能号
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("login_id",login_admin);
        parameters.put("login_password",login_password);
        param.put("parameters",parameters);//参数
        JSONObject paramObj = new JSONObject();
        paramObj.put("url",XT_Url);
        paramObj.put("charset","gbk");//编码方式
        paramObj.put("param", HygeiaUtil.map2Xml("utf-8",param));//请求医保参数
        //解析医保可识别的XML格式编码字符串
        Map<String,Object> httpParams = new HashMap<String,Object>();
        httpParams.put("attrCode","430000");
        String activityCode = SnowflakeUtils.getId();//交易号
        httpParams.put("activityCode",activityCode);
        httpParams.put("data", paramObj);
        Map<String,Object> resultObj = sendMessage_test(service_url,hospCode,httpParams,activityCode);
        Integer code = (Integer) resultObj.get("code");
        if (code < 0) {
            throw new AppException((String) resultObj.get("msg"));
        }
        Map<String,Object> resultData = HygeiaUtil.xml2map((String) resultObj.get("data"));
        int errortype = Integer.parseInt((String) resultData.get("errortype"));
        if (errortype != 0){
            throw new   AppException("【" + resultData.get("errorno") + "】" +  resultData.get("errormessage"));
        }
        System.out.print("成功登录到平台");
    }

    // 访问接口
    private static Map<String,Object> toInsureInfo_test(String hospCode,String insureRegCode,Map<String,Object> param) throws Exception {
        Map<String, Object> parameters = new HashMap<String,Object>();
        parameters.put("FunctionID",param.get("function_id"));//功能号
        param.remove("function_id");
        param.put("hospital_id",insureRegCode);//医疗机构编码
        parameters.put("parameters",param);//参数值
        JSONObject paramObj = new JSONObject();
        paramObj.put("url",XT_Url);
        paramObj.put("charset","gbk");//编码方式
        //paramObj.put("param",HygeiaUtil.map2Xml("utf-8",parameters));//请求医保参数
        paramObj.put("param", XML);//请求医保参数
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("attrCode","430000");
        String activityCode = SnowflakeUtils.getId();//交易号
        httpParam.put("activityCode",activityCode);
        httpParam.put("data", paramObj);
        Map<String,Object> resultObj = sendMessage_test(service_url,hospCode,httpParam,activityCode);
        Integer code = (Integer) resultObj.get("code");
        if (code < 0) {
            throw new AppException((String) resultObj.get("msg"));
        }
        Map<String,Object> resultData = HygeiaUtil.xml2map((String) resultObj.get("data"));
        int errortype = Integer.parseInt((String) resultData.get("errortype"));
        if (errortype != 0){
            throw new   AppException("【" + resultData.get("errorno") + "】" +  resultData.get("errormessage"));
        }
        return resultData;
    }


    // 发送Message
    private static Map<String,Object> sendMessage_test(String servers,String hospCode,Map<String,Object> param,String activityCode) throws Exception {
        String producerTopicKey = "_insure_producer";
        String consumerTopicKey = "_insure_consumer";
        KafkaConsumer<String, String> consumer = null;
        try {
            String producerTopic = hospCode + producerTopicKey;//生产者消息推送Topic
            String consumerTopic = hospCode + consumerTopicKey;//消费者消费信息Topic
            consumer = KafkaUtil.createConsumer(servers,consumerTopic);//消费处理结果消息
            KafkaProducer<String, String> producer = KafkaUtil.createProducer(servers);//推送需处理的消息
            KafkaUtil.sendMessage(producer, producerTopic, DesUtil.encrypt(JSON.toJSONString(param)).replaceAll("[\\s*\t\n\r]", ""));
            producer.close();
            int errorCount = 0;
            int count = 0;
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    if (StringUtils.isNotEmpty(value)){
                        if (value.indexOf("activityCode") == -1 || value.indexOf("code") == -1){ value = DesUtil.decrypt(value); }
                        Map<String,Object> result = JSON.parseObject(value,HashMap.class);
                        if (activityCode.equals(result.get("activityCode"))){
                            //consumer.commitAsync();

                            consumer.commitSync();
                            return result;
                        }
                    }

                    System.out.println(errorCount ++);
                }
                count ++ ;
                if (count > 300) {
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
