package com.yorksale.actalyzer.service.impl;

import com.yorksale.actalyzer.service.SearchService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
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
