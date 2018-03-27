package com.storm_realtime;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


/**
 * Author: changdalin
 * Date: 2017/12/13
 * Description:
 **/
public class RealTopo {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder
                .setSpout("RealSpout", new RealSpout(), 1);
        builder.
                setBolt("AnalysisBolt", new AnalysisBolt(), 1).shuffleGrouping("RealSpout");
        //这里默认是两个对象
        builder.
                setBolt("TimingBolt", new TimingBolt(), 2).shuffleGrouping("AnalysisBolt");
        Config config = new Config();
        config.setDebug(false);
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("real-topo", config, builder.createTopology());
    }
}
