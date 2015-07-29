package com.ys.spring1.service.impl;

import com.ys.spring1.service.SearchService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: yashar
 * Date: 2014-03-09
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("searchService")
public class SearchServiceImpl implements SearchService {

    private Client client;

    @PostConstruct
    public void init() {
        try {
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", "es1")
                    .put("client.transport.sniff", true)
                    .build();

            client =  new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        } catch (Exception ex){
            //==
        }
    }

    @PreDestroy
    public void close() {
        client.close();
    }

    public IndexResponse store(String index, String type, String id, XContentBuilder builder){
        return client.prepareIndex(index, type, id)
                .setSource(builder)
                .execute()
                .actionGet();
    }

    public GetResponse get(String index, String type, String id){
         return client.prepareGet(index, type, id)
                .execute()
                .actionGet();
    }

    public DeleteResponse delete(String index, String type, String id){
        return client.prepareDelete(index, type, id)
                .execute()
                .actionGet();
    }

    public SearchResponse search(String index, String type, String queryString){
         return client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryString(queryString))             // Query
                //.setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))   // Filter
                .setFrom(0).setSize(20).setExplain(true)
                .execute()
                .actionGet();
    }
}
