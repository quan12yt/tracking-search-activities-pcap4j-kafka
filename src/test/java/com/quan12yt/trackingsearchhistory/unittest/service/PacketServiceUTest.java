package com.quan12yt.trackingsearchhistory.unittest.service;

import com.quan12yt.trackingsearchhistory.TrackingSearchHistoryApplication;
import com.quan12yt.trackingsearchhistory.exception.NetworkNotFoundException;
import com.quan12yt.trackingsearchhistory.service.PacketService;
import com.quan12yt.trackingsearchhistory.util.Pcap4jUtil;
import org.assertj.core.condition.AnyOf;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.pcap4j.Pcap4jPropertiesLoader;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.UnknownHostException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest(classes = TrackingSearchHistoryApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PacketServiceUTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PacketService packetService;
    @MockBean
    Pcap4jUtil pcap4jUtil;

    @Test
    public void unknownNetwork() throws PcapNativeException, NotOpenException, UnknownHostException {
        try {
            packetService.getPacket("213.213.523.123");
        }catch (Exception e)
        {
            Assert.assertEquals(e, UnknownHostException.class);
            assertTrue(e.getMessage().contains("No such host is known"));
        }
    }

    @Test
    public void getPacketSucceed() throws PcapNativeException, NotOpenException, UnknownHostException {
        try {
            packetService.getPacket("174.16.10.128");
        }catch (Exception e)
        {
            fail("No such host is known");
        }
    }

    @Test
    public void wrongNetwork() throws PcapNativeException, NotOpenException, UnknownHostException {
        Mockito.when(pcap4jUtil.getNetworkDevice(anyString())).thenReturn(null);
       try {
           packetService.getPacket("213.213.523.123");
       }catch (Exception e)
       {
           Assert.assertEquals(e, NetworkNotFoundException.class);
           assertTrue(e.getMessage().contains("IpAddress not valid"));
       }
    }

}
