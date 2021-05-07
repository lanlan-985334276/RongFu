package com.example.rongfu.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class Enterprise {
    private int epId;
    private int userId;
    private String epName;
    private Timestamp time;

    public Enterprise() {
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "epId=" + epId +
                ", userId=" + userId +
                ", epName='" + epName + '\'' +
                ", time=" + time +
                '}';
    }
}
