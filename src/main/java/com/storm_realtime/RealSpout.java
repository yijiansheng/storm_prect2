package com.storm_realtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * Author: changdalin
 * Date: 2017/12/13
 * Description:
 **/
public class RealSpout extends BaseRichSpout {
    SpoutOutputCollector _collector;
    List<String> data = new ArrayList<String>();
    int index = -1;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        _collector = spoutOutputCollector;
        //Vector
        data.add("1\t1\t9\t0.2");
        data.add("1\t4\t5\t0.2");
        data.add("1\t4\t6\t0.5");
        data.add("1\t3\t7\t0.1");
        data.add("2\t5\t10\t0.2");
        data.add("2\t5\t6\t0.1");
        data.add("2\t5\t7\t0.3");
    }

    //这个方法是一直在调用
    public void nextTuple() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        index = new Random().nextInt(7);
        if (index <= 6) {
            System.out.println(data.get(index));
            _collector.emit(new Values(data.get(index)));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("str"));
    }
}
