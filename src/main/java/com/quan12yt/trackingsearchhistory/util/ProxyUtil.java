package com.quan12yt.trackingsearchhistory.util;

import java.net.*;


public class ProxyUtil {

    public static boolean isValid(Integer port){
        boolean connectionStatus=false;
        try {
            InetAddress addr=InetAddress.getByName("174.16.10.128");//here type proxy server ip
            connectionStatus=addr.isReachable(port); // 1 second time for response
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return connectionStatus;
    }
}
