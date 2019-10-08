package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        CardView manage = findViewById(R.id.btn_manage);
        manage.setAlpha(0.75f);

        CardView reports = findViewById(R.id.btn_reports);
        reports.setAlpha(0.75f);

        CardView realtime = findViewById(R.id.btn_realtime);
        realtime.setAlpha(0.75f);
    }
}
