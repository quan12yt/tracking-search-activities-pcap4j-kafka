package com.quan12yt.trackingsearchhistory.unittest.service;

import com.quan12yt.trackingsearchhistory.TrackingSearchHistoryApplication;
import com.quan12yt.trackingsearchhistory.service.PacketService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TrackingSearchHistoryApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PacketServiceUTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PacketService packetService;

    @Before
    public void setup(){
    }
    @After
    public void clean(){
    }

    @Test
    public void wrongNetwork(){
    }
}
