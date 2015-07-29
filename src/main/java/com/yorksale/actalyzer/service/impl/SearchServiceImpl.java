package com.yorksale.actalyzer.service.impl;

import com.yorksale.actalyzer.service.SearchService;

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


    @PostConstruct
    public void init() {
        try {

        } catch (Exception ex){
            //==
        }
    }

    @PreDestroy
    public void close() {

    }

}
