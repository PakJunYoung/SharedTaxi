package com.inhatc.sharedtaxi;

public class ChatData {
    private String userName;
    private String message;

    public ChatData() {
        this.userName = "UnknownUser";
        this.message = "";
    }

    public ChatData(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
