package com.quan12yt.trackingsearchhistory;

import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import com.quan12yt.trackingsearchhistory.config.ProxyHandler;
import com.quan12yt.trackingsearchhistory.config.ThreadConfig;
import com.quan12yt.trackingsearchhistory.exception.NetworkNotFoundException;
import com.quan12yt.trackingsearchhistory.util.ProxyUtil;
import com.quan12yt.trackingsearchhistory.util.ValueConstant;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SocketProxyApplication extends ThreadConfig {

    public static void main(String[] args) {
        Map<String, Object> prop = new HashMap<>();
        prop.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ValueConstant.BOOTSTRAP_SERVER);
        prop.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, SearchRecord> pro = new DefaultKafkaProducerFactory<>(prop);
        KafkaTemplate<String, SearchRecord> kafkaTemplate = new KafkaTemplate<>(pro);
        (new SocketProxyApplication()).run(kafkaTemplate);
    }

    public SocketProxyApplication() {
        super("Server Thread");
    }

    @Override
    public void run(KafkaTemplate<String, SearchRecord> kafkaTemplate) {
        if (!ProxyUtil.isValid(ValueConstant.PROXY_PORT)){
            throw new NetworkNotFoundException("Proxy is unreachable ");
        }
        try (ServerSocket serverSocket = new ServerSocket(ValueConstant.PROXY_PORT)) {

            Socket socket;
            try {
                while ((socket = serverSocket.accept()) != null) {
                    (new ProxyHandler(socket, kafkaTemplate)).start();
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

