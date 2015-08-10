package com.yorksale.actalyzer.service;


import com.yorksale.actalyzer.model.DataRow;

import java.util.List;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
public interface SparkService {

    void processJson(String filePath);

    List<DataRow> pieQuery(String query);

    List<DataRow> timeQuery(String query);
}
