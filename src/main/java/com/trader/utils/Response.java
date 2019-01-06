package com.trader.utils;

import java.util.Date;

public class Response<T> {
    private boolean success;
    private String message;
    private T data;
    private Exception exception;
    private Date timestamp;
    private int status;

    public Response(boolean success, String message, T data, Exception exception) {
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

    public Exception getException() {
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
