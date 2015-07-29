package com.yorksale.actalyzer.service;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: yashar
 * Date: 2014-03-09
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SearchService {

    IndexResponse store(String index, String type, String id, XContentBuilder builder);

    GetResponse get(String index, String type, String id);

    DeleteResponse delete(String index, String type, String id);

    SearchResponse search(String index, String type, String queryString);
}
