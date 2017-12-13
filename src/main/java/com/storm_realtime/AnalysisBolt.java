package com.storm_realtime;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;


/**
 * Author: changdalin
 * Date: 2017/12/13
 * Description:
 **/
public class AnalysisBolt extends BaseRichBolt {

    private String[] lines;
    private Integer userId;
    private Integer itemId;
    private Integer feature;
    private Double score;
    private OutputCollector _collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        _collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        /**
         * date:2017/12/13
         * description:这个地方有调度问题,taskNum是多少个对象,parallel_num是多少线程来执行这个方法
         * 就相当于在某个thread里面，执行boltN.execute()
         */
//        System.out.println(
//                this.toString() + "___" +
//                        Thread.currentThread().getName() +
//                        "_Analysi调用");
        _collector.ack(tuple);
        //组装数据
        //userId	itemId	feature score
        lines = tuple.getStringByField("str").split("\t");
        try {
            userId = Integer.parseInt(lines[0]);
            itemId = Integer.parseInt(lines[1]);
            feature = Integer.parseInt(lines[2]);
            score = Double.parseDouble(lines[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (notNullOrZero(userId) && notNullOrZero(itemId)
                && notNullOrZero(feature) && score != null && score > 0) {
            //Values本质上就是ArrayList
            this._collector.emit(new Values(userId, itemId, feature, score));
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("userId", "itemId", "feature", "score"));
    }

    private boolean notNullOrZero(Integer number) {
        return number != null && number > 0;
    }

}
