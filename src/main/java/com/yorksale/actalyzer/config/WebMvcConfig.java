package com.yorksale.actalyzer.config;

import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.PostConstruct;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.yorksale.actalyzer.controller"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebMvcConfig.class);

    @PostConstruct
    public void init() {
        LOG.info("init() ...");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
        //jspViewResolver.setViewClass(JstlView.class);
        jspViewResolver.setPrefix("/view/");
        jspViewResolver.setSuffix(".jsp");
        jspViewResolver.setOrder(1);
        return jspViewResolver;
    }

//    @Bean
//    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
//        return new RequestMappingHandlerMapping();
//    }
//
//    @Bean
//    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
//        return new RequestMappingHandlerAdapter();
//    }
}