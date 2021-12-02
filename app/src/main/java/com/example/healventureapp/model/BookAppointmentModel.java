package com.example.healventureapp.model;

public class BookAppointmentModel {
    private String date;
    private String time;
    private String userName;


    public BookAppointmentModel(String date, String time, String userName) {
        this.date = date;
        this.time = time;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
