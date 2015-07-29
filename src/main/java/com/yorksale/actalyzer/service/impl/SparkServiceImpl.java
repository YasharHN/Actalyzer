package com.yorksale.actalyzer.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yorksale.actalyzer.model.Activity;
import com.yorksale.actalyzer.service.SparkService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import scala.Tuple2;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
@Named("searchService")
public class SparkServiceImpl implements Serializable, SparkService {

    private static final String ROOT_DOC = "doc";
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

//    private JavaSparkContext sc;
//    private SQLContext sqlContext;

    @PostConstruct
    public void init() {

    }

    @PreDestroy
    public void close() {

    }

    public void processJson(String filePath) {
        SparkConf conf = new SparkConf().setAppName("com.yorksale.actalyzer").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);
        JavaRDD<Activity> activityRDD = sc.textFile(filePath).map(
                new Function<String, Activity>() {
                    @Override
                    public Activity call(String line) throws Exception {
                        JsonNode root = OBJ_MAPPER.readTree(line);
                        if (root.has(ROOT_DOC)) {
                            JsonNode doc = root.get(ROOT_DOC);
                            Activity activityDoc = OBJ_MAPPER.treeToValue(doc, Activity.class);
                            return activityDoc;
                        }
                        return null;
                    }
                });
        activityRDD = activityRDD.filter(new Function<Activity, Boolean>() {
            @Override
            public Boolean call(Activity activity) throws Exception {
                return (activity!=null);
            }
        });

        DataFrame dfActivity = sqlContext.createDataFrame(activityRDD, Activity.class);
        dfActivity.registerTempTable("activity");

        DataFrame dfIP = sqlContext.sql("SELECT ipAddress FROM activity WHERE language = 'zh-CN'");
        List<Activity> activities = dfIP.javaRDD().map(new Function<Row, Activity>() {

            public Activity call(Row row) {
                Activity activity = new Activity();
                if (row != null) {
                    //activity.setId(row.getString(0));
                    //activity.setDateTime(row.getDate(1));
                    activity.setIpAddress(row.getString(0));
                }
                return activity;
            }
        }).collect();

        for(Activity activity:activities){
            System.out.println(activity.getIpAddress());
        }

    }

}
