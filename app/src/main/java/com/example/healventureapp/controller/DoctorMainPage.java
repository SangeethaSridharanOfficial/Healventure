package com.example.healventureapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healventureapp.R;
public class DoctorMainPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_main_page);
    }
    public void onCLickUploadReport(View v){
        Intent mainPage = new Intent(this, UploadReport.class);
        mainPage.putExtra("testData","test");
        startActivity(mainPage);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.profile:
                Intent mainPage = new Intent(this, Profile.class);
                mainPage.putExtra("testData","test");
                startActivity(mainPage);
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.notification:
                Intent mainPage1 = new Intent(this, NotificationDoctor.class);
                mainPage1.putExtra("testData","test");
                startActivity(mainPage1);
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return true;
    }
}