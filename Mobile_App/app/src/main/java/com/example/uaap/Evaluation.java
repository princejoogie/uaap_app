package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import com.example.uaap.Model.EvaluationModel2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private TextView txtRef1;
    private TextView txtRef1Tot;
    private TextView txtRef1Cor;
    private TextView txtRef2;
    private TextView txtRef2Tot;
    private TextView txtRef2Cor;
    private TextView txtRef3;
    private TextView txtRef3Tot;
    private TextView txtRef3Cor;

    private Button btnMinuteUp;
    private Button btnMinuteDown;
    private Button btnSecondUp;
    private Button btnSecondDown;
    private Button btnMillisUp;
    private Button btnMillisDown;
    private TextView txtScoreA;
    private TextView txtScoreB;
    private TextView txtTeamA;
    private TextView txtTeamB;
    private Button btnAddScoreA;
    private Button btnAddScoreB;
    private Button btnSubScoreA;
    private Button btnSubScoreB;
    private Button btnEndGame;
    private Button btnTeamTO;
    private Button btnTVTO;

    private FloatingActionButton fab;
    private EvaluationListAdapter listAdapter;
    private EvaluationModel2 calls;

    private CountDownTimer cdTimer;
    private boolean timerRunning;
    private long time;

    private CurrentGame currentGame;
    private String SaveScoreURL = "http://68.183.49.18/uaap/public/saveScore";
    private String TimeoutURL = "http://68.183.49.18/uaap/public/timeout";

    private String GetCallURL = "http://68.183.49.18/uaap/public/getAllandRef";
    private String DeleteEvaluationURL = "http://68.183.49.18/uaap/public/deleteEvaluation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        evaluationList = findViewById(R.id.evaluationList);
        fab = findViewById(R.id.fab);
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

        txtScoreA = findViewById(R.id.txtScoreA);
        txtScoreB = findViewById(R.id.txtScoreB);
        txtTeamA = findViewById(R.id.txtTeamA);
        txtTeamB = findViewById(R.id.txtTeamB);
        btnAddScoreA = findViewById(R.id.btnAddScoreA);
        btnAddScoreB = findViewById(R.id.btnAddScoreB);
        btnSubScoreA = findViewById(R.id.btnSubScoreA);
        btnSubScoreB = findViewById(R.id.btnSubScoreB);
        btnEndGame = findViewById(R.id.btnEndGame);

        txtRef1 = findViewById(R.id.txtRef1);
        txtRef2 = findViewById(R.id.txtRef2);
        txtRef3 = findViewById(R.id.txtRef3);
        txtRef1Tot = findViewById(R.id.txtRef1Tot);
        txtRef2Tot = findViewById(R.id.txtRef2Tot);
        txtRef3Tot = findViewById(R.id.txtRef3Tot);
        txtRef1Cor = findViewById(R.id.txtRef1Cor);
        txtRef2Cor = findViewById(R.id.txtRef2Cor);
        txtRef3Cor = findViewById(R.id.txtRef3Cor);

        btnTeamTO = findViewById(R.id.btnTeamTO);
        btnTVTO = findViewById(R.id.btnTVTO);

        final Button[] periodButtons = {btnQ1, btnQ2, btnQ3, btnQ4, btnOT};

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playing = extras.getString("playing");
        }
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);

        getCalls();
        time = currentGame.getTimeInMillis();
        updateCountDownText();
        refreshScore();
        txtTeamA.setText(currentGame.getTeamA());
        txtTeamB.setText(currentGame.getTeamB());
        evaluationList.setOnItemClickListener(this);
        evaluationList.setOnItemLongClickListener(this);
        enablePeriod(currentGame.getPeriod(), periodButtons);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    btnStart.setText("Start");
                    pauseTimer();
                } else {
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
        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setDate(new SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
                Intent intent = new Intent(Evaluation.this, AfterGameSummary.class);
                Gson gson = new Gson();
                String json = gson.toJson(currentGame);
                intent.putExtra("playing", json);
                startActivity(intent);
            }
        });
        btnTeamTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    btnStart.setText("Start");
                    pauseTimer();
                    btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnTeamTO.setTextColor(Color.parseColor("#FFFFFF"));
                    btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    btnTVTO.setTextColor(Color.parseColor("#E9841A"));
                    timeout(true);

                }else{
                    btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnTeamTO.setTextColor(Color.parseColor("#FFFFFF"));
                    btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    btnTVTO.setTextColor(Color.parseColor("#E9841A"));
                    timeout(true);
                }


            }
        });

        btnTVTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    btnStart.setText("Start");
                    pauseTimer();
                    btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnTVTO.setTextColor(Color.parseColor("#FFFFFF"));
                    btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    btnTeamTO.setTextColor(Color.parseColor("#E9841A"));
                    timeout(false);
                }else{
                    btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnTVTO.setTextColor(Color.parseColor("#FFFFFF"));
                    btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    btnTeamTO.setTextColor(Color.parseColor("#E9841A"));
                    timeout(false);
                }

            }
        });
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            periodButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enablePeriod(finalI, periodButtons);
                }
            });
        }
        btnAddScoreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setScoreA(currentGame.getScoreA() + 1);
                refreshScore();
            }
        });

        btnAddScoreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setScoreB(currentGame.getScoreB() + 1);
                refreshScore();

            }
        });

        btnSubScoreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setScoreA(currentGame.getScoreA() - 1);
                if (currentGame.getScoreA() < 0) {
                    currentGame.setScoreA(0);
                }
                refreshScore();
            }
        });

        btnSubScoreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setScoreB(currentGame.getScoreB() - 1);
                if (currentGame.getScoreB() < 0) {
                    currentGame.setScoreB(0);
                }
                refreshScore();
            }
        });


    }

    private void refreshScore() {
        currentGame.setScoreA(currentGame.getScoreA());
        currentGame.setScoreB(currentGame.getScoreB());
        txtScoreA.setText(String.valueOf(currentGame.getScoreA()));
        txtScoreB.setText(String.valueOf(currentGame.getScoreB()));
        RequestQueue queue = Volley.newRequestQueue(Evaluation.this);

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
                params.put("gameId", currentGame.getGameId());
                params.put("scoreA", String.valueOf(currentGame.getScoreA()));
                params.put("scoreB", String.valueOf(currentGame.getScoreB()));
                return params;
            }

        };

        queue.add(putRequest);
    }

    private void enablePeriod(int i, Button[] buttons) {
        for (int x = 0; x < 5; x++) {
            if (x == i) {
                buttons[x].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                buttons[x].setTextColor(Color.parseColor("#FFFFFF"));
                currentGame.setPeriod(i);
            } else {
                buttons[x].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                buttons[x].setTextColor(Color.parseColor("#E9841A"));
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
                        calls = gson.fromJson(response, EvaluationModel2.class);
                        txtRef1.setText(calls.referee.get(0).refName);
                        txtRef1Tot.setText(calls.referee.get(0).total);
                        txtRef1Cor.setText(calls.referee.get(0).correct);
                        txtRef2.setText(calls.referee.get(1).refName);
                        txtRef2Tot.setText(calls.referee.get(1).total);
                        txtRef2Cor.setText(calls.referee.get(1).correct);
                        txtRef3.setText(calls.referee.get(2).refName);
                        txtRef3Tot.setText(calls.referee.get(2).total);
                        txtRef3Cor.setText(calls.referee.get(2).correct);

                        ArrayList<EvaluationDetails> dataModelArrayList = calls.evaluation;
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
                params.put("gameId", currentGame.getGameId());
                return params;
            }

        };

        queue.add(putRequest);
    }

    public void onItemClick(AdapterView parent, View v, final int position, long id) {
        if(!calls.evaluation.get(position).callType.contains("Timeout")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(Evaluation.this);
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
                            intent.putExtra("id", String.valueOf(calls.evaluation.get(position).id));
                            Log.e("id", String.valueOf(calls.evaluation.get(position).id));
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
        }else{
            Toast.makeText(getApplicationContext(), "Programmer's Note: You cannot edit timeout as of this moment!", Toast.LENGTH_LONG).show();
        }



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
                                params.put("evaluationId", String.valueOf(calls.evaluation.get(position).id));
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

    private void timeout(boolean to) {
        if (to) {
            final Dialog dialog = new Dialog(Evaluation.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.radiobutton_staff);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
            final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroup);

            RadioButton rb = new RadioButton(Evaluation.this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(currentGame.getTeamA());
            rb.setId(Integer.parseInt(currentGame.getTeamAId()));
            rg.addView(rb);
            rb = new RadioButton(Evaluation.this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(currentGame.getTeamB());
            rb.setId(Integer.parseInt(currentGame.getTeamBId()));
            rg.addView(rb);


            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                   addTimeout(true, checkedId);
                    dialog.dismiss();

                }
            });

            dialog.show();

        }else{
            addTimeout(false, 0);
        }
    }

    private void addTimeout(final boolean to, final int id){
        RequestQueue queue = Volley.newRequestQueue(Evaluation.this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, TimeoutURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        calls = gson.fromJson(response, EvaluationModel2.class);
                        txtRef1.setText(calls.referee.get(0).refName);
                        txtRef1Tot.setText(calls.referee.get(0).total);
                        txtRef1Cor.setText(calls.referee.get(0).correct);
                        txtRef2.setText(calls.referee.get(1).refName);
                        txtRef2Tot.setText(calls.referee.get(1).total);
                        txtRef2Cor.setText(calls.referee.get(1).correct);
                        txtRef3.setText(calls.referee.get(2).refName);
                        txtRef3Tot.setText(calls.referee.get(2).total);
                        txtRef3Cor.setText(calls.referee.get(2).correct);

                        ArrayList<EvaluationDetails> dataModelArrayList = calls.evaluation;
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
                currentGame.setTimeInMillis(time);
                Map<String, String> params = new HashMap<String, String>();
                params.put("gameId", currentGame.getGameId());
                Log.e("gameId", currentGame.getGameId());
                params.put("period", String.valueOf(currentGame.getPeriod()));
               Log.e("period", String.valueOf(currentGame.getPeriod()));

                String timeText = txtMinute1.getText().toString() + txtMinute2.getText().toString() + ":" +
                        txtSecond1.getText().toString() + txtSecond2.getText().toString() + ":" +
                        txtMillis1.getText().toString() + txtMillis2.getText().toString();
                params.put("time", timeText);
                Log.e("time", timeText);

                params.put("timeInMillis", String.valueOf(currentGame.getTimeInMillis()));
                Log.e("timeInMillis", String.valueOf(currentGame.getTimeInMillis()));

                params.put("scoreA", String.valueOf(currentGame.getScoreA()));
                params.put("scoreB", String.valueOf(currentGame.getScoreB()));

                Log.e("scoreA", String.valueOf(currentGame.getScoreA()));
               Log.e("scoreB", String.valueOf(currentGame.getScoreB()));
                if(to){
                    params.put("call", "Team Timeout");
                    params.put("committingTeam", String.valueOf(id));
                    Log.e("call", "Team Timeout");
                    Log.e("committingTeam", String.valueOf(id));
                }else{
                    params.put("call", "TV Timeout");
                    Log.e("call", "TV Timeout");

                }
                return params;
            }

        };

        queue.add(putRequest);

    }
    private void ops(String op, boolean opType) {
        long value = 0;
        if (op.equals("minute")) {
            value = 60000;
        } else if (op.equals("second")) {
            value = 1000;
        } else if (op.equals("millis")) {
            value = 10;
        }
        if (opType) {
            time += value;
        } else {
            time -= value;
        }
        updateCountDownText();
    }

    private void startTimer() {
        cdTimer = new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                btnTeamTO.setTextColor(Color.parseColor("#E9841A"));
                btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                btnTVTO.setTextColor(Color.parseColor("#E9841A"));
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
                btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                btnTeamTO.setTextColor(Color.parseColor("#E9841A"));
                btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                btnTVTO.setTextColor(Color.parseColor("#E9841A"));
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
        btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
        btnTeamTO.setTextColor(Color.parseColor("#E9841A"));
        btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
        btnTVTO.setTextColor(Color.parseColor("#E9841A"));
    }

    private void pauseTimer() {
        if (timerRunning) {
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
        btnTeamTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
        btnTeamTO.setTextColor(Color.parseColor("#E9841A"));
        btnTVTO.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
        btnTVTO.setTextColor(Color.parseColor("#E9841A"));
        time = 600000;
        pauseTimer();
        updateCountDownText();
    }

    private void updateCountDownText() {
        long minutes = (time / 1000) / 60;
        long seconds = (time / 1000) % 60;
        long millis = time - (minutes * 60000) - (seconds * 1000);
        updateText(txtMinute1, txtMinute2, minutes);
        updateText(txtSecond1, txtSecond2, seconds);
        updateMillis(txtMillis1, txtMillis2, millis);
    }

    private void updateText(TextView txtView1, TextView txtView2, long timeValue) {
        if (timeValue >= 10) {
            txtView1.setText(Character.toString(Long.toString(timeValue).charAt(0)));
            txtView2.setText(Character.toString(Long.toString(timeValue).charAt(1)));
        } else {
            txtView1.setText("0");
            txtView2.setText(Long.toString(timeValue));
        }
    }

    private void updateMillis(TextView txtView1, TextView txtView2, long timeValue) {
        if (timeValue >= 100) {
            txtView1.setText(Character.toString(Long.toString(timeValue).charAt(0)));
            txtView2.setText(Character.toString(Long.toString(timeValue).charAt(1)));
        } else {
            txtView1.setText("0");
            txtView2.setText(Character.toString(Long.toString(timeValue).charAt(0)));
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Evaluation.this);
        builder.setCancelable(true);
        builder.setTitle("Exit Game");
        builder.setMessage("Are you sure you want to exit game (CODE:" + currentGame.getGameCode() + ")");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), EvaluatorActivity.class);
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
}
