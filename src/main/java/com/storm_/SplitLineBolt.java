package com.storm_;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SplitLineBolt extends BaseRichBolt {
    private OutputCollector outputCollector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    public void execute(Tuple tuple) {
//        System.out.println(Thread.currentThread().getName() + "split 调用");
        String line = tuple.getStringByField("line");
        System.out.println(line);
        String[] words = line.split(" ");
        for (String word : words) {
            word = word.trim();
            if (!word.equals("")) {
                //几个word就发几次
                this.outputCollector.emit(new Values(new Object[]{word}));
                //消息可靠性
                this.outputCollector.ack(tuple);
            }
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        /**
         * date:2017/9/29
         * description:注意，发标识的时候，field里面是字符串数组
         */
        outputFieldsDeclarer.declare(new Fields(new String[]{"word"}));
    }
}
