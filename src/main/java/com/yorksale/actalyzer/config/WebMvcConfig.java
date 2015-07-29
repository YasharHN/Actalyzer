package com.yorksale.actalyzer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.yorksale.actalyzer.controller"})
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
}