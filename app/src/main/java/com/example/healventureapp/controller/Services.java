package com.example.healventureapp.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
import com.google.firebase.database.annotations.Nullable;

public class Services extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_activity);
        this.setTitle("Services");
    }

}