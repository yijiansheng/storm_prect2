package com.storm_;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class SecondTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        /**
         * date:2017/9/29
         * description:这个spout的threadNum暂时不能随意设置,
         */
        builder.setSpout("ReadLineSpout", new ReadLineSpout(), 1);

        /**
         * date:2017/9/29
         * description:task=3，是3个bolt对象，2是2个线程，一共加起来是两个线程，
         * 所以spout给出一个tuple，这个tuple会进入一个bolt,用一个线程执行,因为tuple一个算一个
         */
        builder.setBolt("SplitLineBolt", new SplitLineBolt(), 2).setNumTasks(5).shuffleGrouping("ReadLineSpout");
        //向下分给3个countBolt
        builder.setBolt("CountWordBolt", new CountWordBolt(), 3).setNumTasks(5).fieldsGrouping("SplitLineBolt", new Fields(new String[]{"word"}));
        //builder.setBolt("CountWordBolt", new CountWordBolt(), 1).setNumTasks(1).shuffleGrouping("SplitLineBolt");
        //builder.setBolt("ReportWordBolt", new ReportBolt(),2).setNumTasks(3).globalGrouping("CountWordBolt");
        builder.setBolt("ReportWordBolt", new ReportBolt(),3).setNumTasks(5).shuffleGrouping("CountWordBolt");
        Config config = new Config();
        config.setDebug(false);
        config.put("storm.zookeeper.session.timeout", 4000);
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("second-topology", config, builder.createTopology());
    }
}
