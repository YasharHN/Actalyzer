package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.service.SearchService;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * Created with IntelliJ IDEA.
 * User: yashar
 * Date: 2014-03-01
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class IndexController {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    SearchService searchService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return Calendar.getInstance().getTime().toString();
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> showSample() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("LOCAL_TIME", Calendar.getInstance().getTimeInMillis());
        map.put("GMT_TIME", Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis());
        map.put("JSON", objectMapper.writeValueAsString(map));
        return map;
    }

    @RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
    @ResponseBody
    public IndexResponse indexDoc(@PathVariable("id") String id) throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch" + new Date())
                .endObject();
        return searchService.store("twitter", "tweet", id, builder);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDoc(@PathVariable("id") String id) throws IOException {
        GetResponse getResponse = searchService.get("twitter", "tweet", id);
        return getResponse.getSource();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DeleteResponse deleteDoc(@PathVariable("id") String id) throws IOException {
        DeleteResponse deleteResponse = searchService.delete("twitter", "tweet", id);
        return deleteResponse;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> searchDoc(@RequestParam("q") String q) throws IOException {
        SearchResponse searchResponse = searchService.search("twitter", "tweet", q);
        List<Map<String, Object>> list = new ArrayList<>();
        if(searchResponse.getHits()!=null){
            for(SearchHit searchHit: searchResponse.getHits()){
                list.add(searchHit.getSource());
            }
        }
        return list;
    }
}