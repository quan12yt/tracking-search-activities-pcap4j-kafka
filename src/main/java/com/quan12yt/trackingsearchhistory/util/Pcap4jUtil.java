package com.quan12yt.trackingsearchhistory.util;

import org.pcap4j.core.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Pcap4jUtil {

    public static PcapNetworkInterface getNetworkDevice(String ipAddress) throws UnknownHostException, PcapNativeException {
        InetAddress addr = InetAddress.getByName(ipAddress);
        return Pcaps.getDevByAddress(addr);
    }
}