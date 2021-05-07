package com.example.rongfu.bean;

public class ProductCategory {
    private int pcId;
    private String pcName;
    private int userId;

    public ProductCategory() {
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProductCategroy{" +
                "pcId=" + pcId +
                ", pcName='" + pcName + '\'' +
                ", userid=" + userId +
                '}';
    }
}
