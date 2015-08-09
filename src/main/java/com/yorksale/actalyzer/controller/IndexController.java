package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.model.DataRow;
import com.yorksale.actalyzer.service.SparkService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
    public List<DataRow> queryFile(@RequestParam(value = "q", required = true) String query) throws IOException {
        List<DataRow> dataRows = sparkService.queryJson(query);
        return dataRows;
    }

    @RequestMapping(value = "/spark/chart", method = RequestMethod.GET)
    public ModelAndView drawChart() throws IOException {
        return new ModelAndView("chart");
    }
}