//package com.quan12yt.trackingsearchhistory.controller;
//
//import com.quan12yt.trackingsearchhistory.exception.DataNotFoundException;
//import com.quan12yt.trackingsearchhistory.exception.StartServiceFailedException;
//import com.quan12yt.trackingsearchhistory.service.PacketService;
//import org.pcap4j.core.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.UnknownHostException;
//
//@RestController
//public class PacketController {
//
//    @Autowired
//    PacketService packetService;
//
//    @GetMapping("/do")
//    public ResponseEntity<?> getURL(@RequestParam String ipAddress) throws PcapNativeException, NotOpenException, UnknownHostException {
//        packetService.getPacket(ipAddress);
//        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
