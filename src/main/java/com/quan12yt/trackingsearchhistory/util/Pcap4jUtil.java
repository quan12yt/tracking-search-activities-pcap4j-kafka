package com.quan12yt.trackingsearchhistory.util;

import org.pcap4j.core.*;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class Pcap4jUtil {

    public PcapNetworkInterface getNetworkDevice(String ipAddress) throws UnknownHostException, PcapNativeException {
        InetAddress addr = InetAddress.getByName(ipAddress);
        return Pcaps.getDevByAddress(addr);
    }
}