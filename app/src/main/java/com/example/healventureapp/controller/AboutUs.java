package com.example.healventureapp.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;

public class AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
    }
//    public void fbClick(View view) {
//        startActivity(getOpenFacebookIntent());
//    }
//    public Intent getOpenFacebookIntent() {
//        try {
//            getPackageManager().getPackageInfo("com.facebook.katana", 0);
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/426253597411506"));
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/appetizerandroid"));
//        }
//    }

}
