package com.example.uaap.Manage;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Adapter.LeagueListAdapter;
import com.example.uaap.Admin;
import com.example.uaap.EvaluatorActivity;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.League;
import com.example.uaap.Model.LeagueDetails;
import com.example.uaap.Model.User;
import com.example.uaap.R;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddLeague extends AppCompatActivity {
    Button btnAddLeague, btnCancel, btnAdd;
    EditText edtLeague;
    private ListView leagueList;
    private LeagueListAdapter leagueListAdapter;
    private String addLeagueUrl = "http://68.183.49.18/uaap/public/addLeague";
    private String GetLeagueURL = "http://68.183.49.18/uaap/public/getLeague";
    private League league;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_league);

        btnAddLeague = findViewById(R.id.btnAddLeague);
        btnCancel = findViewById(R.id.btnCancel);
        btnAdd = findViewById(R.id.btnAdd);
        final LinearLayout linearLayout = findViewById(R.id.addLeague);
        edtLeague = findViewById(R.id.edtLeague);
        leagueList = findViewById(R.id.leagueList);

        getLeague();
        btnAddLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leagueName = edtLeague.getText().toString();
                addingLeague(leagueName);

            }
        });

    }

    private void addingLeague(final String leagueName) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, addLeagueUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("leagueName", leagueName);
                return params;
            }

        };

        queue.add(putRequest);
    }

    private void getLeague() {
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest putRequest = new StringRequest(Request.Method.POST, GetLeagueURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Here", response);
                            Gson gson = new Gson();
                            league = gson.fromJson(response, League.class);
                            ArrayList<LeagueDetails> dataModelArrayList = league.result;
                            Log.e("size", String.valueOf(league.result.size()));
                            if(!dataModelArrayList.isEmpty()) {
                                leagueListAdapter = new LeagueListAdapter(getApplicationContext(), dataModelArrayList);
                                leagueList.setAdapter(leagueListAdapter);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.e("Error.Response", String.valueOf(error));
                        }
                    }
            );
            queue.add(putRequest);
        }
    }
}
