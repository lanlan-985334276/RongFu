package com.example.rongfu.bean;

public class StepBean {
    private int stepId;
    private int userId;
    private String userName;
    private int num;
    private int epId;

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public StepBean() {
    }

    @Override
    public String toString() {
        return "step{" +
                "stepId=" + stepId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", num=" + num +
                ", epId=" + epId +
                '}';
    }
}
