package com.quan12yt.trackingsearchhistory.service;

import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import com.quan12yt.trackingsearchhistory.exception.NetworkNotFoundException;
import com.quan12yt.trackingsearchhistory.util.Pcap4jUtil;
import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Service
public class PacketService {

    @Autowired
    KafkaTemplate<String, SearchRecord> kafkaTemplate;
    final String topic = "tracking-topic";
    final String username = System.getProperty("user.name");
    @Autowired
    Pcap4jUtil pcap4jUtil;
//    final String ipAddress = "174.16.10.128";

    // send time and hostname of facebook activities to kafka consumer
    public void getPacket(String ipAddress) throws PcapNativeException, UnknownHostException, NotOpenException {
        PcapNetworkInterface device = pcap4jUtil.getNetworkDevice(ipAddress);
        if (device == null) {
            throw new NetworkNotFoundException("IpAddress not valid");
        }
        System.out.println("Device info: " + device);
        // Open the device and get a handle
        int snapshotLength = 65536; // in bytes
        int readTimeout = 10; // in milliseconds
        final PcapHandle handle;
        handle = device.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);
        // Set a filter to only listen for tcp packets on port 443 (HTTPS)
        String filter = "tcp port 443";
        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        // Create a listener that defines what to do with the received packets
        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(PcapPacket pcapPacket) {
                String ipDest = String.valueOf(pcapPacket.get(IpV4Packet.class).getHeader().getDstAddr());
                ipDest = ipDest.replaceAll("/", "");
                InetAddress addr = null;
                try {
                    addr = InetAddress.getByName(ipDest);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                String host = addr.getHostName();
                if (host.contains("facebook.com")) {
                    SearchRecord ms = new SearchRecord(username, LocalDateTime.now().toString(), host);
                    kafkaTemplate.send(topic, ms);
                }
            }
        };
        // Tell the handle to loop using the listener we created
        try {
            int maxPackets = -1;
            handle.loop(maxPackets, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handle.close();
    }
}
