package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.uaap.Manage.ManageDashboard;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void btn_manage(View view) {
        startActivity(new Intent(DashboardActivity.this, ManageDashboard.class));
    }

    public void btn_reports(View view) {
    }

    public void btn_realtime(View view) {
    }
}
