package com.yorksale.actalyzer.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yorksale.actalyzer.model.Activity;
import com.yorksale.actalyzer.model.DataRow;
import com.yorksale.actalyzer.service.SparkService;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.storage.StorageLevel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

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

    GraphDatabaseService graphDatabaseService;

    @PostConstruct
    public void init() {

    }

    @PreDestroy
    public void close() {
        graphDatabaseService.shutdown();
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
    }

    public List<DataRow> pieQuery(String query) {
        List<DataRow> list = new ArrayList<>();
        DataFrame df = sqlContext.sql(query);
        final String colLabel = "label";
        final String colValue = "c1";
        try{
            list= df.limit(10).javaRDD().map(new Function<Row, DataRow>() {
                @Override
                public DataRow call(Row row) throws Exception {
                    DataRow dataRow = new DataRow();
                    dataRow.setLabel((String) row.getAs(colLabel));
                    if(StringUtils.isEmpty(dataRow.getLabel())){
                        return null;
                    }
                    dataRow.setValue((Number) row.getAs(colValue));
                    return dataRow;
                }
            }).collect();

        } catch (Exception ex){
            LOG.error(ex.getMessage());
        }

        int i=1;
        list.removeAll(Collections.singleton(null));
        for(DataRow dataRow: list){
            dataRow.setPos(i++);
        }
        return list;
    }

    public List<DataRow> timeQuery(String query) {
        List<DataRow> list = new ArrayList<>();
        DataFrame df = sqlContext.sql(query);
        final String colLabel = "label";
        final String colLabel2 = "label2";
        final String colValue = "c1";
        try{
            list= df.javaRDD().map(new Function<Row, DataRow>() {
                @Override
                public DataRow call(Row row) throws Exception {
                    DataRow dataRow = new DataRow();
                    dataRow.setLabel((String) row.getAs(colLabel));
                    if(StringUtils.isEmpty(dataRow.getLabel())){
                        return null;
                    }
                    dataRow.setLabel2((Integer) row.getAs(colLabel2));
                    dataRow.setValue((Number) row.getAs(colValue));
                    return dataRow;
                }
            }).collect();

        } catch (Exception ex){
            LOG.error(ex.getMessage());
        }

        int i=1;
        list.removeAll(Collections.singleton(null));
        for(DataRow dataRow: list){
            dataRow.setPos(i++);
        }
        return list;
    }
}
