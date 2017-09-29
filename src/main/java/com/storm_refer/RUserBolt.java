package com.storm_refer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class RUserBolt
        extends BaseRichBolt {
    OutputCollector collector;
    ConcurrentHashMap<String, List<String>> userMap = new ConcurrentHashMap();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        if (tuple.getStringByField("msgType").equals("clean")) {
            this.userMap.clear();
            System.out.println("清空userMap");
        }
        if (tuple.getStringByField("msgType").equals("data")) {
            String userId = tuple.getStringByField("userId");
            String videoId = tuple.getStringByField("videoId");
            List<String> watchList;
            if (null == this.userMap.get(userId)) {
                watchList = new ArrayList();
            } else {
                watchList = (List) this.userMap.get(userId);
            }
            watchList.add(videoId);
            this.userMap.put(userId, watchList);
            this.collector.emit(new Values(new Object[]{"data2", userId, watchList}));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(new String[]{"msgType", "userId", "watchList"}));
    }
}
