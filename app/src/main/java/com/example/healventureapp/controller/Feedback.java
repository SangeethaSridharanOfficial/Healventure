package com.example.healventureapp.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;

public class Feedback extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        this.setTitle("Feedback");
    }
}