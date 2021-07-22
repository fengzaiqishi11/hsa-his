import cn.hsa.util.KafkaUtil;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @Package_name: PACKAGE_NAME
 * @Class_name: KafkaProducerTest
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2021/02/02 20:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class KafkaProducerTest {
    public static void main(String[] args) {
        // 在host中配置外网地址
        // 8.136.110.29	kafka
        String servers = "kafka:9092";
        String topic = "test";
        String message = "你好kafka-%d";

        KafkaProducer<String, String> producer = KafkaUtil.createProducer(servers);
        for (int i = 0; i < 100; i++) {
            KafkaUtil.sendMessage(producer, topic, String.format(message, i));
        }
        producer.close();
    }
}
