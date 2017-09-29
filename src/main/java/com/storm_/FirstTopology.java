package com.storm_;

import java.util.ArrayList;
import java.util.Map;

import org.apache.storm.StormSubmitter;
import org.apache.storm.shade.org.json.simple.JSONValue;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.NimbusClient;
import org.apache.storm.utils.Utils;

public class FirstTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("ReadLineSpout", new ReadLineSpout(), Integer.valueOf(1));
        builder.setBolt("SplitLineBolt", new SplitLineBolt(), Integer.valueOf(1)).shuffleGrouping("ReadLineSpout");
        builder.setBolt("CountWordBolt", new CountWordBolt(), Integer.valueOf(1)).fieldsGrouping("SplitLineBolt", new Fields(new String[]{"word"}));
        builder.setBolt("ReportWordBolt", new ReportBolt(), Integer.valueOf(1)).globalGrouping("CountWordBolt");
        try {
            Map stormConf = Utils.readStormConfig();

            ArrayList<String> nimbusList = new ArrayList();
            nimbusList.add("192.168.71.128");

            stormConf.put("nimbus.seeds", nimbusList);

            stormConf.put("nimbus.thrift.port", Integer.valueOf(6627));
            System.out.println(stormConf);
            String inputJar = "D:\\bigdata\\storm\\out\\artifacts\\storm_jar\\storm.jar";

            String uploadedJarLocation = StormSubmitter.submitJar(stormConf, inputJar);
            System.out.println("uploadedJarLocation:" + uploadedJarLocation);
            String jsonConf = JSONValue.toJSONString(stormConf);
            NimbusClient nimbus = new NimbusClient(stormConf, "192.168.71.128", 6627);
            nimbus.getClient().submitTopology("First-Topology-Test", uploadedJarLocation, jsonConf, builder
                    .createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
