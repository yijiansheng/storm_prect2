package com.stormrefer;

import com.alibaba.fastjson.JSON;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class RJsonParseBolt
        extends BaseRichBolt {
    OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        String jVideo = new String((byte[]) tuple.getValueByField("bytes"));
        RVideoEntity temp = (RVideoEntity) JSON.parseObject(jVideo, RVideoEntity.class);
        this.collector.emit(new Values(new Object[]{"data", temp.getUserId(), temp.getVideoId()}));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(new String[]{"msgType", "userId", "videoId"}));
    }
}
