package com.example.healventureapp.controller;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.healventureapp.R;

public class NotificationPatients extends AppCompatActivity {
    ConstraintLayout patientHolder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paitient_notification);
        patientHolder = findViewById(R.id.patientHolder);
        String[] textArray = {"your appointment has been booked for", "Reminder! your appointment is on", "your appointment has been booked for", "your appointment was cancelled on "};
        LinearLayout topLayer = new LinearLayout(this);
        topLayer.setOrientation(LinearLayout.VERTICAL);
        for( int i = 0; i < textArray.length; i++ )
        {
            LinearLayout reportLinearLayout = new LinearLayout(this);
            reportLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView pdfIcon = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);

            // setting the margin in linearlayout
            params.setMargins(10, 20, 10, 20);
            pdfIcon.setLayoutParams(params);


            String uri = "@drawable/info";  // where myresource (without the extension) is the file
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            pdfIcon.setImageDrawable(res);


            TextView textView = new TextView(this);
            textView.setText(textArray[i] + " time" + ", date");

            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setTextSize(22);
            reportLinearLayout.addView(pdfIcon);
            reportLinearLayout.addView(textView);
            topLayer.addView(reportLinearLayout);
        }
        patientHolder.addView(topLayer);

    }


}
