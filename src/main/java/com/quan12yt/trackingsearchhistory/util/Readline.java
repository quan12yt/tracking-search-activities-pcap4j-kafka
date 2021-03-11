package com.quan12yt.trackingsearchhistory.util;

import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import com.quan12yt.trackingsearchhistory.service.ProxyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
public class Readline {

    @Autowired
    KafkaTemplate<String, SearchRecord> kafkaTemplate;
    final String topic = "tracking-topic";
    boolean previousWasR = false;

    public String readLine(Socket socket) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int next;
        readerLoop:
        while ((next = socket.getInputStream().read()) != -1) {
            if (previousWasR && next == '\n') {
                previousWasR = false;
                continue;
            }
            previousWasR = false;
            switch (next) {
                case '\r':
                    previousWasR = true;
                    break readerLoop;
                case '\n':
                    break readerLoop;
                default:
                    byteArrayOutputStream.write(next);
                    break;
            }
        }
        return byteArrayOutputStream.toString("ISO-8859-1");
    }

    public void run() {
        try (
                ServerSocket serverSocket = new ServerSocket(9999)) {
                    Socket socket;
                    try {
                        while ((socket = serverSocket.accept()) != null) {
                            kafkaTemplate.send(topic, new SearchRecord(LocalDateTime.now().toString(), readLine(socket)));
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
