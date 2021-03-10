package com.quan12yt.trackingsearchhistory.exception;

public class StartServiceFailedException extends RuntimeException{

    public StartServiceFailedException(String mess){
        super(mess);
    }

    public StartServiceFailedException(String mess, Throwable t){
        super(mess, t);
    }
}
