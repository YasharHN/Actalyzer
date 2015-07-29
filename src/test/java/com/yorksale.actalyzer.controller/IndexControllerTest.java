package com.yorksale.actalyzer.controller;

import com.yorksale.actalyzer.AppInitializer;
import com.yorksale.actalyzer.config.AppConfig;
import com.yorksale.actalyzer.config.WebMvcConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by Yashar HN
 * Date: 29/07/15 2:55 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, AppConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class IndexControllerTest {

    @Inject
    protected WebApplicationContext wac;

    @Inject
    protected ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testShowIndex() throws Exception {
        String result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertNotNull(result);
    }

    @Test
    public void testShowSample() throws Exception {
        mockMvc.perform(get("/sample"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("LOCAL_TIME").exists());
    }
}
