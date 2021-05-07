package com.example.rongfu.bean;

import java.sql.Timestamp;
import java.util.List;

public class Plan {
    private int planId;
    private String title;
    private String content;
    private Timestamp addTime;
    private String staffs;
    private String progress;
    private int epId;
    private int userId;

    private int progressNum;
    private List<User> userList;

    public Plan() {
    }

    public void setProgressNum(int progressNum) {
        this.progressNum = progressNum;
    }

    public int getProgressNum() {
        return progressNum;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getStaffs() {
        return staffs;
    }

    public void setStaffs(String staffs) {
        this.staffs = staffs;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planId=" + planId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", addTime=" + addTime +
                ", staffs='" + staffs + '\'' +
                ", progress='" + progress + '\'' +
                ", epId=" + epId +
                ", userId=" + userId +
                ", progressNum=" + progressNum +
                ", userList=" + userList +
                '}';
    }
}
