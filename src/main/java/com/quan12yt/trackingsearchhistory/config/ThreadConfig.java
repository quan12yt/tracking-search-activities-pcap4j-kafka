package com.quan12yt.trackingsearchhistory.config;

import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import org.springframework.kafka.core.KafkaTemplate;

public class ThreadConfig extends Thread {
    public ThreadConfig(String name) {
        super(name);
    }

    public void run(KafkaTemplate<String, SearchRecord> kafkaTemplate) {
        super.run();
    }
}
