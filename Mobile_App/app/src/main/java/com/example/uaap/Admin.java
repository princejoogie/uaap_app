package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uaap.Manage.AddLeague;

public class Admin extends AppCompatActivity {
    Button btnLeague, btnTeam, btnReferee, btnReport, btnAccount, btnRealtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnLeague = findViewById(R.id.btnLeagues);
        btnTeam = findViewById(R.id.btnTeams);
        btnAccount = findViewById(R.id.btnAccounts);
        btnRealtime = findViewById(R.id.btnRealtime);
        btnReport = findViewById(R.id.btnReports);
        btnReferee = findViewById(R.id.btnReferees);



        btnLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddLeague.class);
                startActivity(intent);
            }
        });






    }
}
