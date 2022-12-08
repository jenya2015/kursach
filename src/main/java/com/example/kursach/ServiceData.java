package com.example.kursach;

public class ServiceData {

    private String operator;
    private String window;
    private String client;
    private String order;
    private String date;
    private String status;

    public ServiceData(String operator, String window, String client, String order, String date, String status) {
        this.operator = operator;
        this.window = window;
        this.client = client;
        this.order = order;
        this.date = date;
        this.status = status;
    }

    public ServiceData() {}

    public String getOperator() {return operator;}

    public void setOperator(String operator) {this.operator = operator;}

    public String getWindow() {return window;}

    public void setWindow(String window) {this.window = window;}

    public String getClient() {return client;}

    public void setClient(String client) {this.client = client;}

    public String getOrder() {return order;}

    public void setOrder(String order) {this.order = order;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

}
