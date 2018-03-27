package com.storm_realtime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
    private Connection connection;
    private DateFormat dateFormat;
    //用户的行为特征评分信息
    private ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> userMemoryFeatureScore;
    //数据库里存储的用户的特征评分信息 有一个库存着，然后再更新
    private ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> userDbFeatureScore;
    //物品的特征矩阵
    private ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> itemFeatureScore;
    //10s内存 用户产生行为的物品集合
    private ConcurrentMap<Integer, ConcurrentMap<Integer, Integer>> userItemFilter;
    private String dateTimeStr = null;

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);
        return map;
    }

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        userMemoryFeatureScore = new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Double>>();
        userItemFilter = new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Integer>>();
        itemFeatureScore = new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Double>>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateTimeStr = dateFormat.format(new Date());
        Properties prop = new Properties();
        try {
//			mysql.driverClassName	mysql.url	mysql.username	mysql.password
            prop.load(TimingBolt.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            Class.forName(prop.getProperty("mysql.driverClassName"));
            connection = DriverManager.getConnection(prop.getProperty("mysql.url")
                    , prop.getProperty("mysql.username")
                    , prop.getProperty("mysql.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    public void execute(Tuple tuple) {
        //System.out.println(Thread.currentThread().getName() + "__TimingBolt调用");
        /**
         * date:2017/12/13
         * description:所有instance都会收到这个信号
         * 如果setParallelnum = 2的话，默认是两个对象
         *
         */
        if (!dateTimeStr.equals(dateFormat.format(new Date()))) {
            userMemoryFeatureScore.clear();
            itemFeatureScore.clear();
            userItemFilter.clear();
            //将当天的日期更新新的时间戳
            dateTimeStr = dateFormat.format(new Date());
        }
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
            try {
                statData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            userId = tuple.getIntegerByField("userId");
            itemId = tuple.getIntegerByField("itemId");
            feature = tuple.getIntegerByField("feature");
            score = tuple.getDoubleByField("score");
            // < userId  < pinlei0 , 3.0 > , < pinlei1 , 2.0 > .... >
            //放入内存
            GatherUtil.gatherUserData(userId, feature, score, userMemoryFeatureScore);
            // < userId  < productId0,1 > , < productId1,1 > >
            GatherUtil.gatherUserItemData(userId, itemId, userItemFilter);
        }
    }

    private void statData() throws Exception {
        try {
            /**
             * date:2017/12/14
             * description:内存中的userFeature + db原始值
             *
             */
            userDbFeatureScore = getUserFeatureFromDb();
            //循环所有user的 所有品类,重复的无所谓
            for (Integer _user : userMemoryFeatureScore.keySet()) {
                //db里没有的跳过
                ConcurrentMap<Integer, Double> oneUserScore = userDbFeatureScore.get(_user);
                if (oneUserScore != null) {
                    //循环这个user的所有品类
                    for (Integer _feature :  oneUserScore.keySet()) {
                        //UMF加上这一行score
                        //更新user_feature的score，进入内存
                        GatherUtil.gatherUserData(_user, _feature, oneUserScore.get(_feature), userMemoryFeatureScore);
                    }
                }
            }

            /**
             * date:2017/12/14
             * description:product 和 类别 的score
             */
            itemFeatureScore = getItemFeatureFromDb();



            /**
             * date:2017/12/15
             * description:从这个地方就有不同了，假设现在redis中有
             * 这里已经不需要用map来表示itemId，因为已经有了排好序的itemList
             * itemIdList已经生成，已经排序,[ item1,item2 .. ]
             * itemCF已经生成, < itemN,[ 0, 3.0 , 3.0 , 2.0...]  > 双list
             * userItemMatrix已经生成, < uN, [ 1.0,0.0,2.0....  ] >这样一个矩阵 双list
             * userTopMatrix已经生成, 本质上是一个list < uN,list[ 9,6,1,2.. ]  > 20个top 也是双list
             * 这些list都可以序列化，在spark任务中，写入reids,只要能找出来即可。可以以唯一索引做index
             * 很显然,实时进入的流量,可以对uNlist做实时的删除，如果有浏览过的item，就从list中删除
             * 这是第一个用处
             * 还有第二个用处
             * 但是，还会有新的流量进来，流量只是离线推荐的一个指标，但确是实时推荐的核心，比如给用户A推荐的是[1,2,3,4,5]
             * 用户A看了一个10，可以拿到30s内的用户，每一个用户看过了[i10,i2,i20]，这样更改userTopMatrix，
             * 先删除i2这些看过的，然后找到i10,i20的最相关的itemCF,写入list的前面，即实时推荐
             */


            /**
             * date:2017/12/14
             * description:循环10s内存
             */
            for (Integer user : userMemoryFeatureScore.keySet()) {
                //存储此用户的物品和得分
                ConcurrentMap<Integer, Double> itemTop = new ConcurrentHashMap<Integer, Double>();

                ConcurrentMap<Integer, Double> featureScoreForUser = userMemoryFeatureScore.get(user);

                //每一个series_id
                for (Integer item : itemFeatureScore.keySet()) {
                    //过滤已经浏览过的物品
                    if (userItemFilter.containsKey(user) && userItemFilter.get(user).containsKey(item)) {
                        continue;
                    }
                    ConcurrentMap<Integer, Double> featureScoreForItem = itemFeatureScore.get(item);
                    //根据该用户的每个特征值和值。 结合特征值和物品的权重计算物品的 推荐分数
                    double scoreSum = 0d;
                    for (Integer feature : featureScoreForUser.keySet()) {
                        //如果有此特征
                        if (featureScoreForItem.containsKey(feature)) {
                            scoreSum += featureScoreForUser.get(feature) * featureScoreForItem.get(feature);
                        }
                    }

                    //排序取出top10
                    itemTop.put(item, scoreSum);
                    if (itemTop.size() > 10) {
                        removeLittleItem(itemTop);
                    }
                }
                Statement statement = connection.createStatement();
                for (Integer item : itemTop.keySet()) {
                    if (itemTop.get(item) > 0) {
                        statement.execute(String.format("insert into t_user_item_top (user_id,item_id,score) values(%s,%s,%s); "
                                , user, item, itemTop.get(item)));
                    }
                }
                statement.close();


            }
            //将userFeatureScore里的db部分去除掉，避免下次再次读取的重复问题
            //组合的逆操作
            for (Integer _user : userMemoryFeatureScore.keySet()) {
                ConcurrentMap<Integer, Double> _featureScore = userDbFeatureScore.get(_user);
                if (_featureScore != null) {
                    for (Integer _feature : _featureScore.keySet()) {
                        GatherUtil.removeUserData(_user, _feature, _featureScore.get(_feature), userMemoryFeatureScore);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> getUserFeatureFromDb() throws Exception {
        ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> _userDbFeatureScore = new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Double>>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from t_user_feature");
        while (rs.next()) {
            if (userMemoryFeatureScore.containsKey(rs.getInt("u_id"))) {
                GatherUtil.gatherUserData(rs.getInt("u_id"), rs.getInt("feature_id"), rs.getDouble("score"), _userDbFeatureScore);
            }
        }
        statement.close();
        return _userDbFeatureScore;
    }

    private ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> getItemFeatureFromDb() throws Exception {
        ConcurrentMap<Integer, ConcurrentMap<Integer, Double>> _itemDbFeatureScore = new ConcurrentHashMap<Integer, ConcurrentMap<Integer, Double>>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from t_feature_item");
        while (rs.next()) {
            GatherUtil.gatherItemData(rs.getInt("item_id"), rs.getInt("feature_id"), rs.getDouble("weight"), _itemDbFeatureScore);
        }
        statement.close();
        return _itemDbFeatureScore;
    }

    private void removeLittleItem(ConcurrentMap<Integer, Double> top) {
        Integer littleIndex = 0;
        Double littleScore = 0d;
        for (Integer key : top.keySet()) {
            if (top.get(key) < littleScore) {
                littleIndex = key;
                littleScore = top.get(key);
            }
        }
        top.remove(littleIndex);
    }
}