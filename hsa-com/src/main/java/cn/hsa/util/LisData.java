package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Component
public class LisData {
    /**
     * @Package_name: cn.hsa.insure.util
     * @Class_name: LisData
     * @Describe(描述): 医保请求公共方法
     * @Author: Ou·Mr
     * @Eamil: oubo@powersi.com.cn
     * @Date: 2020/10/29 14:21
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
        @Resource
        private RedisUtils redisUtils;

        private static int timeOut = 60 * 60 * 24 - 500;

        private static final String producerTopicKey = "_lis_producer";
        private static final String consumerTopicKey = "_lis_consumer";

    /**
     * @Description: 保存体检者信息到医院本地数据库
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
            Map<String,Object> httpResult = LisData.sendMessage(kafkaIp,hospCode,httpParam,activityCode);
            Integer code = (Integer) httpResult.get("code");
            if (code < 0){
                throw new AppException((String) httpResult.get("msg"));
            }
            return null;
        }catch (Exception e){
            throw new AppException("体检数据下发提示【"+e.getMessage()+"】");
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
    private static Map<String,Object> sendMessage(String servers,String hospCode,Map<String,Object> param,String activityCode) throws Exception {
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
                    if (StringUtils.isNotEmpty(value)){  // 111
                        if (value.indexOf("activityCode") == -1 || value.indexOf("code") == -1){ value = DesUtil.decrypt(value); }
                        System.out.println(JSON.parseObject(value,HashMap.class));
                        Map<String,Object> result = JSON.parseObject(value,HashMap.class);
                        if (activityCode.equals(result.get("activityCode"))){
                            consumer.commitAsync();
                            return result;
                        }
                    }
                    if (errorCount >= 100){
                        throw new AppException("消息队列处理超时，数据下发失败，请前往lis结果管理重新下发或者联系管理员。");
                    }
                    errorCount ++;
                }
                count ++ ;
                if (count > 500) {
                    throw new AppException("消息队列处理超时");
                }
            }
        } catch (Exception e){
            throw e;
        } finally {
            if (consumer != null){ consumer.close(); }
        }
    }
}
