package com.example.kursach;

public class OperaData {

    private String id;
    private String fio;
    private String login;
    private String password;
    private String windows;

    public OperaData(String id, String fio, String login, String password, String windows) {
        this.id = id;
        this.fio = fio;
        this.login = login;
        this.password = password;
        this.windows = windows;
    }

    public OperaData() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getWindows() {return windows;}

    public void setWindows(String windows) {this.windows = windows;}


}
