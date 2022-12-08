package com.example.kursach;

public class WindowData {

    private String id;
    private String window;

    public WindowData(String id, String window) {
        this.id = id;
        this.window = window;
    }

    public WindowData() {}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getWindow() {return window;}

    public void setWindow(String window) {this.window = window;}

}
