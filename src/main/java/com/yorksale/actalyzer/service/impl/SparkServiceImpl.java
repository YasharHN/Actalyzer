package com.yorksale.actalyzer.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yorksale.actalyzer.model.Activity;
import com.yorksale.actalyzer.service.SparkService;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.storage.StorageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.count;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
@Named("searchService")
public class SparkServiceImpl implements Serializable, SparkService {

    private static final Logger LOG = LoggerFactory.getLogger(SparkServiceImpl.class);
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private static final String ROOT_DOC = "doc";

    private static JavaSparkContext sparkContext;
    private static SQLContext sqlContext;

    static {
//        try {
            SparkConf conf = new SparkConf()
                    .setAppName("com.yorksale.actalyzer")
                    .setMaster("local");
            sparkContext = new JavaSparkContext(conf);
            sqlContext = new SQLContext(sparkContext);
//        } catch (ClassNotFoundException e) {
//            LOG.error("JavaSparkContext is failed");
//        }

    }
//    private JavaSparkContext sc;
//    private SQLContext sqlContext;

    @PostConstruct
    public void init() {

    }

    @PreDestroy
    public void close() {

    }

    public void processJson(String filePath) {
        sparkContext.cancelAllJobs();
        sparkContext.clearCallSite();
        sparkContext.clearJobGroup();
        JavaRDD<Activity> rddActivity = sparkContext.textFile(filePath).map(
                new Function<String, Activity>() {
                    @Override
                    public Activity call(String line) throws Exception {
                        JsonNode root = OBJ_MAPPER.readTree(line);
                        if (root.has(ROOT_DOC)) {
                            JsonNode doc = root.get(ROOT_DOC);
                            try {
                                Activity activityDoc = OBJ_MAPPER.treeToValue(doc, Activity.class);
                                return activityDoc;
                            } catch (Exception ex){
                                LOG.error("",ex);
                            }
                        }
                        return null;
                    }
                })
                .filter(new Function<Activity, Boolean>() {
                    @Override
                    public Boolean call(Activity activity) throws Exception {
                        return (activity!=null);
                    }
                });

        rddActivity.persist(StorageLevel.DISK_ONLY());

        sqlContext.clearCache();
        DataFrame dfActivity = sqlContext.createDataFrame(rddActivity, Activity.class);
        dfActivity.registerTempTable("activity");
        dfActivity.persist(StorageLevel.DISK_ONLY());
        sqlContext.cacheTable("activity");
//        dfActivity.select("id", "appId", "dateTime", "sessionId", "ipAddress", "type", "userAgent",
//                "categoryId", "categoryName", "keywords", "companyID", "companyName", "referrer", "username",
//                "source", "language", "pageTitle", "productName", "mobileBrowser", "targetURL", "topic", "sector")
//                .write().format("json")
//                .save("/home/yashar/development/testenv/test-tracking/output.json");
        //System.out.println("Result: " + dfActivity.count());
    }

    public void queryJson(String query) {
        DataFrame dfIP = sqlContext.sql("SELECT ipAddress, count(1) FROM activity group by ipAddress order by c1 desc");

        dfIP.show(10);

//        List<Row> rows = dfIP.javaRDD().map(new Function<Row, Activity>() {
//            public Row call(Row row) {
//                System.out.println(row.toString());
//                return ro
//            }
//        }).collect();

//        for(Activity activity:activities){
//            System.out.println(activity.getIpAddress());
//        }
    }

}
