package com.example.rongfu.bean;

public class PlanItem {
    private int piId;
    private int planId;
    private int userId;
    private int state;

    public PlanItem() {
    }

    public int getPiId() {
        return piId;
    }

    public void setPiId(int piId) {
        this.piId = piId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PlanItem{" +
                "piId=" + piId +
                ", planId=" + planId +
                ", userId=" + userId +
                ", state=" + state +
                '}';
    }
}
