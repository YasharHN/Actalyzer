package com.yorksale.actalyzer.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
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

    @Bean(name = "graphDatabaseService")
    public GraphDatabaseService GraphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(
                "/home/yashar/development/testenv/test-tracking/neo4j.db" )
                .setConfig( GraphDatabaseSettings.read_only, "false" )
                .newGraphDatabase();
    }
}
