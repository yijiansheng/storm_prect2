package com.stormkafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.TopologyBuilder;

public class TKafkaTopo {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("KafkaSpout", TKafkaSpout.initKafkaSpout(), Integer.valueOf(1));
        builder.setBolt("KafkaBolt", new TkafkaBolt(), Integer.valueOf(1)).shuffleGrouping("KafkaSpout");
        Config config = new Config();
        config.setDebug(false);
        config.put("storm.zookeeper.session.timeout", Integer.valueOf(400000));
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("tkafka-topology", config, builder.createTopology());
    }
}
