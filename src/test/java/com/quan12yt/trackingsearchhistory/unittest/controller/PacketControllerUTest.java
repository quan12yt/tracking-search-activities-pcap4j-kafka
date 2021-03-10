package com.quan12yt.trackingsearchhistory.unittest.controller;

import com.quan12yt.trackingsearchhistory.TrackingSearchHistoryApplication;
import com.quan12yt.trackingsearchhistory.exception.NetworkNotFoundException;
import com.quan12yt.trackingsearchhistory.service.PacketService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.UnknownHostException;

@SpringBootTest(classes = TrackingSearchHistoryApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PacketControllerUTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacketService packetService;

    @Before
    public void setup(){

    }

    @After
    public void clean(){

    }
//
//    @Test
//    public void wrongNetwork() throws PcapNativeException, NotOpenException, UnknownHostException {
//        Mockito.when(packetService.getPacket()).thenThrow(new NetworkNotFoundException("IpAddress not valid"));
//    }
}
