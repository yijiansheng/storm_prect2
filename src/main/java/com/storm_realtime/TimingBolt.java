package com.storm_realtime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

/**
 * Author: changdalin
 * Date: 2017/12/13
 * Description:storm的定时机制，能让这个bolt的任何task，每隔一段时间收到一个信号
 **/
public class TimingBolt extends BaseRichBolt {
    private Integer userId;
    private Integer itemId;
    private Integer feature;
    private Double score;
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);
        return map;
    }

    public void execute(Tuple tuple) {
        //System.out.println(Thread.currentThread().getName() + "__TimingBolt调用");
        /**
         * date:2017/12/13
         * description:所有instance都会收到这个信号
         * 如果setParallelnum = 2的话，默认是两个对象
         *
         */
        if (tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)) {
            /**
             * date:2017/12/13
             * description:这个tuple是系统发的，与spout的输入没有关系，
             * 如果，这个if{}里面执行时间过长，那么spout的输入tuple会阻塞
             * 然后等线程空闲了，再继续向下执行,tuple不会丢失
             */
            //Integer userId = tuple.getIntegerByField("userId");
            //System.out.println(Thread.currentThread().getName() + "__tick");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + "__tickEnd");
//            System.out.println(this.toString() + "__" + Thread.currentThread().getName() + "收到信号并且执行");
        } else {
            userId = tuple.getIntegerByField("userId");
            itemId = tuple.getIntegerByField("itemId");
            feature = tuple.getIntegerByField("feature");
            score = tuple.getDoubleByField("score");
            System.out.println(Thread.currentThread().getName() + "   " + score);
        }
    }

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
