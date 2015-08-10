package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.model.DataRow;
import com.yorksale.actalyzer.model.QueryType;
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
        return new ModelAndView("pieChart");
    }

    @RequestMapping(value = "/spark/load", method = RequestMethod.GET)
    @ResponseBody
    public DataRow loadFile(@RequestParam(value = "path", required = true) String path) throws IOException {
        DataRow dr = new DataRow();
        try {
            sparkService.processJson(path);
            dr.setLabel("LOADED");
        } catch (Exception ex){
            dr.setLabel("ERROR: " + ex.getMessage());
        }
        return dr;
    }

    @RequestMapping(value = "/spark/query", method = RequestMethod.GET)
    @ResponseBody
    public List<DataRow> queryFile(
            @RequestParam(value = "q", required = true) String query,
            @RequestParam(value = "t", required = true) QueryType type
    ) throws IOException {
        List<DataRow> dataRows = null;
        if(type!=null){
            switch (type){
                case PIE:
                    dataRows = sparkService.pieQuery(query);
                    break;
                case TIME:
                    dataRows = sparkService.timeQuery(query);
                    break;
                default:
                    dataRows = sparkService.pieQuery(query);;
            }
        }
        return dataRows;
    }

    @RequestMapping(value = "/spark/pie-chart", method = RequestMethod.GET)
    public ModelAndView drawPieChart() throws IOException {
        return new ModelAndView("pieChart");
    }

    @RequestMapping(value = "/spark/time-chart", method = RequestMethod.GET)
    public ModelAndView drawTimeChart() throws IOException {
        return new ModelAndView("timeChart");
    }
}