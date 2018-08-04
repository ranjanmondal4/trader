package com.trader.utils;

import java.util.Date;

public class Response<T,G> {
    private boolean success;
    private String message;
    private T data;
    private G exception;
    private Date timestamp;
    private int status;

    public Response(boolean success, String message, T data, G exception) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.exception = exception;
        this.timestamp = new Date();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public G getException() {
        return exception;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
