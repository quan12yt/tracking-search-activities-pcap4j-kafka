package com.quan12yt.trackingsearchhistory.service;

import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

@SpringBootApplication
public class Server extends Thread {

    public static void main(String[] args) {
        (new Server()).run();
    }

    public Server() {
        super("Server Thread");
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            Socket socket;
            try {
                while ((socket = serverSocket.accept()) != null) {
                    (new ProxyHandler(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();  // TODO: implement catch
            }
        } catch (IOException e) {
            e.printStackTrace();  // TODO: implement catch
            return;
        }
    }


}

