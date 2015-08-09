package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.service.SparkService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
@Controller
public class IndexController {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    SparkService sparkService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return Calendar.getInstance().getTime().toString();
    }

    @RequestMapping(value = "/spark/load", method = RequestMethod.GET)
    @ResponseBody
    public String loadFile() throws IOException {
        sparkService.processJson("/Users/admin/Projects/big-data-course/project/data/t-data.json");
        return "Loaded";
    }

    @RequestMapping(value = "/spark/query", method = RequestMethod.GET)
    @ResponseBody
    public String queryFile() throws IOException {
        sparkService.queryJson("SELECT ipAddress FROM activity WHERE language = 'zh-CN'");
        return "Done";
    }

    @RequestMapping(value = "/spark/chart", method = RequestMethod.GET)
    public ModelAndView drawChart() throws IOException {
        return new ModelAndView("chart");
    }
}