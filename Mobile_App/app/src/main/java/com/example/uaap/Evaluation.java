package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Adapter.EvaluationListAdapter;
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.EvaluationModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluation extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private String playing;
    private ListView evaluationList;
    private Button btnQ1;
    private Button btnQ2;
    private Button btnQ3;
    private Button btnQ4;
    private Button btnOT;
    private Button btnStart;
    private Button btnReset;
    private TextView txtMinute1;
    private TextView txtSecond1;
    private TextView txtMillis1;
    private TextView txtMinute2;
    private TextView txtSecond2;
    private TextView txtMillis2;
    private Button btnMinuteUp;
    private Button btnMinuteDown;
    private Button btnSecondUp;
    private Button btnSecondDown;
    private Button btnMillisUp;
    private Button btnMillisDown;
    private TextView txtTeams;

    private FloatingActionButton fab;
    private EvaluationListAdapter listAdapter;
    private EvaluationModel calls;

    private CountDownTimer cdTimer;
    private boolean timerRunning;
    private long time;

    private CurrentGame currentGame;

    private String GetCallURL = "http://68.183.49.18/uaap/public/getAll";
    private String DeleteEvaluationURL = "http://68.183.49.18/uaap/public/deleteEvaluation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        evaluationList = findViewById(R.id.evaluationList);
        fab = findViewById(R.id.fab);
        txtTeams = findViewById(R.id.txtTeams);
        txtMinute1 = findViewById(R.id.txtMinute1);
        txtSecond1 = findViewById(R.id.txtSecond1);
        txtMillis1 = findViewById(R.id.txtMillis1);
        txtMinute2 = findViewById(R.id.txtMinute2);
        txtSecond2 = findViewById(R.id.txtSecond2);
        txtMillis2 = findViewById(R.id.txtMillis2);
        btnMinuteUp = findViewById(R.id.btnMinuteUp);
        btnMinuteDown = findViewById(R.id.btnMinuteDown);
        btnSecondUp = findViewById(R.id.btnSecondUp);
        btnSecondDown = findViewById(R.id.btnSecondDown);
        btnMillisUp = findViewById(R.id.btnMillisUp);
        btnMillisDown = findViewById(R.id.btnMillisDown);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btnQ1 = findViewById(R.id.btnQ1);
        btnQ2 = findViewById(R.id.btnQ2);
        btnQ3 = findViewById(R.id.btnQ3);
        btnQ4 = findViewById(R.id.btnQ4);
        btnOT = findViewById(R.id.btnOT);

        final Button[] periodButtons = {btnQ1, btnQ2, btnQ3, btnQ4, btnOT};

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playing = extras.getString("playing");
        }
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);

        txtTeams.setText(currentGame.getTeamA() + " vs " +currentGame.getTeamB());
        getCalls();
        time = currentGame.getTimeInMillis();
        evaluationList.setOnItemClickListener(this);
        evaluationList.setOnItemLongClickListener(this);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    btnStart.setText("Start");
                    pauseTimer();
                }else{
                    btnStart.setText("Pause");
                    startTimer();

                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setText("Start");
                resetTimer();
            }
        });
        btnMinuteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("minute", true);
            }
        });

        btnMinuteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("minute", false);
            }
        });

        btnSecondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("second", true);
            }
        });

        btnSecondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("second", false);
            }
        });

        btnMillisUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("millis", true);
            }
        });

        btnMillisDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("millis", false);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EvaluatorDetails.class);
                currentGame.setTimeInMillis(time);
                Gson gson = new Gson();
                String json = gson.toJson(currentGame);
                intent.putExtra("playing", json);
                startActivity(intent);
            }
        });
        for(int i=0;i<5;i++){
            final int finalI = i;
            periodButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enablePeriod(finalI, periodButtons);
                }
            });
        }

    }

    private void enablePeriod(int i, Button[] buttons){
        String[] periods = {"Q1", "Q2", "Q3", "Q4", "OT"};
        for (int x=0;x<5;x++){
            if(x==i){
                buttons[x].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                currentGame.setPeriodName(periods[i]);
                currentGame.setPeriod(i);
            }
            else{
                buttons[x].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
            }
        }
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
                params.put("gameId", currentGame.getGameId());
                return params;
            }

        };

        queue.add(putRequest);
    }




    public void onItemClick(AdapterView parent, View v, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Evaluation.this);
        builder.setCancelable(true);
        builder.setTitle("Edit Evaluation");
        builder.setMessage("Do you want to edit this record?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), EvaluatorDetailsEdit.class);
                        currentGame.setTimeInMillis(time);
                        Gson gson = new Gson();
                        String json = gson.toJson(currentGame);
                        intent.putExtra("playing", json);
                        intent.putExtra("id",String.valueOf(calls.result.get(position).id));
                        Log.e("id", String.valueOf(calls.result.get(position).id));
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Evaluation.this);
        builder.setCancelable(true);
        builder.setTitle("Delete Evaluation");
        builder.setMessage("Are you sure you want to delete this record?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(Evaluation.this);
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
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }
    private void ops(String op, boolean opType){
        long value = 0;
        if(op.equals("minute")){
            value = 60000;
        }
        else if(op.equals("second")){
            value = 1000;
        }
        else if(op.equals("millis")){
            value = 10;
        }
        if(opType){
            time+=value;
        }
        else{
            time-=value;
        }
        updateCountDownText();
    }

    private void startTimer() {
        cdTimer = new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                updateCountDownText();
                btnMinuteUp.setEnabled(false);
                btnMinuteDown.setEnabled(false);
                btnSecondUp.setEnabled(false);
                btnSecondDown.setEnabled(false);
                btnMillisDown.setEnabled(false);
                btnMillisUp.setEnabled(false);

            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStart.setText("Start");
                btnMinuteUp.setEnabled(true);
                btnMinuteDown.setEnabled(true);
                btnSecondUp.setEnabled(true);
                btnSecondDown.setEnabled(true);
                btnMillisDown.setEnabled(true);
                btnMillisUp.setEnabled(true);
            }
        }.start();

        timerRunning = true;
        btnStart.setText("Pause");
    }
    private void pauseTimer() {
        if(timerRunning){
            cdTimer.cancel();
            timerRunning = false;
        }
        btnStart.setText("Start");
        btnMinuteUp.setEnabled(true);
        btnMinuteDown.setEnabled(true);
        btnSecondUp.setEnabled(true);
        btnSecondDown.setEnabled(true);
        btnMillisDown.setEnabled(true);
        btnMillisUp.setEnabled(true);
    }

    private void resetTimer() {
        time = 600000;
        pauseTimer();
        updateCountDownText();
    }

    private void updateCountDownText() {
        long minutes = (time / 1000) / 60;
        long seconds = (time / 1000) % 60;
        long millis = time - (minutes*60000) - (seconds*1000);
        updateText(txtMinute1, txtMinute2,minutes);
        updateText(txtSecond1,txtSecond2,seconds);
        updateMillis(txtMillis1,txtMillis2,millis);
    }
    private void updateText(TextView txtView1, TextView txtView2, long timeValue){
        if(timeValue>=10){
            txtView1.setText(Character.toString(Long.toString(timeValue).charAt(0)));
            txtView2.setText(Character.toString(Long.toString(timeValue).charAt(1)));
        }
        else{
            txtView1.setText("0");
            txtView2.setText(Long.toString(timeValue));
        }
    }
    private void updateMillis(TextView txtView1, TextView txtView2, long timeValue){
        if(timeValue>=100){
            txtView1.setText(Character.toString(Long.toString(timeValue).charAt(0)));
            txtView2.setText(Character.toString(Long.toString(timeValue).charAt(1)));
        }
        else{
            txtView1.setText("0");
            txtView2.setText(Character.toString(Long.toString(timeValue).charAt(0)));
        }
    }


}
