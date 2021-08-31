package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: KafkaUtil
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2021/02/03 11:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class KafkaUtil {
    /**
     * @Method 创建kafka消息队列消费者
     * @Description
     *
     * @Param servers 消息服务IP:PORT
     * @Param topic 消息服务消费主题
     *
     * @Author zhongming
     * @Date 2021/2/3 12:56
     * @Return
     **/
    public static KafkaConsumer<String, String> createConsumer(String servers, String topic) {
        try {
            Properties properties = new Properties();
            properties.put("bootstrap.servers", servers);
            properties.put("group.id", "group-"+topic);
            properties.put("enable.auto.commit", "false");
            properties.put("auto.commit.interval.ms", "1000");
            properties.put("auto.offset.reset", "earliest");
            properties.put("session.timeout.ms", "30000");
            properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
            kafkaConsumer.subscribe(Arrays.asList(topic));
            return kafkaConsumer;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method kafka消息队列消费者样例
     * @Description
     *
     * @Param kafkaConsumer kafka消息队列消费者
     * @Param timeout 拉取超时时间：MS
     *
     * @Author zhongming
     * @Date 2021/2/3 12:56
     * @Return
     **/
    public static void readMessage(KafkaConsumer<String, String> kafkaConsumer, int timeout) {
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(timeout));
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    System.out.println(value);
                }
                kafkaConsumer.commitAsync();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        } finally {
            try {
                kafkaConsumer.commitSync();
            } finally {
                kafkaConsumer.close();
            }
        }
    }

    /**
     * @Method 创建kafka消息队列生产者
     * @Description
     *
     * @Param servers 消息服务IP:PORT
     *
     * @Author zhongming
     * @Date 2021/2/3 12:56
     * @Return
     **/
    public static KafkaProducer<String, String> createProducer(String servers) {
        try {
            Properties properties = new Properties();
            properties.put("bootstrap.servers", servers);
            properties.put("acks", "all");
            properties.put("retries", 0);
            properties.put("batch.size", 16384);
            properties.put("linger.ms", 1);
            properties.put("buffer.memory", 33554432);
            properties.put("compression.type", "gzip");
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            return new KafkaProducer<>(properties);
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method kafka消息队列生产者
     * @Description
     *
     * @Param producer kafka消息队列生产者
     * @Param topic 消息服务消费主题
     * @Param message 业务数据（JSON格式）
     *
     * @Author zhongming
     * @Date 2021/2/3 12:56
     * @Return
     **/
    public static void sendMessage(KafkaProducer<String, String> producer, String topic, String message) {
        try {
            producer.send(new ProducerRecord<>(topic, message));
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }
}
