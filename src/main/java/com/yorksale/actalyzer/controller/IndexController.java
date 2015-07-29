package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.service.SearchService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
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
}