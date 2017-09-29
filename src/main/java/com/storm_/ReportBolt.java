package com.storm_;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class ReportBolt
        extends BaseRichBolt {

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    }

    public void execute(Tuple tuple) {
    //    System.out.println("report 调用");
    //    System.out.println(Thread.currentThread().getName());
        String word = tuple.getStringByField("word");
        int cnt = tuple.getIntegerByField("cnt").intValue();
        System.out.printf("%s\t%d\n", new Object[]{word, Integer.valueOf(cnt)});
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
