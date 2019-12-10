package com.example.uaap.Manage;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Adapter.LeagueListAdapter;
import com.example.uaap.Model.AddingLeague;
import com.example.uaap.Model.League;
import com.example.uaap.Model.LeagueDetails;
import com.example.uaap.R;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AddLeague extends AppCompatActivity {
    Button btnAddLeague;
    EditText edtLeague;
    private ListView leagueList;
    private LeagueListAdapter leagueListAdapter;
    private String addLeagueUrl = "http://68.183.49.18/uaap/public/addLeague";
    private String GetLeagueURL = "http://68.183.49.18/uaap/public/getLeague";
    private League league;
    AlertDialog dialog;
//    private AddingLeague addingLeagues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_league);

//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        }, 0, 1000);
        getLeague();

        btnAddLeague = findViewById(R.id.btnAddLeague);
        final LinearLayout linearLayout = findViewById(R.id.addLeague);
        edtLeague = findViewById(R.id.edtLeague);
        leagueList = findViewById(R.id.leagueList);


        btnAddLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            createAlert();


            }
        });



        leagueList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();
            }
        });

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
                            leagueListAdapter.notifyDataSetChanged();
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
                                leagueListAdapter.notifyDataSetChanged();
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

    public void onDestroy() {
        super.onDestroy();
    }

    private void addToLeague(final String leagues){
        RequestQueue queue = Volley.newRequestQueue(AddLeague.this);
        StringRequest putRequest = new StringRequest(Request.Method.POST, addLeagueUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        AddingLeague addingLeagues = gson.fromJson(response, AddingLeague.class);
                        String message = addingLeagues.getMessage();
                        Toast.makeText(AddLeague.this,  message, Toast.LENGTH_SHORT).show();

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
                params.put("leagueName", leagues);
                return params;
            }

        };

        queue.add(putRequest);
    }

    public void createAlert(){
        final EditText edittext = new EditText(getApplicationContext());
        final TextView title = new TextView(getApplicationContext());
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(AddLeague.this);

        title.setBackgroundColor(Color.parseColor("#F7741F"));
        title.setText("Add league");
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setTextSize(20.f);
        edittext.setTextColor(Color.BLACK);
        alertbox.setView(edittext);
        alertbox.setCustomTitle(title);
        alertbox.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String leagues = edittext.getText().toString();
                addToLeague(leagues);
                dialog.dismiss();
            }

        });
       dialog =  alertbox.show();

    }


}
