package com.stormkafka;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TKafkaProducer
        extends Thread {
    private String topic;
    private static final Map<Integer, String> map = new HashMap();
    final Random random = new Random();

    static {
        map.put(Integer.valueOf(0), "hello");
        map.put(Integer.valueOf(1), "word");
        map.put(Integer.valueOf(2), "world");
    }

    public TKafkaProducer(String topic) {
        this.topic = topic;
    }

    private KafkaProducer createProducer() {
        HashMap<String, Object> config = new HashMap();
        config.put("zookeeper.connect", "192.168.71.128:2181");

        config.put("bootstrap.servers", "192.168.71.128:9092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer(config);
    }

    public void run() {
        KafkaProducer producer = createProducer();
        for (; ; ) {
            producer.send(new ProducerRecord(this.topic, map

                    .get(Integer.valueOf(this.random.nextInt(3)))), new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("写入的位置是: " + recordMetadata.offset());
                }
            });
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new TKafkaProducer("topicA").start();
    }
}
