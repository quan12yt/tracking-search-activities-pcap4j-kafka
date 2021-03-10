package com.quan12yt.trackingsearchhistory.controller;

import com.quan12yt.trackingsearchhistory.exception.StartServiceFailedException;
import com.quan12yt.trackingsearchhistory.service.PacketService;
import org.pcap4j.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
public class PacketController {

    @Autowired
    PacketService packetService;

    @GetMapping("/do")
    public String getURL() throws PcapNativeException, NotOpenException, UnknownHostException {
        try {
            packetService.getPacket();
            return "Start recording web visited service ...";
        }catch(Exception e) {
           throw  new StartServiceFailedException("Cant start recording service !!");
        }
    }
}
