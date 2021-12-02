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
import androidx.core.content.ContextCompat;

import com.example.healventureapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MyReport extends AppCompatActivity {
    ConstraintLayout reportsHolder;
    private DatabaseReference healventureDatabase;
    private DatabaseReference reportEndPoint;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreports);
        healventureDatabase = FirebaseDatabase.getInstance().getReference();
        reportEndPoint = healventureDatabase.child("reports");
        reportsHolder = findViewById(R.id.reportsHolder);
        renderReports();
    }

    private void renderReports(){
        reportEndPoint.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    LinearLayout topLayer = new LinearLayout(MyReport.this);
                    topLayer.setOrientation(LinearLayout.VERTICAL);
                    for(DataSnapshot doc: task.getResult().getChildren()){
                        Map<String,String> reports=(HashMap<String, String>)doc.getValue();
                        LinearLayout reportLinearLayout = new LinearLayout(MyReport.this);
                        reportLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        ImageView pdfIcon = new ImageView(MyReport.this);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);

                        // setting the margin in linearlayout
                        params.setMargins(10, 20, 10, 20);
                        pdfIcon.setLayoutParams(params);


                        String uri = "@drawable/pdf";  // where myresource (without the extension) is the file
                        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        pdfIcon.setImageDrawable(res);


                        TextView textView = new TextView(MyReport.this);
                        textView.setText(reports.get("name") + "time");
                        textView.setWidth(1300);
                        textView.setGravity(Gravity.CENTER_VERTICAL);
                        textView.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        textView.setTextSize(22);
                        reportLinearLayout.addView(pdfIcon);
                        reportLinearLayout.addView(textView);
                        GradientDrawable border = new GradientDrawable();
                        border.setStroke(1, 0xFF000000); //black border with full opacity
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            reportLinearLayout.setBackgroundDrawable(border);
                        } else {
                            reportLinearLayout.setBackground(border);
                        }

                        reportLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent pdfView = new Intent(Intent.ACTION_VIEW);
                                pdfView.setType("application/pdf");
                                pdfView.setData(Uri.parse(reports.get("url")));
                                startActivity(pdfView);
                            }
                        });
                        topLayer.addView(reportLinearLayout);
                    }
                    reportsHolder.addView(topLayer);
                }else{
                    Toast.makeText(MyReport.this,"There is no data", Toast.LENGTH_LONG).show();
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
                startActivity(mainPage);
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.notification:

                Intent mainPage1 = new Intent(this, NotificationPatients.class);
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
