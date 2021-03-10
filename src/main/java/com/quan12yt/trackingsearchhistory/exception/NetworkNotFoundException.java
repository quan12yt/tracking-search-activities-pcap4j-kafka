package com.quan12yt.trackingsearchhistory.exception;


public class NetworkNotFoundException extends RuntimeException {

    public NetworkNotFoundException(String message) {
        super(message);
    }

    public NetworkNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
