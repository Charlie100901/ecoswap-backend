package com.app.ecoswap.models;


import org.springframework.http.HttpStatus;

import java.util.Date;

public class Error {

    private Date date;
    private int status;
    private String message;

    public Error() {
    }

    public Error(Date date, int status, String message) {
        this.date = date;
        this.status = status;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "date=" + date +
                ", httpStatus=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}

