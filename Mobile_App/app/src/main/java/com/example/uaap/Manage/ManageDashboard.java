package com.example.uaap.Manage;
import com.example.uaap.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dashboard);
    }

    public void btn_teams(View view) {
        startActivity(new Intent(ManageDashboard.this, TeamsDashboard.class));
    }
}
