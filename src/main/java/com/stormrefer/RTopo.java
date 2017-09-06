package com.stormrefer;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.TopologyBuilder;

public class RTopo {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder
                .setSpout("KafkaSpout", RVideoKfkSpout.initKafkaSpout(), Integer.valueOf(1));
        builder
                .setSpout("TimeSpout", new RTimeSpout(), Integer.valueOf(1));

        builder
                .setBolt("JsonBolt", new RJsonParseBolt(), Integer.valueOf(1))
                .shuffleGrouping("KafkaSpout");

        ((BoltDeclarer) builder.setBolt("UserBolt", new RUserBolt(), Integer.valueOf(1)).shuffleGrouping("JsonBolt"))
                .shuffleGrouping("TimeSpout");
        builder
                .setBolt("UserNumBolt", new RUserNumBolt(), Integer.valueOf(1))
                .shuffleGrouping("UserBolt");

        Config config = new Config();
        config.setDebug(false);
        config.put("storm.zookeeper.session.timeout", Integer.valueOf(400000));
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("rkafka-topo", config, builder.createTopology());
    }
}
