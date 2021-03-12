package com.quan12yt.trackingsearchhistory.controller;

import com.quan12yt.trackingsearchhistory.config.ProxyHandler;
import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@RestController
public class SocketProxyController {

    @Autowired
    KafkaTemplate<String, SearchRecord> kafkaTemplate;

    @GetMapping("/exec")
    public void excecute(){

    }
}
