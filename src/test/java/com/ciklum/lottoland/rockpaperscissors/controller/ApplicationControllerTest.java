package com.ciklum.lottoland.rockpaperscissors.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @InjectMocks
    private ApplicationController applicationController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.applicationController).build();
    }

    @Test
    public void testMainAppIsLoadedWithBundle() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue("Expected index path", "index".equals(result.getModelAndView().getViewName()));
    }
}