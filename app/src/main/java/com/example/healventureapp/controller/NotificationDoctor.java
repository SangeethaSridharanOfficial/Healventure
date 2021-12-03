package com.example.healventureapp.controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.healventureapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NotificationDoctor extends AppCompatActivity {
    ConstraintLayout doctorHolder;
    private DatabaseReference healventureDatabase;
    private DatabaseReference bookAppointEndPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_notification);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        bookAppointEndPoint = healventureDatabase.child("bookAppointment");
        doctorHolder = findViewById(R.id.doctorHolder);

        renderNotif();

    }

    private void renderNotif() {
        bookAppointEndPoint.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    LinearLayout topLayer = new LinearLayout(NotificationDoctor.this);
                    topLayer.setOrientation(LinearLayout.VERTICAL);
                    for(DataSnapshot doc: task.getResult().getChildren()){
                        Map<String,String> reports=(HashMap<String, String>)doc.getValue();
                        LinearLayout reportLinearLayout = new LinearLayout(NotificationDoctor.this);
                        reportLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        ImageView pdfIcon = new ImageView(NotificationDoctor.this);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);

                        // setting the margin in linearlayout
                        params.setMargins(10, 20, 10, 40);
                        pdfIcon.setLayoutParams(params);


                        String uri = "@drawable/info";  // where myresource (without the extension) is the file
                        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        pdfIcon.setImageDrawable(res);


                        TextView textView = new TextView(NotificationDoctor.this);
                        textView.setText("Patient " + reports.get("userName") + " has booked an appointment on " + reports.get("date") + " at " + reports.get("time"));

                        textView.setGravity(Gravity.CENTER_VERTICAL);
                        textView.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        textView.setTextSize(16);
                        GradientDrawable border = new GradientDrawable();
                        border.setStroke(1, 0xFF000000); //black border with full opacity
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            reportLinearLayout.setBackgroundDrawable(border);
                        } else {
                            reportLinearLayout.setBackground(border);
                        }
                        reportLinearLayout.addView(pdfIcon);
                        reportLinearLayout.addView(textView);
                        topLayer.addView(reportLinearLayout);
                    }
                    doctorHolder.addView(topLayer);
                }else{
                    Toast.makeText(NotificationDoctor.this,"There is no data", Toast.LENGTH_LONG).show();
                }
            }
        });
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

                Intent mainPage1 = new Intent(this, NotificationPatients.class);
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

