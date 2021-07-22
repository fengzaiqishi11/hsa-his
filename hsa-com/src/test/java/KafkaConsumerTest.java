import cn.hsa.util.KafkaUtil;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @Package_name: PACKAGE_NAME
 * @Class_name: KafkaConsumerTest
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2021/02/02 20:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class KafkaConsumerTest {
    public static void main(String[] args) {
        // 在host中配置外网地址
        // 8.136.110.29	kafka
        String servers = "kafka:9092";
        String topic = "test";

        KafkaConsumer<String, String> consumer = KafkaUtil.createConsumer(servers, topic);
        KafkaUtil.readMessage(consumer, 100);
    }
}
