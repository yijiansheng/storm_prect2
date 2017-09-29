package com.storm_kafka;

import kafka.api.OffsetRequest;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;

public class TKafkaSpout {
    public static final String TOPIC = "topicA";
    public static final String ZKINFO = "192.168.71.128:2181";

    public static KafkaSpout initKafkaSpout() {
        ZkHosts zkHosts = new ZkHosts("192.168.71.128:2181");

        SpoutConfig spoutConfig = new SpoutConfig(zkHosts, "topicA", "", "KafkaSpout");
        spoutConfig.startOffsetTime = OffsetRequest.LatestTime();

        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        return kafkaSpout;
    }
}
