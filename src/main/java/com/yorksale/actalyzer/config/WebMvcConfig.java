package com.yorksale.actalyzer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: yashar
 * Date: 2014-03-01
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.ys.spring1.controller"})
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
}