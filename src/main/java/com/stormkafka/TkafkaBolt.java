package com.stormkafka;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class TkafkaBolt
        extends BaseRichBolt
{
    HashMap<String, Integer> dataMap = new HashMap();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {}

    public void execute(Tuple tuple)
    {
        String str = new String((byte[])tuple.getValueByField("bytes"));
        int count = 0;
        if (this.dataMap.get(str) == null)
        {
            this.dataMap.put(str, Integer.valueOf(count));
        }
        else
        {
            count = ((Integer)this.dataMap.get(str)).intValue();
            count += 1;
            this.dataMap.put(str, Integer.valueOf(count));
        }
        System.out.println(str + "   " + count);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {}
}
