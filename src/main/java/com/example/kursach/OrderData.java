package com.example.kursach;

public class OrderData {

    private String id;
    private String order;

    public OrderData(String id, String order) {
        this.id = id;
        this.order = order;
    }

    public OrderData() {}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getOrder() {return order;}

    public void setOrder(String order) {this.order = order;}

}
