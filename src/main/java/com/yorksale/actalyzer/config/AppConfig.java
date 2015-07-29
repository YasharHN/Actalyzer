package com.yorksale.actalyzer.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: yashar
 * Date: 2014-03-01
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(basePackages = {"com.yorksale.actalyzer"}, excludeFilters = @ComponentScan.Filter(
        value= Controller.class,
        type = FilterType.ANNOTATION
))
@PropertySource("classpath:app.properties")
public class AppConfig {

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
