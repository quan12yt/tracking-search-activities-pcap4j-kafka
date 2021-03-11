package com.quan12yt.trackingsearchhistory.exception;

public class DataNotFoundException extends  RuntimeException{

    public DataNotFoundException(String mess){
        super(mess);
    }

    public DataNotFoundException(String mess, Throwable t){
        super(mess, t);
    }
}
