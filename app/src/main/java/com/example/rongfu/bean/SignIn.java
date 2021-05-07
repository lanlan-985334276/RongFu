package com.example.rongfu.bean;

import java.sql.Timestamp;

public class SignIn {
    private int siId;
    private int staffId;
    private Timestamp date;

    public SignIn() {
    }

    public int getSiId() {
        return siId;
    }

    public void setSiId(int siId) {
        this.siId = siId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "siId=" + siId +
                ", staffId=" + staffId +
                ", date=" + date +
                '}';
    }
}
