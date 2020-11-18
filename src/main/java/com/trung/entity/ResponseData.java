package com.trung.entity;

/**
 * @author trung
 * @version 1.0
 * @since 10/5/2020
 */
public class ResponseData {
    private int status;
    private String data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
