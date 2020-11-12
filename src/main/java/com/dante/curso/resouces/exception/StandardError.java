package com.dante.curso.resouces.exception;

import java.io.Serializable;

public class StandardError implements Serializable {

    private Integer error;

    private String message;

    private Long timeStamp;

    public StandardError() {
    }

    public StandardError(Integer error, String message, Long timeStamp) {
        this.error = error;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
