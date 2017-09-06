package com.stormrefer;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.storm.shade.org.apache.commons.collections.map.HashedMap;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class RUserNumBolt
        extends BaseRichBolt {
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    }

    public void execute(Tuple tuple) {
        if (tuple.getStringByField("msgType").equals("data2")) {
            List<String> watchList = (List) tuple.getValueByField("watchList");
            Map<String, Integer> watchMap = new HashedMap();
            for (String vid : watchList) {
                if (watchMap.get(vid) == null) {
                    watchMap.put(vid, Integer.valueOf(1));
                } else {
                    int count = ((Integer) watchMap.get(vid)).intValue();
                    count += 1;
                    watchMap.put(vid, Integer.valueOf(count));
                }
            }
            String maxVid = null;
            int maxNum = 0;
            Iterator<Map.Entry<String, Integer>> iterator = watchMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry) iterator.next();
                if (((Integer) entry.getValue()).intValue() > maxNum) {
                    maxNum = ((Integer) entry.getValue()).intValue();
                    maxVid = (String) entry.getKey();
                }
            }
            System.out.println("用户:" + tuple.getStringByField("userId") + "  video:" + maxVid + "  num:" + maxNum);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
