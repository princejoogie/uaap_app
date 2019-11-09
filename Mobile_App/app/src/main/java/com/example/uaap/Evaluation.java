package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    int counter = 0;
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
    long finalMinute, finalSecond, finalMilli;
    private String GetCallURL = "http://68.183.49.18/uaap/public/getAll";
    private String SaveScoreURL = "http://68.183.49.18/uaap/public/saveScore";
    private String DeleteEvaluationURL = "http://68.183.49.18/uaap/public/deleteEvaluation";

    private long totalMillis;
    // Timer
    Button minuteUp, secondUp, miliUp, minuteDown, secondDown, miliDown, start, reset;
    TextView txtMinute1, txtSecond1, txtMillis1, txtMinute2, txtSecond2, txtMillis2;

    private Handler handler = new Handler();
    private Runnable runnable;

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
        finalMinute = 10;
        finalSecond = 0;
        finalMilli = 0;
        //Timer
        minuteUp = findViewById(R.id.minuteUp);
        secondUp = findViewById(R.id.secondUp);
        miliUp = findViewById(R.id.miliUp);
        minuteDown = findViewById(R.id.minuteDown);
        secondDown = findViewById(R.id.secondDown);
        miliDown = findViewById(R.id.miliDown);
        txtMinute1 = findViewById(R.id.txtMinute1);
        txtSecond1 = findViewById(R.id.txtSecond1);
        txtMillis1 = findViewById(R.id.txtMillis1);
        txtMinute2 = findViewById(R.id.txtMinute2);
        txtSecond2 = findViewById(R.id.txtSecond2);
        txtMillis2 = findViewById(R.id.txtMillis2);
        start = findViewById(R.id.start);
        reset = findViewById(R.id.reset);

        initTime();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameId = extras.getString("gameId");
            gameCode = extras.getString("gameCode");
            playing = extras.getString("playing");
        }
        Log.e("GameCode", gameCode);

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

        minuteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMinute >= 9) {
                    finalMinute = 0;
                    txtMinute1.setText("1");

                } else {
                    finalMinute = finalMinute + 1;
                }
                updateTime(txtMinute1, txtMinute2, finalMinute);
            }
        });

        secondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalSecond >= 59) {
                    finalSecond = 0;

                } else {
                    finalSecond = finalSecond + 1;
                }
                updateTime(txtSecond1, txtSecond2, finalSecond);
            }
        });

        miliUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMilli >= 59) {
                    finalMilli = 0;
                } else {
                    finalMilli = finalMilli + 1;
                }
                updateTime(txtMillis1, txtMillis2, finalMilli);
            }
        });

        minuteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMinute <= 0) {
                    finalMinute = 10;
                } else {
                    finalMinute = finalMinute - 1;
                }
                updateTime(txtMinute1, txtMinute2, finalMinute);

            }
        });

        secondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalSecond <= 0) {
                    finalSecond = 59;

                } else {
                    finalSecond = finalSecond - 1;
                }
                updateTime(txtSecond1, txtSecond2, finalSecond);
            }
        });

        miliDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMilli <= 0) {
                    finalMilli = 59;
                } else {
                    finalMilli = finalMilli - 1;
                }
                updateTime(txtMillis1, txtMillis2, finalMilli);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStart();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReset();
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
                        if (!dataModelArrayList.isEmpty()) {
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

    private void initTime() {
        txtMinute1.setText("1");
        txtMinute2.setText("0");
        txtSecond1.setText("0");
        txtSecond2.setText("0");
        txtMillis1.setText("0");
        txtMillis2.setText("0");
    }

    private void updateTime(TextView textView1, TextView textView2, long time) {
        if (time >= 10) {
            textView1.setText(Character.toString(Long.toString(time).charAt(0)));
            textView2.setText(Character.toString(Long.toString(time).charAt(1)));
        } else {
            textView1.setText("0");
            textView2.setText(Long.toString(time));
        }
    }

    //   private void countDown(){
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    handler.postDelayed(this, 1);
//                   finalMilli =finalMilli - 1;
//
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        handler.postDelayed(runnable, 0);
//    }
    private void countDown() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1);
                    totalMillis= finalMilli + (finalSecond*60) + (finalMinute*60*60);
                    totalMillis--;
                    if(totalMillis==0){

                    }
                    finalMinute = (totalMillis/1000) *60;
                    finalSecond = (totalMillis/1000) %60;
                    finalMilli = totalMillis - (finalSecond*60)-(finalMinute*60*60);
                    update();
//                    finalMilli = finalMilli - 1;
//
//                    if (finalMilli <= 0) {
//                        finalSecond = finalSecond - 1;
//                        update();
//                        finalMilli = 60;
//                        if (finalSecond <= 0) {
//                            finalMinute = finalMinute - 1;
//                            update();
//                            finalSecond = 60;
//                            finalMilli = 60;
//                            if (finalMinute <= 0) {
//                                finalMinute = 10;
//                                finalSecond = 0;
//                                finalMilli = 0;
//                                update();
//                                onStop();
//
//                                enable(false);
//                            } else {
//                                update();
//                            }
//                        } else {
//                            update();
//                        }
//
//                    } else {
//                        update();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }








    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    private void update() {
        updateTime(txtMillis1, txtMillis2, finalMilli);
        updateTime(txtMinute1, txtMinute2, finalMinute);
        updateTime(txtSecond1, txtSecond2, finalSecond);

    }

    private void enable(boolean value) {
        minuteUp.setEnabled(value);
        secondUp.setEnabled(value);
        miliUp.setEnabled(value);
        minuteDown.setEnabled(value);
        secondDown.setEnabled(value);
        miliDown.setEnabled(value);

    }

    private void buttonStart() {
        if (counter == 0) {
            counter = counter + 1;
            enable(false);
            countDown();
            start.setText("Pause");
        } else {
            onStop();
            counter = 0;
            enable(true);
            start.setText("Start");
        }
    }


    private void onReset() {
        onStop();
        enable(true);
        finalMinute = 9;
        finalSecond = 59;
        finalMilli = 59;
        initTime();
        start.setText("Start");
    }

}
