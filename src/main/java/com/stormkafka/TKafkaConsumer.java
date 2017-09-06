package com.stormkafka;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TKafkaConsumer
        extends Thread {
    private String topic;
    private long i;

    public TKafkaConsumer(String topic) {
        this.topic = topic;
    }

    private KafkaConsumer createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.71.128:9092");
        props.put("zookeeper.connect", "192.168.71.128:2181");
        props.put("group.id", "T_java_consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        return consumer;
    }

    final int minBatchSize = 20;

    public void run() {
        KafkaConsumer consumer = createConsumer();

        consumer.subscribe(Arrays.asList(new String[]{this.topic}));

        List<ConsumerRecord<String, String>> buffer = new ArrayList();
        for (; ; ) {
            ConsumerRecords<String, String> records = consumer.poll(1L);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println((String) record.value());
                buffer.add(record);
            }
            if (buffer.size() >= 20) {
                System.out.println("清空");
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    public static void main(String[] args) {
        new TKafkaConsumer("topicA").start();
    }
}
