package com.storm_refer;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Random;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class RVideoKfkProducer
        extends Thread {
    public static final Integer Users = Integer.valueOf(5);
    public static final Integer Videos = Integer.valueOf(3);

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
        while (true) {
            String vid = "v" + new Random().nextInt(Videos.intValue());
            String uid = "u" + new Random().nextInt(Users.intValue());
            producer.send(new ProducerRecord("topicA",
                    JSON.toJSONString(new RVideoEntity(vid, uid))), new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("写入成功，位置是: " + recordMetadata.offset());
                }
            });
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new RVideoKfkProducer().start();
    }
}
