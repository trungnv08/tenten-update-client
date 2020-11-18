package com.trung.entity;

import com.google.gson.annotations.SerializedName;
import com.trung.util.Helpers;

/**
 * @author trung
 * @version 1.0
 * @since 10/4/2020
 */

public class Record {

    /**
     * {
     * "id": "320a2b92-8b78-485c-808a-5096dbf93a25",
     * "ttl": null,
     * "action": "NONE",
     * "zone_name": "piconat.cloud.",
     * "name": "mail.piconat.cloud.",
     * "type": "A",
     * "records": [
     * "113.179.114.111"
     * ],
     * "status": "ACTIVE"
     * }
     */
    private String id;
    private String name;
    @SerializedName("zone_name")
    private String zoneName;
    private String type;
    private String[] records;
    private int mxPreference;
    private String status;
    private String action;
    private String ttl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getRecords() {
        return records;
    }

    public void setRecords(String[] records) {
        this.records = records;
    }

    public int getMxPreference() {
        return mxPreference;
    }

    public void setMxPreference(int mxPreference) {
        this.mxPreference = mxPreference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return Helpers.gson.toJson(this);
    }
}

