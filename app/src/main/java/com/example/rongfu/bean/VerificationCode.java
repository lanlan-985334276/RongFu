package com.example.rongfu.bean;

import java.sql.Timestamp;
import java.util.Date;

//验证码
public class VerificationCode {
    /**
     * ID
     */
    private int vcId;
    /**
     * 验证码
     */
    private String verificationCode;
    /**
     * 开始时间
     */
    private Timestamp startTime;
    /**
     * 结束时间
     */
    private Timestamp endTime;

    private String username;

    public VerificationCode() {
    }

    public int getVcId() {
        return vcId;
    }

    public void setVcId(int vcId) {
        this.vcId = vcId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "vcId=" + vcId +
                ", verificationCode='" + verificationCode + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", username='" + username + '\'' +
                '}';
    }
}
