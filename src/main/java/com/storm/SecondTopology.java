package com.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class SecondTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("ReadLineSpout", new ReadLineSpout(), Integer.valueOf(1));
        builder.setBolt("SplitLineBolt", new SplitLineBolt(), Integer.valueOf(1)).shuffleGrouping("ReadLineSpout");
        builder.setBolt("CountWordBolt", new CountWordBolt(), Integer.valueOf(1)).fieldsGrouping("SplitLineBolt", new Fields(new String[]{"word"}));
        builder.setBolt("ReportWordBolt", new ReportBolt(), Integer.valueOf(1)).globalGrouping("CountWordBolt");
        Config config = new Config();
        config.setDebug(false);
        config.put("storm.zookeeper.session.timeout", Integer.valueOf(400000));
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("second-topology", config, builder.createTopology());
    }
}
