package com.quan12yt.trackingsearchhistory.exception;



public class NetworkNotFoundException extends RuntimeException {

    public String getMessage(String mess) {
        return mess;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
