package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Adapter.EvaluationListAdapter;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.EvaluationModel;

import com.example.uaap.Model.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluation extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private String gameId;
    private String gameCode;
    private String playing;
    private String teamA;
    private String teamB;
    private int scoreA;
    private int scoreB;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ListView evaluationList;
    private TextView txtTeamA;
    private TextView txtTeamB;
    private TextView txtScoreA;
    private TextView txtScoreB;
    private Button btnAddScoreA;
    private Button btnSubScoreA;
    private Button btnAddScoreB;
    private Button btnSubScoreB;
    private FloatingActionButton fab;
    private EvaluationListAdapter listAdapter;
    private EvaluationModel calls;

    private String GetCallURL = "http://68.183.49.18/uaap/public/getAll";
    private String SaveScoreURL = "http://68.183.49.18/uaap/public/saveScore";
    private String DeleteEvaluationURL = "http://68.183.49.18/uaap/public/deleteEvaluation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        evaluationList = findViewById(R.id.evaluationList);
        txtTeamA = findViewById(R.id.txtTeamA);
        txtTeamB = findViewById(R.id.txtTeamB);
        txtScoreA = findViewById(R.id.txtScoreA);
        txtScoreB = findViewById(R.id.txtScoreB);
        btnAddScoreA = findViewById(R.id.btnAddScoreA);
        btnSubScoreA = findViewById(R.id.btnSubScoreA);
        btnAddScoreB = findViewById(R.id.btnAddScoreB);
        btnSubScoreB = findViewById(R.id.btnSubScoreB);
        fab = findViewById(R.id.fab);

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameId = extras.getString("gameId");
            gameCode = extras.getString("gameCode");
            playing = extras.getString("playing");
        }

        init();
        evaluationList.setOnItemClickListener(this);
        evaluationList.setOnItemLongClickListener(this);


        btnAddScoreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreA++;
                refreshScore();
            }
        });

        btnAddScoreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreB++;
                refreshScore();

            }
        });

        btnSubScoreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreA--;
                if (scoreA < 0) {
                    scoreA = 0;
                }
                refreshScore();
            }
        });

        btnSubScoreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreB--;
                if (scoreB < 0) {
                    scoreB = 0;
                }
                refreshScore();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EvaluatorDetails.class);
                intent.putExtra("gameId", gameId);
                intent.putExtra("gameCode", gameCode);
                intent.putExtra("playing", playing);
                intent.putExtra("teamA", teamA);
                intent.putExtra("teamB", teamB);
                startActivity(intent);
            }
        });

    }


    private void refreshScore() {
        editor.putInt("scoreA", scoreA);
        editor.putInt("scoreB", scoreB);
        editor.apply();
        scoreA = pref.getInt("scoreA", 0);
        scoreB = pref.getInt("scoreB", 0);

        txtScoreA.setText(String.valueOf(scoreA));
        txtScoreB.setText(String.valueOf(scoreB));
    }

    private void init() {

        teamA = pref.getString("teamA", null);
        teamB = pref.getString("teamB", null);
        scoreA = pref.getInt("scoreA", 0);
        scoreB = pref.getInt("scoreB", 0);

        txtTeamA.setText(teamA);
        txtTeamB.setText(teamB);
        txtScoreA.setText(String.valueOf(scoreA));
        txtScoreB.setText(String.valueOf(scoreB));
        getCalls();
    }

    private void getCalls() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, GetCallURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        Gson gson = new Gson();
                        calls = gson.fromJson(response, EvaluationModel.class);
                        ArrayList<EvaluationDetails> dataModelArrayList = calls.result;
                        if(!dataModelArrayList.isEmpty()) {
                            listAdapter = new EvaluationListAdapter(getApplicationContext(), dataModelArrayList);
                            evaluationList.setAdapter(listAdapter);
                        }

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
                params.put("gameId", gameId);
                return params;
            }

        };

        queue.add(putRequest);
    }

    @Override
    public void onBackPressed() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, SaveScoreURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


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
                params.put("gameId", gameId);
                params.put("scoreA", String.valueOf(scoreA));
                params.put("scoreB", String.valueOf(scoreB));
                return params;
            }

        };

        queue.add(putRequest);
        Intent intent = new Intent(getApplicationContext(), EvaluatorActivity.class);
        startActivity(intent);
    }


    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest putRequest = new StringRequest(Request.Method.POST, DeleteEvaluationURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
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
                params.put("evaluationId", String.valueOf(calls.result.get(position).id));
                return params;
            }

        };

        queue.add(putRequest);
        return true;
    }


}
