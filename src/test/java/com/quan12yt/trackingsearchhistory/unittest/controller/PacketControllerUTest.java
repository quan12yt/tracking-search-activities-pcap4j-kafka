//package com.quan12yt.trackingsearchhistory.unittest.controller;
//
//import com.quan12yt.trackingsearchhistory.TrackingSearchHistoryApplication;
//import com.quan12yt.trackingsearchhistory.exception.NetworkNotFoundException;
//import com.quan12yt.trackingsearchhistory.service.PacketService;
//import org.hamcrest.CoreMatchers;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.pcap4j.core.NotOpenException;
//import org.pcap4j.core.PcapNativeException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.net.UnknownHostException;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest(classes = TrackingSearchHistoryApplication.class)
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//public class PacketControllerUTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PacketService packetService;
//
//    @Test
//    public void unknownNetwork() throws PcapNativeException, NotOpenException, UnknownHostException {
//        try {
//            packetService.getPacket("213.213.523.123");
//        }catch (Exception e)
//        {
//            Assert.assertEquals(e, UnknownHostException.class);
//            assertTrue(e.getMessage().contains("No such host is known"));
//        }
//        when(packetService.getPacket("213.213.523.123")).thenThrow( new NetworkNotFoundException());
//
//        mockMvc.perform(get("/emails/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath
//                        ("$.message", CoreMatchers.is("Must contains 2 emails")));
//    }
//
//    @Test
//    public void getPacketSucceed() throws PcapNativeException, NotOpenException, UnknownHostException {
//        try {
//            packetService.getPacket("174.16.10.128");
//        }catch (Exception e)
//        {
//            fail("No such host is known");
//        }
//    }
//
//    @Test
//    public void wrongNetwork() throws PcapNativeException, NotOpenException, UnknownHostException {
//        try {
//            packetService.getPacket("213.213.523.123");
//        }catch (Exception e)
//        {
//            Assert.assertEquals(e, NetworkNotFoundException.class);
//            assertTrue(e.getMessage().contains("IpAddress not valid"));
//        }
//    }
//}
