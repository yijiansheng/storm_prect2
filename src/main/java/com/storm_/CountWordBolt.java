package com.storm_;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class CountWordBolt
        extends BaseRichBolt {
    private OutputCollector outputCollector;
    private HashMap<String, Integer> countWords;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
        this.countWords = new HashMap();
    }

//    public CountWordBolt() {
//        System.out.println("countbolt初始化");
//    }

    public void execute(Tuple tuple) {
        //    System.out.println("count 调用");
        String word = tuple.getStringByField("word");
        //    System.out.println(word);
        int cnt = 1;
        if (this.countWords.containsKey(word)) {
            cnt = (this.countWords.get(word)).intValue() + 1;
        }
        this.countWords.put(word, Integer.valueOf(cnt));
        //     System.out.println(countWords);
        this.outputCollector.emit(new Values(new Object[]{word, Integer.valueOf(cnt)}));
        //消息可靠性
        this.outputCollector.ack(tuple);
    }

    /**
     * date:2017/9/29
     * description:可以发map的
     */
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(new String[]{"word", "cnt"}));
    }
}
