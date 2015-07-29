package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.service.SparkService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

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

    @RequestMapping(value = "/spark", method = RequestMethod.GET)
    @ResponseBody
    public String showSample() throws IOException {
        sparkService.processJson("/home/yashar/development/testenv/test-tracking/t-data.json");
        return "Done";
    }
}