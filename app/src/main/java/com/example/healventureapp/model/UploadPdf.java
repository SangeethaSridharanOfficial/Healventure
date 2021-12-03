package com.example.healventureapp.model;

public class UploadPdf {
    private String name;
    private String url;
    private String username;

    public UploadPdf(){

    }

    public UploadPdf(String name, String url, String username){
        this.name = name;
        this.url = url;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
