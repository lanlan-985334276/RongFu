package com.example.rongfu.bean;

public class JsonBean<T>{
    private int state;
    private T data;
    private String message;

    public JsonBean() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public JsonBean(int state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "state=" + state +
                ", data='" + data + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
