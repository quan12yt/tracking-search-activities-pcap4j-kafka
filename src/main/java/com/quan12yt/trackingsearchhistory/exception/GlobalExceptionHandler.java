package com.quan12yt.trackingsearchhistory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnknownHostException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageHeaders inputInvalidException(UnknownHostException ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("message", ex.getMessage());
        body.put("timestamp", new Date());
        body.put("description", webRequest.getDescription(false));

        return new ErrorMessage(new Throwable(), body).getHeaders();
    }

    @ExceptionHandler(NetworkNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageHeaders networkNotFound(NetworkNotFoundException ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("message", ex.getMessage());
        body.put("timestamp", new Date());
        body.put("description", webRequest.getDescription(false));

        return new ErrorMessage(new Throwable(), body).getHeaders();
    }
}
