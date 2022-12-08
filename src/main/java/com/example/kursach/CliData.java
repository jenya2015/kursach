package com.example.kursach;

public class CliData {

    private String id;
    private String number;

    public CliData(String id, String number) {
        this.id = id;
        this.number = number;
    }

    public CliData() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
