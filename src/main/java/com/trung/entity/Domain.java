package com.trung.entity;


import java.util.List;
import java.util.Map;

/**
 * @author trung
 * @version 1.0
 * @since 10/4/2020
 */

public class Domain {
    private Map<String, String> cookies;
    private List<Record> records;
    private String username;
    private String passwd;

    public Domain() {
    }

    public Domain(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }


    public List<Record> getRecords() {
        return this.records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}

