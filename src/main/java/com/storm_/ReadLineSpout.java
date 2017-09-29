package com.storm_;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class ReadLineSpout
        extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Map conf;
    private final String[] lines = {
            "first second ok"
    };
    private ArrayList<String> arrayList;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.conf = map;
        this.arrayList = new ArrayList();
        for (String line : this.lines) {
            this.arrayList.add(line);
        }
    }

    public void nextTuple() {
        emitRandomAndContinue();
    }

    private void emitRandomAndContinue() {
        Utils.sleep(1000L);
        Random random = new Random();
        int id = random.nextInt();
        id = id < 0 ? -id : id;
        id %= this.arrayList.size();
        System.out.println("read_line_num : " + id);
        this.collector.emit(new Values(new String[]{this.arrayList.get(id)}));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields(new String[]{"line"}));
    }
}
