package com.example.uaap;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Model.Call;
import com.example.uaap.Model.CallToIssue;
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.PersonDetails;
import com.example.uaap.Model.PlayersDetails;
import com.google.gson.Gson;

import org.apache.commons.text.WordUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

public class EvaluatorDetailsEdit extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private String playing;
    private String id;

    private String[] playingA;
    private String[] playingB;

    private Button btnRefA;
    private Button btnRefB;
    private Button btnRefC;
    private Button[] btnComm;
    private Button[] btnDis;
    private Button[] refButtons;
    private Button[] areaButtons;
    private Button[] aopButtons;
    private Button[] reviewButtons;
    private Button[] periodButtons;

    private Button btnStaffComm;
    private Button btnStaffDis;
    private Button btnChange;
    private Button btnSubmitEval;
    private Button btnFoul;
    private Button btnViolation;
    private Button btnAreaLead;
    private Button btnAreaCenter;
    private Button btnAreaTrail;
    private Button btnAoPLead;
    private Button btnAoPTrail;
    private Button btnAoPCenter;
    private Button btnReviewCC;
    private Button btnReviewINC;
    private Button btnReviewCFR;
    private Button btnReviewNCFR;
    private Button btnReviewIC;
    private Button btnReviewCNC;
    private Button btnMinUp;
    private Button btnMinDown;
    private Button btnSecUp;
    private Button btnSecDown;
    private Button btnMillisUp;
    private Button btnMillisDown;
    private Button btnQ1;
    private Button btnQ2;
    private Button btnQ3;
    private Button btnQ4;
    private Button btnOT;
    private TextView txtMinute1;
    private TextView txtMinute2;
    private TextView txtSecond1;
    private TextView txtSecond2;
    private TextView txtMillis1;
    private TextView txtMillis2;
    private TextView txtComment;
    private TextView txtTeamComm;
    private TextView txtTeamDis;
    private TextView txtFoul;
    private String SubmitEvalURL = "http://68.183.49.18/uaap/public/editEvaluation";
    private String GetEvalURL = "http://68.183.49.18/uaap/public/getEval";

    private CallToIssue callToIssue;

    private CurrentGame currentGame;
    private long time;
    private boolean changed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator_detailsv2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playing = extras.getString("playing");
            id = extras.getString("id");
        }
        Log.e("id", id);
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);
        Log.e("thisplaying", playing);
        Log.e("player", currentGame.playerA.get(0).jerseyNumber);


        time = currentGame.getTimeInMillis();
        btnComm = new Button[20];
        btnDis = new Button[20];
        refButtons = new Button[3];
        areaButtons = new Button[3];
        aopButtons = new Button[3];
        reviewButtons = new Button[6];
        periodButtons = new Button[5];

        btnRefA = findViewById(R.id.btnRefA);
        btnRefB = findViewById(R.id.btnRefB);
        btnRefC = findViewById(R.id.btnRefC);

        btnComm[0] = findViewById(R.id.btnComm1);
        btnComm[1] = findViewById(R.id.btnComm2);
        btnComm[2] = findViewById(R.id.btnComm3);
        btnComm[3] = findViewById(R.id.btnComm4);
        btnComm[4] = findViewById(R.id.btnComm5);
        btnComm[5] = findViewById(R.id.btnComm6);
        btnComm[6] = findViewById(R.id.btnComm7);
        btnComm[7] = findViewById(R.id.btnComm8);
        btnComm[8] = findViewById(R.id.btnComm9);
        btnComm[9] = findViewById(R.id.btnComm10);
        btnComm[10] = findViewById(R.id.btnComm11);
        btnComm[11] = findViewById(R.id.btnComm12);
        btnComm[12] = findViewById(R.id.btnComm13);
        btnComm[13] = findViewById(R.id.btnComm14);
        btnComm[14] = findViewById(R.id.btnComm15);
        btnComm[15] = findViewById(R.id.btnComm16);
        btnComm[16] = findViewById(R.id.btnComm17);
        btnComm[17] = findViewById(R.id.btnComm18);
        btnComm[18] = findViewById(R.id.btnComm19);
        btnComm[19] = findViewById(R.id.btnComm20);

        btnDis[0] = findViewById(R.id.btnDis1);
        btnDis[1] = findViewById(R.id.btnDis2);
        btnDis[2] = findViewById(R.id.btnDis3);
        btnDis[3] = findViewById(R.id.btnDis4);
        btnDis[4] = findViewById(R.id.btnDis5);
        btnDis[5] = findViewById(R.id.btnDis6);
        btnDis[6] = findViewById(R.id.btnDis7);
        btnDis[7] = findViewById(R.id.btnDis8);
        btnDis[8] = findViewById(R.id.btnDis9);
        btnDis[9] = findViewById(R.id.btnDis10);
        btnDis[10] = findViewById(R.id.btnDis11);
        btnDis[11] = findViewById(R.id.btnDis12);
        btnDis[12] = findViewById(R.id.btnDis13);
        btnDis[13] = findViewById(R.id.btnDis14);
        btnDis[14] = findViewById(R.id.btnDis15);
        btnDis[15] = findViewById(R.id.btnDis16);
        btnDis[16] = findViewById(R.id.btnDis17);
        btnDis[17] = findViewById(R.id.btnDis18);
        btnDis[18] = findViewById(R.id.btnDis19);
        btnDis[19] = findViewById(R.id.btnDis20);

        btnChange = findViewById(R.id.btnChange);
        btnStaffComm = findViewById(R.id.btnStaffComm);
        btnStaffDis = findViewById(R.id.btnStaffDis);
        btnFoul = findViewById(R.id.btnFoul);
        btnViolation = findViewById(R.id.btnViolation);
        btnSubmitEval = findViewById(R.id.btnSubmitEval);
        btnAreaLead = findViewById(R.id.btnAreaLead);
        btnAreaCenter = findViewById(R.id.btnAreaCenter);
        btnAreaTrail = findViewById(R.id.btnAreaTrail);
        btnAoPLead = findViewById(R.id.btnAoPLead);
        btnAoPCenter = findViewById(R.id.btnAoPCenter);
        btnAoPTrail = findViewById(R.id.btnAoPTrial);
        btnReviewCC = findViewById(R.id.btnReviewCC);
        btnReviewINC = findViewById(R.id.btnReviewINC);
        btnReviewCFR = findViewById(R.id.btnReviewCFR);
        btnReviewNCFR = findViewById(R.id.btnReviewNCFR);
        btnReviewIC = findViewById(R.id.btnReviewIC);
        btnReviewCNC = findViewById(R.id.btnReviewCNC);
        btnMinUp = findViewById(R.id.btnMinUp);
        btnMinDown = findViewById(R.id.btnMinDown);
        btnSecUp = findViewById(R.id.btnSecUp);
        btnSecDown = findViewById(R.id.btnSecDown);
        btnMillisUp = findViewById(R.id.btnMillisUp);
        btnMillisDown = findViewById(R.id.btnMillisDown);
        btnQ1 = findViewById(R.id.btnQ1);
        btnQ2 = findViewById(R.id.btnQ2);
        btnQ3 = findViewById(R.id.btnQ3);
        btnQ4 = findViewById(R.id.btnQ4);
        btnOT = findViewById(R.id.btnOT);
        txtMinute1 = findViewById(R.id.txtMinute1);
        txtMinute2 = findViewById(R.id.txtMinute2);
        txtSecond1 = findViewById(R.id.txtSecond1);
        txtSecond2 = findViewById(R.id.txtSecond2);
        txtMillis1 = findViewById(R.id.txtMillis1);
        txtMillis2 = findViewById(R.id.txtMillis2);
        txtComment = findViewById(R.id.txtComment);
        txtTeamDis = findViewById(R.id.txtTeamDis);
        txtTeamComm = findViewById(R.id.txtTeamComm);
        txtFoul = findViewById(R.id.txtFoul);
        initTime();
        refButtons[0] = btnRefA;
        refButtons[1] = btnRefB;
        refButtons[2] = btnRefC;
        areaButtons[0] = btnAreaLead;
        areaButtons[1] = btnAreaCenter;
        areaButtons[2] = btnAreaTrail;
        aopButtons[0] = btnAoPLead;
        aopButtons[1] = btnAoPCenter;
        aopButtons[2] = btnAoPTrail;
        reviewButtons[0] = btnReviewCC;
        reviewButtons[1] = btnReviewIC;
        reviewButtons[2] = btnReviewCFR;
        reviewButtons[3] = btnReviewNCFR;
        reviewButtons[4] = btnReviewCNC;
        reviewButtons[5] = btnReviewINC;
        periodButtons[0] = btnQ1;
        periodButtons[1] = btnQ2;
        periodButtons[2] = btnQ3;
        periodButtons[3] = btnQ4;
        periodButtons[4] = btnOT;
        playingA = new String[currentGame.playerA.size()];
        playingB = new String[currentGame.playerB.size()];

        for (int i = 0; i < currentGame.playerA.size(); i++) {
            playingA[i] = currentGame.playerA.get(i).jerseyNumber;
        }
        for (int i = 0; i < currentGame.playerB.size(); i++) {
            playingB[i] = currentGame.playerB.get(i).jerseyNumber;
        }

        getThisGame();

        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            btnComm[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!changed) {
                        if(currentGame.getColorTeamA()==Color.WHITE){
                            btnStaffComm.setBackgroundColor(Color.WHITE);
                            btnStaffComm.setTextColor(Color.BLACK);
                        }else{
                            btnStaffComm.setBackgroundColor(currentGame.getColorTeamA());
                            btnStaffComm.setTextColor(Color.WHITE);
                        }
                        if (finalI < currentGame.playerA.size()) {
                            clearPlayer(true);
                            playerSelect(true, finalI);
                        }
                    } else {
                        if(currentGame.getColorTeamB()==Color.WHITE){
                            btnStaffComm.setBackgroundColor(Color.WHITE);
                            btnStaffComm.setTextColor(Color.BLACK);
                        }else{
                            btnStaffComm.setBackgroundColor(currentGame.getColorTeamB());
                            btnStaffComm.setTextColor(Color.WHITE);
                        }
                        if (finalI < currentGame.playerB.size()) {
                            clearPlayer(true);
                            playerSelect(true, finalI);
                        }
                    }

                }
            });
            btnComm[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callToIssue.setCommittingType(null);
                    callToIssue.setCommitting(null);
                    callToIssue.setCommittingTeam(null);
                    if (!changed) {
                        if (finalI < currentGame.playerA.size()) {
                            clearPlayer(true);
                        }
                    } else {
                        if (finalI < currentGame.playerB.size()) {
                            clearPlayer(true);
                        }
                    }
                    return true;
                }
            });

            btnDis[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!changed) {
                        if(currentGame.getColorTeamB()==Color.WHITE){
                            btnStaffDis.setBackgroundColor(Color.WHITE);
                            btnStaffDis.setTextColor(Color.BLACK);
                        }else{
                            btnStaffDis.setBackgroundColor(currentGame.getColorTeamB());
                            btnStaffDis.setTextColor(Color.WHITE);
                        }
                        if (finalI < currentGame.playerB.size()) {
                            clearPlayer(false);
                            playerSelect(false, finalI);
                        }
                    } else {
                        if(currentGame.getColorTeamA()==Color.WHITE){
                            btnStaffDis.setBackgroundColor(Color.WHITE);
                            btnStaffDis.setTextColor(Color.BLACK);
                        }else{
                            btnStaffDis.setBackgroundColor(currentGame.getColorTeamA());
                            btnStaffDis.setTextColor(Color.WHITE);
                        }
                        if (finalI < currentGame.playerB.size()) {
                            clearPlayer(false);
                            playerSelect(false, finalI);
                        }
                    }
                }
            });
            btnDis[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callToIssue.setDisType(null);
                    callToIssue.setDis(null);
                    callToIssue.setDisTeam(null);
                    if (!changed) {
                        if (finalI < currentGame.playerB.size()) {
                            clearPlayer(false);
                        }
                    } else {
                        if (finalI < currentGame.playerA.size()) {
                            clearPlayer(false);
                        }
                    }
                    return true;
                }
            });

        }
        btnViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCall(false);

            }
        });
        btnFoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCall(true);
//                    genFoul(getResources().getStringArray(R.array.foul));\
            }
        });
        btnStaffComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staff(true);
            }
        });
        btnStaffDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staff(false);
            }
        });
        btnStaffComm.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callToIssue.setCommittingTeam(null);
                callToIssue.setCommittingType(null);
                callToIssue.setCommitting(null);
                if (!changed) {
                    btnStaffComm.setBackgroundColor(currentGame.getColorTeamA());
                    if (currentGame.getColorTeamA() == Color.WHITE) {
                        btnStaffComm.setTextColor(Color.BLACK);
                    } else {
                        btnStaffComm.setTextColor(Color.WHITE);
                    }
                } else {
                    btnStaffComm.setBackgroundColor(currentGame.getColorTeamB());
                    if (currentGame.getColorTeamB() == Color.WHITE) {
                        btnStaffComm.setTextColor(Color.BLACK);
                    } else {
                        btnStaffComm.setTextColor(Color.WHITE);
                    }
                }
                return true;
            }
        });
        btnStaffDis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callToIssue.setDisTeam(null);
                callToIssue.setDisType(null);
                callToIssue.setDis(null);
                if (!changed) {
                    btnStaffDis.setBackgroundColor(currentGame.getColorTeamB());
                    if (currentGame.getColorTeamB() == Color.WHITE) {
                        btnStaffDis.setTextColor(Color.BLACK);
                    } else {
                        btnStaffDis.setTextColor(Color.WHITE);
                    }
                } else {
                    btnStaffDis.setBackgroundColor(currentGame.getColorTeamA());
                    if (currentGame.getColorTeamA() == Color.WHITE) {
                        btnStaffDis.setTextColor(Color.BLACK);
                    } else {
                        btnStaffDis.setTextColor(Color.WHITE);
                    }
                }
                return true;
            }
        });
        btnSubmitEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEval();
            }
        });

        for (int i = 0; i < refButtons.length; i++) {
            final int finalI = i;
            refButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInfo(currentGame.referee.get(finalI).name, finalI, "referee", refButtons);

                }
            });
        }
        for (int i = 0; i < areaButtons.length; i++) {
            final int finalI = i;
            areaButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String aop = areaButtons[finalI].getText().toString();
                    aop = aop.toLowerCase();
                    aop = aop.substring(0, 1).toUpperCase() + aop.substring(1);
                    setInfo(aop, finalI, "area", areaButtons);

                }
            });
            areaButtons[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    areaButtons[finalI].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    callToIssue.setArea(null);
                    return true;
                }
            });
        }
        for (int i = 0; i < aopButtons.length; i++) {
            final int finalI = i;
            aopButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String aop = aopButtons[finalI].getText().toString();
                    aop = aop.toLowerCase();
                    aop = aop.substring(0, 1).toUpperCase() + aop.substring(1);
                    setInfo(aop, finalI, "aop", aopButtons);

                }
            });
            aopButtons[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    aopButtons[finalI].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    callToIssue.setAreaOfPlay(null);
                    return true;
                }
            });
        }
        for (int i = 0; i < reviewButtons.length; i++) {
            final int finalI = i;
            reviewButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("REVIEW", reviewButtons[finalI].getText().toString());
                    setInfo(reviewButtons[finalI].getText().toString(), finalI, "review", reviewButtons);
                }
            });
        }
        for (int i = 0; i < periodButtons.length; i++) {
            final int finalI = i;
            periodButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (periodButtons[finalI].getText().toString().equals("OT")) {
                        setInfo(periodButtons[finalI].getText().toString(), finalI, "period", periodButtons);
                    } else {
                        setInfo("Q" + periodButtons[finalI].getText().toString(), finalI, "period", periodButtons);

                    }

                }
            });

        }

        btnMinUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("minute", true);

            }
        });

        btnSecUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("second", true);

            }
        });

        btnMillisUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("millis", true);

            }
        });

        btnMinDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("minute", false);


            }
        });

        btnSecDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("second", false);

            }
        });

        btnMillisDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops("millis", false);

            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToIssue.setCommittingTeam(null);
                callToIssue.setCommittingType(null);
                callToIssue.setCommitting(null);
                callToIssue.setDisTeam(null);
                callToIssue.setDisType(null);
                callToIssue.setDis(null);
                changed = !changed;
                setPlayers();
            }
        });


    }

    private void getThisGame() {
        btnRefA.setText(currentGame.referee.get(0).name);
        btnRefB.setText(currentGame.referee.get(1).name);
        btnRefC.setText(currentGame.referee.get(2).name);
        RequestQueue queue = Volley.newRequestQueue(EvaluatorDetailsEdit.this);
        StringRequest putRequest = new StringRequest(Request.Method.POST, GetEvalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response", response);
                        Gson gson = new Gson();
                        Call call = gson.fromJson(response, Call.class);
                        callToIssue = call.result.get(0);
                        Log.e("type", callToIssue.getCommittingType());
                        if(callToIssue.getCommittingTeam().equals(currentGame.getTeamAId())){
                           changed = false;
                        }else{
                            changed = true;
                        }
                        setInitPlayers();
                        if(callToIssue.getCommittingType().equals("player")){
                           if(!changed){
                               int index = getIndexOfPlayers(callToIssue.getCommitting(),currentGame.getPlayerA());
                               if(currentGame.getColorTeamA()==Color.WHITE){
                                   btnComm[index].setBackgroundColor(Color.BLACK);
                                   btnComm[index].setTextColor(Color.WHITE);
                               }else{
                                   btnComm[index].setBackgroundColor(Color.WHITE);
                                   btnComm[index].setTextColor(currentGame.getColorTeamA());
                               }
                           }else{
                               int index = getIndexOfPlayers(callToIssue.getCommitting(),currentGame.getPlayerB());
                               if(currentGame.getColorTeamB()==Color.WHITE){
                                   btnComm[index].setBackgroundColor(Color.BLACK);
                                   btnComm[index].setTextColor(Color.WHITE);
                               }else{
                                   btnComm[index].setBackgroundColor(Color.WHITE);
                                   btnComm[index].setTextColor(currentGame.getColorTeamB());
                               }
                           }
                        }else{
                            if(!changed){
                                if(currentGame.getColorTeamA()==Color.WHITE){
                                    btnStaffComm.setBackgroundColor(Color.BLACK);
                                    btnStaffComm.setTextColor(Color.WHITE);
                                }else{
                                    btnStaffComm.setBackgroundColor(Color.WHITE);
                                    btnStaffComm.setTextColor(currentGame.getColorTeamA());
                                }
                            }else{
                                if(currentGame.getColorTeamB()==Color.WHITE){
                                    btnStaffComm.setBackgroundColor(Color.BLACK);
                                    btnStaffComm.setTextColor(Color.WHITE);
                                }else{
                                    btnStaffComm.setBackgroundColor(Color.WHITE);
                                    btnStaffComm.setTextColor(currentGame.getColorTeamB());
                                }
                            }
                        }
                        if(callToIssue.getDis()!=null){
                            if(callToIssue.getDisType().equals("player")){
                                if(!changed){
                                    int index = getIndexOfPlayers(callToIssue.getDis(),currentGame.getPlayerB());
                                    if(currentGame.getColorTeamB()==Color.WHITE){
                                        btnDis[index].setBackgroundColor(Color.BLACK);
                                        btnDis[index].setTextColor(Color.WHITE);
                                    }else{
                                        btnDis[index].setBackgroundColor(Color.WHITE);
                                        btnDis[index].setTextColor(currentGame.getColorTeamB());
                                    }
                                }else{
                                    int index = getIndexOfPlayers(callToIssue.getDis(),currentGame.getPlayerA());
                                    if(currentGame.getColorTeamA()==Color.WHITE){
                                        btnDis[index].setBackgroundColor(Color.BLACK);
                                        btnDis[index].setTextColor(Color.WHITE);
                                    }else{
                                        btnDis[index].setBackgroundColor(Color.WHITE);
                                        btnDis[index].setTextColor(currentGame.getColorTeamA());
                                    }
                                }
                            }else{
                                if(!changed){
                                    if(currentGame.getColorTeamB()==Color.WHITE){
                                        btnStaffDis.setBackgroundColor(Color.BLACK);
                                        btnStaffDis.setTextColor(Color.WHITE);
                                    }else{
                                        btnStaffDis.setBackgroundColor(Color.WHITE);
                                        btnStaffDis.setTextColor(currentGame.getColorTeamB());
                                    }
                                }else{
                                    if(currentGame.getColorTeamA()==Color.WHITE){
                                        btnStaffDis.setBackgroundColor(Color.BLACK);
                                        btnStaffDis.setTextColor(Color.WHITE);
                                    }else{
                                        btnStaffDis.setBackgroundColor(Color.WHITE);
                                        btnStaffDis.setTextColor(currentGame.getColorTeamA());
                                    }
                                }
                            }
                        }
                        //timie
                        String timeString = callToIssue.getTime();
                        String[] timeSep = timeString.split(":");
                        time = (Long.valueOf(timeSep[0])*60000)+(Long.valueOf(timeSep[1])*1000)+(Long.valueOf(timeSep[2])*10);
                        initTime();

                        //foul
                        txtFoul.setText(callToIssue.getCall());
                        if(callToIssue.getCallType().equals("Foul")){
                            btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                            btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                            btnFoul.setTextColor(Color.parseColor("#FFFFFF"));
                        }else{
                            btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                            btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                            btnViolation.setTextColor(Color.parseColor("#FFFFFF"));

                        }

                        //referee
                        int refIndex = getIndexOfReferee(callToIssue.getRefereeId(),currentGame.getReferee());
                        refButtons[refIndex].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                        refButtons[refIndex].setTextColor(Color.parseColor("#FFFFFF"));

                        String[] areas = {"Lead", "Center", "Trail"};
                        //area
                        int index=0;
                        if(callToIssue.getArea()!=null){
                            for (int i=0;i<areas.length;i++) {
                                if (areas[i].equals(callToIssue.getArea())) {
                                    index = i;
                                    break;
                                }
                            }
                            areaButtons[index].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                            areaButtons[index].setTextColor(Color.parseColor("#FFFFFF"));

                        }
                        //aop
                        if(callToIssue.getAreaOfPlay()!=null){
                            for (int i=0;i<areas.length;i++) {
                                if (areas[i].equals(callToIssue.getAreaOfPlay())) {
                                    index = i;
                                    break;
                                }
                            }
                            aopButtons[index].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                            aopButtons[index].setTextColor(Color.parseColor("#FFFFFF"));

                        }

                        //review
                        String[] rev = {"CC", "IC", "CFR", "NCFR", "CNC", "INC"};
                        for (int i=0;i<rev.length;i++) {
                            if (rev[i].equals(callToIssue.getReviewDecision())) {
                                index = i;
                                break;
                            }
                        }
                        reviewButtons[index].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                        reviewButtons[index].setTextColor(Color.parseColor("#FFFFFF"));

                        if(callToIssue.getComment()!=null){
                            txtComment.setText(callToIssue.getComment());
                        }

                        periodButtons[callToIssue.getPeriod()].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                        periodButtons[callToIssue.getPeriod()].setTextColor(Color.parseColor("#FFFFFF"));
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
                params.put("id", id);
                return params;
            }

        };

        queue.add(putRequest);

    }
    private int getIndexOfPlayers(String playerId, ArrayList<PlayersDetails> players) {
        for(int i=0;i<players.size();i++){
            if(players.get(i).id.equals(playerId)){
                return i;
            }
        }
        return -1;
    }
    private int getIndexOfReferee(String refereeId, ArrayList<PersonDetails> referees) {
        for(int i=0;i<referees.size();i++){
            if(referees.get(i).id.equals(refereeId)){
                return i;
            }
        }
        return -1;
    }
    private void setInitPlayers(){
        for (int i = 0; i < 20; i++) {
            btnComm[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnComm[i].setText("");
            btnDis[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnDis[i].setText("");

        }
        if (!changed) {
            if (currentGame.getColorTeamA() == Color.WHITE) {
                btnStaffComm.setTextColor(Color.BLACK);
            } else {
                btnStaffComm.setTextColor(Color.WHITE);
            }
            if (currentGame.getColorTeamB() == Color.WHITE) {
                btnStaffDis.setTextColor(Color.BLACK);
            } else {
                btnStaffDis.setTextColor(Color.WHITE);
            }
            btnStaffComm.setBackgroundColor(currentGame.getColorTeamA());
            btnStaffDis.setBackgroundColor(currentGame.getColorTeamB());
            txtTeamComm.setText(currentGame.getTeamA());
            txtTeamDis.setText(currentGame.getTeamB());
            for (int i = 0; i < playingA.length; i++) {
                btnComm[i].setBackgroundColor(currentGame.getColorTeamA());
                if (currentGame.getColorTeamA() == Color.WHITE) {
                    btnComm[i].setTextColor(Color.BLACK);
                } else {
                    btnComm[i].setTextColor(Color.WHITE);
                }
                btnComm[i].setText(playingA[i]);

            }
            for (int i = 0; i < playingB.length; i++) {
                btnDis[i].setBackgroundColor(currentGame.getColorTeamB());
                if (currentGame.getColorTeamB() == Color.WHITE) {
                    btnDis[i].setTextColor(Color.BLACK);
                } else {
                    btnDis[i].setTextColor(Color.WHITE);
                }
                btnDis[i].setText(playingB[i]);
            }
        } else {
            if (currentGame.getColorTeamB() == Color.WHITE) {
                btnStaffComm.setTextColor(Color.BLACK);
            } else {
                btnStaffComm.setTextColor(Color.WHITE);
            }
            if (currentGame.getColorTeamA() == Color.WHITE) {
                btnStaffDis.setTextColor(Color.BLACK);
            } else {
                btnStaffDis.setTextColor(Color.WHITE);
            }
            txtTeamComm.setText(currentGame.getTeamB());
            txtTeamDis.setText(currentGame.getTeamA());
            btnStaffComm.setBackgroundColor(currentGame.getColorTeamB());
            btnStaffDis.setBackgroundColor(currentGame.getColorTeamA());
            for (int i = 0; i < playingB.length; i++) {
                btnComm[i].setBackgroundColor(currentGame.getColorTeamB());
                if (currentGame.getColorTeamB() == Color.WHITE) {
                    btnComm[i].setTextColor(Color.BLACK);
                } else {
                    btnComm[i].setTextColor(Color.WHITE);
                }
                btnComm[i].setText(playingB[i]);

            }
            for (int i = 0; i < playingA.length; i++) {
                btnDis[i].setBackgroundColor(currentGame.getColorTeamA());
                if (currentGame.getColorTeamA() == Color.WHITE) {
                    btnDis[i].setTextColor(Color.BLACK);
                } else {
                    btnDis[i].setTextColor(Color.WHITE);
                }
                btnDis[i].setText(playingA[i]);
            }
        }

    }
    private void setPlayers() {
        for (int i = 0; i < 20; i++) {
            btnComm[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnComm[i].setText("");
            btnComm[i].setElevation(0);
            btnDis[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnDis[i].setText("");
            btnDis[i].setElevation(0);
        }


        callToIssue.setCommitting(null);
        callToIssue.setCommittingTeam(null);
        callToIssue.setCommittingType(null);
        callToIssue.setDisType(null);
        callToIssue.setDisTeam(null);
        callToIssue.setDis(null);
        if (!changed) {
            if (currentGame.getColorTeamA() == Color.WHITE) {
                btnStaffComm.setTextColor(Color.BLACK);
            } else {
                btnStaffComm.setTextColor(Color.WHITE);
            }
            if (currentGame.getColorTeamB() == Color.WHITE) {
                btnStaffDis.setTextColor(Color.BLACK);
            } else {
                btnStaffDis.setTextColor(Color.WHITE);
            }
            btnStaffComm.setBackgroundColor(currentGame.getColorTeamA());
            btnStaffDis.setBackgroundColor(currentGame.getColorTeamB());
            txtTeamComm.setText(currentGame.getTeamA());
            txtTeamDis.setText(currentGame.getTeamB());
            for (int i = 0; i < playingA.length; i++) {
                btnComm[i].setBackgroundColor(currentGame.getColorTeamA());
                if (currentGame.getColorTeamA() == Color.WHITE) {
                    btnComm[i].setTextColor(Color.BLACK);
                } else {
                    btnComm[i].setTextColor(Color.WHITE);
                }
                btnComm[i].setText(playingA[i]);
                btnComm[i].setElevation(3);

            }
            for (int i = 0; i < playingB.length; i++) {
                btnDis[i].setBackgroundColor(currentGame.getColorTeamB());
                if (currentGame.getColorTeamB() == Color.WHITE) {
                    btnDis[i].setTextColor(Color.BLACK);
                } else {
                    btnDis[i].setTextColor(Color.WHITE);
                }
                btnDis[i].setText(playingB[i]);
                btnDis[i].setElevation(3);

            }
        } else {
            if (currentGame.getColorTeamB() == Color.WHITE) {
                btnStaffComm.setTextColor(Color.BLACK);
            } else {
                btnStaffComm.setTextColor(Color.WHITE);
            }
            if (currentGame.getColorTeamA() == Color.WHITE) {
                btnStaffDis.setTextColor(Color.BLACK);
            } else {
                btnStaffDis.setTextColor(Color.WHITE);
            }
            txtTeamComm.setText(currentGame.getTeamB());
            txtTeamDis.setText(currentGame.getTeamA());
            btnStaffComm.setBackgroundColor(currentGame.getColorTeamB());
            btnStaffDis.setBackgroundColor(currentGame.getColorTeamA());
            for (int i = 0; i < playingB.length; i++) {
                btnComm[i].setBackgroundColor(currentGame.getColorTeamB());
                if (currentGame.getColorTeamB() == Color.WHITE) {
                    btnComm[i].setTextColor(Color.BLACK);
                } else {
                    btnComm[i].setTextColor(Color.WHITE);
                }
                btnComm[i].setText(playingB[i]);
                btnComm[i].setElevation(3);

            }
            for (int i = 0; i < playingA.length; i++) {
                btnDis[i].setBackgroundColor(currentGame.getColorTeamA());
                if (currentGame.getColorTeamA() == Color.WHITE) {
                    btnDis[i].setTextColor(Color.BLACK);
                } else {
                    btnDis[i].setTextColor(Color.WHITE);
                }
                btnDis[i].setText(playingA[i]);
                btnDis[i].setElevation(3);

            }
        }


    }

    private void clearPlayer(boolean commDis) {
        if (commDis) {
            if (!changed) {
                for (int i = 0; i < playingA.length; i++) {
                    btnComm[i].setBackgroundColor(currentGame.getColorTeamA());
                    if (currentGame.getColorTeamA() == Color.WHITE) {
                        btnComm[i].setTextColor(Color.BLACK);
                    } else {
                        btnComm[i].setTextColor(Color.WHITE);
                    }
                    btnComm[i].setText(playingA[i]);

                }
            } else {
                for (int i = 0; i < playingB.length; i++) {
                    btnComm[i].setBackgroundColor(currentGame.getColorTeamB());
                    if (currentGame.getColorTeamB() == Color.WHITE) {
                        btnComm[i].setTextColor(Color.BLACK);
                    } else {
                        btnComm[i].setTextColor(Color.WHITE);
                    }
                    btnComm[i].setText(playingB[i]);

                }
            }
        } else {
            if (!changed) {
                for (int i = 0; i < playingB.length; i++) {
                    btnDis[i].setBackgroundColor(currentGame.getColorTeamB());
                    if (currentGame.getColorTeamB() == Color.WHITE) {
                        btnDis[i].setTextColor(Color.BLACK);
                    } else {
                        btnDis[i].setTextColor(Color.WHITE);
                    }
                    btnDis[i].setText(playingB[i]);

                }
            } else {
                for (int i = 0; i < playingA.length; i++) {
                    btnDis[i].setBackgroundColor(currentGame.getColorTeamA());
                    if (currentGame.getColorTeamA() == Color.WHITE) {
                        btnDis[i].setTextColor(Color.BLACK);
                    } else {
                        btnDis[i].setTextColor(Color.WHITE);
                    }
                    btnDis[i].setText(playingA[i]);

                }
            }
        }
    }


    private void playerSelect(boolean commDis, int position) {
        if (commDis) {
            if (!changed) {
                if (currentGame.getColorTeamA() == Color.WHITE) {
                    btnComm[position].setBackgroundColor(Color.BLACK);
                    btnComm[position].setTextColor(Color.WHITE);
                } else {
                    btnComm[position].setBackgroundColor(Color.WHITE);
                    btnComm[position].setTextColor(currentGame.getColorTeamA());
                }
                callToIssue.setCommittingTeam(currentGame.getTeamAId());
                callToIssue.setCommittingType("player");
                callToIssue.setCommitting(currentGame.playerA.get(position).id);
                Toast.makeText(getApplicationContext(),
                        "You selected "+currentGame.playerA.get(position).jerseyNumber +" "+currentGame.playerA.get(position).name,
                        Toast.LENGTH_SHORT).show();
            } else {
                if (currentGame.getColorTeamB() == Color.WHITE) {
                    btnComm[position].setBackgroundColor(Color.BLACK);
                    btnComm[position].setTextColor(Color.WHITE);
                } else {
                    btnComm[position].setBackgroundColor(Color.WHITE);
                    btnComm[position].setTextColor(currentGame.getColorTeamB());
                }
                callToIssue.setCommittingTeam(currentGame.getTeamBId());
                callToIssue.setCommittingType("player");
                callToIssue.setCommitting(currentGame.playerB.get(position).id);
                Toast.makeText(getApplicationContext(),
                        "You selected "+currentGame.playerB.get(position).jerseyNumber +" "+currentGame.playerB.get(position).name,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!changed) {
                if (currentGame.getColorTeamB() == Color.WHITE) {
                    btnDis[position].setBackgroundColor(Color.BLACK);
                    btnDis[position].setTextColor(Color.WHITE);
                } else {
                    btnDis[position].setBackgroundColor(Color.WHITE);
                    btnDis[position].setTextColor(currentGame.getColorTeamB());
                }
                callToIssue.setDisTeam(currentGame.getTeamBId());
                callToIssue.setDisType("player");
                callToIssue.setDis(currentGame.playerB.get(position).id);
                Toast.makeText(getApplicationContext(),
                        "You selected "+currentGame.playerB.get(position).jerseyNumber +" "+currentGame.playerB.get(position).name,
                        Toast.LENGTH_SHORT).show();
            } else {
                if (currentGame.getColorTeamA() == Color.WHITE) {
                    btnDis[position].setBackgroundColor(Color.BLACK);
                    btnDis[position].setTextColor(Color.WHITE);
                } else {
                    btnDis[position].setBackgroundColor(Color.WHITE);
                    btnDis[position].setTextColor(currentGame.getColorTeamA());
                }
                callToIssue.setDisTeam(currentGame.getTeamAId());
                callToIssue.setDisType("player");
                callToIssue.setDis(currentGame.playerA.get(position).id);
                Toast.makeText(getApplicationContext(),
                        "You selected "+currentGame.playerA.get(position).jerseyNumber +" "+currentGame.playerA.get(position).name,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void staff(final boolean commDis) {
        // custom dialog
        final Dialog dialog = new Dialog(EvaluatorDetailsEdit.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_staff);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(commDis){
                    callToIssue.setCommitting(null);
                    callToIssue.setCommittingType(null);
                    callToIssue.setCommittingTeam(null);
                    if(!changed){
                        if(currentGame.getColorTeamA()==Color.WHITE){
                            btnStaffComm.setBackgroundColor(Color.WHITE);
                            btnStaffComm.setTextColor(Color.BLACK);
                        }else{
                            btnStaffComm.setBackgroundColor(currentGame.getColorTeamA());
                            btnStaffComm.setTextColor(Color.WHITE);
                        }
                    }else{
                        if(currentGame.getColorTeamB()==Color.WHITE){
                            btnStaffComm.setBackgroundColor(Color.WHITE);
                            btnStaffComm.setTextColor(Color.BLACK);
                        }else{
                            btnStaffComm.setBackgroundColor(currentGame.getColorTeamB());
                            btnStaffComm.setTextColor(Color.WHITE);
                        }
                    }
                }else{

                    callToIssue.setDis(null);
                    callToIssue.setDisType(null);
                    callToIssue.setDisTeam(null);
                    if(!changed){
                        if(currentGame.getColorTeamB()==Color.WHITE){
                            btnStaffDis.setBackgroundColor(Color.WHITE);
                            btnStaffDis.setTextColor(Color.BLACK);
                        }else{
                            btnStaffDis.setBackgroundColor(currentGame.getColorTeamB());
                            btnStaffDis.setTextColor(Color.WHITE);
                        }
                    }else{
                        if(currentGame.getColorTeamA()==Color.WHITE){
                            btnStaffDis.setBackgroundColor(Color.WHITE);
                            btnStaffDis.setTextColor(Color.BLACK);
                        }else{
                            btnStaffDis.setBackgroundColor(currentGame.getColorTeamA());
                            btnStaffDis.setTextColor(Color.WHITE);
                        }
                    }
                }
            }
        });
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        if(commDis){
            if (!changed) {
                for (int i = 0; i < currentGame.staffA.size(); i++) {
                    RadioButton rb = new RadioButton(EvaluatorDetailsEdit.this); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(currentGame.staffA.get(i).name + " (" + currentGame.getTeamA() + ")");
                    rb.setId(Integer.parseInt(currentGame.staffA.get(i).id));
                    rg.addView(rb);
                }
            } else {
                for (int i = 0; i < currentGame.staffB.size(); i++) {
                    RadioButton rb = new RadioButton(EvaluatorDetailsEdit.this); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(currentGame.staffB.get(i).name + " (" + currentGame.getTeamB() + ")");
                    rb.setId(Integer.parseInt(currentGame.staffB.get(i).id));
                    rg.addView(rb);
                }
            }
        }else {
            if (!changed) {
                for (int i = 0; i < currentGame.staffB.size(); i++) {
                    RadioButton rb = new RadioButton(EvaluatorDetailsEdit.this); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(currentGame.staffB.get(i).name + " (" + currentGame.getTeamB() + ")");
                    rb.setId(Integer.parseInt(currentGame.staffB.get(i).id));
                    rg.addView(rb);
                }
            } else {
                for (int i = 0; i < currentGame.staffA.size(); i++) {
                    RadioButton rb = new RadioButton(EvaluatorDetailsEdit.this); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(currentGame.staffA.get(i).name + " (" + currentGame.getTeamA() + ")");
                    rb.setId(Integer.parseInt(currentGame.staffA.get(i).id));
                    rg.addView(rb);
                }
            }
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (commDis) {
                    callToIssue.setCommittingType("staff");
                    callToIssue.setCommitting(String.valueOf(checkedId));
                } else {
                    callToIssue.setDisType("staff");
                    callToIssue.setDis(String.valueOf(checkedId));
                }
                if (commDis) {
                    clearPlayer(true);
                    if (!changed) {

                        if (currentGame.getColorTeamA() == Color.WHITE) {
                            btnStaffComm.setBackgroundColor(Color.BLACK);
                            btnStaffComm.setTextColor(Color.WHITE);
                        } else {
                            btnStaffComm.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            btnStaffComm.setTextColor(currentGame.getColorTeamA());
                        }
                        callToIssue.setCommittingTeam(currentGame.getTeamAId());

                    } else {
                        clearPlayer(true);
                        clearPlayer(false);
                        if (currentGame.getColorTeamB() == Color.WHITE) {
                            btnStaffComm.setBackgroundColor(Color.BLACK);
                            btnStaffComm.setTextColor(Color.WHITE);
                        } else {
                            btnStaffComm.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            btnStaffComm.setTextColor(currentGame.getColorTeamB());
                        }
                        callToIssue.setCommittingTeam(currentGame.getTeamBId());
                    }
                } else {
                    clearPlayer(false);

                    if (!changed) {

                        if (currentGame.getColorTeamB() == Color.WHITE) {
                            btnStaffDis.setBackgroundColor(Color.BLACK);
                            btnStaffDis.setTextColor(Color.WHITE);
                        } else {
                            btnStaffDis.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            btnStaffDis.setTextColor(currentGame.getColorTeamB());
                        }
                        callToIssue.setDisTeam(currentGame.getTeamBId());

                    } else {
                        clearPlayer(true);
                        clearPlayer(false);
                        if (currentGame.getColorTeamA() == Color.WHITE) {
                            btnStaffDis.setBackgroundColor(Color.BLACK);
                            btnStaffDis.setTextColor(Color.WHITE);
                        } else {
                            btnStaffDis.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            btnStaffDis.setTextColor(currentGame.getColorTeamA());
                        }
                        callToIssue.setDisTeam(currentGame.getTeamAId());
                    }
                }

                dialog.dismiss();

            }
        });

        dialog.show();

    }


    public void onItemClick(AdapterView parent, View v, int position, long id) {
        TextView textView = (TextView) v.findViewById(R.id.txtFoul);
        callToIssue.setCall(WordUtils.capitalizeFully(textView.getText().toString()));
    }

    private void showCall(final boolean foulVio) {
        final Dialog dialog = new Dialog(EvaluatorDetailsEdit.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_staff);

        final String[] call = new String[1];
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        final String[] foulVioList;
        if (foulVio) {
            foulVioList = getResources().getStringArray(R.array.foul);

        } else {
            foulVioList = getResources().getStringArray(R.array.violation);
        }
        for (int i = 0; i < foulVioList.length; i++) {
            RadioButton rb = new RadioButton(EvaluatorDetailsEdit.this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(foulVioList[i]);
            rg.addView(rb);
        }




        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (foulVio) {
                    callToIssue.setCallType("Foul");
                } else {
                    callToIssue.setCallType("Violation");
                }
                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        call[0] = btn.getText().toString();
                        break;
                    }
                }
                if (call[0]!=null) {
                    txtFoul.setText(call[0]);
                    callToIssue.setCall(call[0]);

                    if (foulVio) {
                        btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                        btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                        btnFoul.setTextColor(Color.parseColor("#FFFFF"));
                    } else {
                        btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                        btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                        btnViolation.setTextColor(Color.parseColor("#FFFFF"));

                    }
                }

                dialog.dismiss();


            }
        });

        dialog.show();
    }

    private void setInfo(String string, int pos, String designation, Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == pos) {
                buttons[i].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                buttons[i].setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                buttons[i].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                buttons[i].setTextColor(Color.parseColor("#E9841A"));
            }
        }
        if (designation.equals("referee")) {
            callToIssue.setRefereeId(currentGame.referee.get(pos).id);
        } else if (designation.equals("area")) {
            callToIssue.setArea(string);
        } else if (designation.equals("aop")) {
            callToIssue.setAreaOfPlay(string);
        } else if (designation.equals("review")) {
            callToIssue.setReviewDecision(string);
        } else if (designation.equals("period")) {
            callToIssue.setPeriod(pos);
        }
    }

    private void submitEval() {
        if (isEmpty(callToIssue.getCommitting())) {
            Toast.makeText(getApplicationContext(), "Please select a committing player/staff", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(callToIssue.getCallType()) || isEmpty(callToIssue.getCall())) {
            Toast.makeText(getApplicationContext(), "Please select a call", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(callToIssue.getRefereeId())) {
            Toast.makeText(getApplicationContext(), "Please select a referee", Toast.LENGTH_SHORT).show();
        }
        if (!isEmpty(callToIssue.getCommitting()) &&
                !isEmpty(callToIssue.getCallType()) &&
                !isEmpty(callToIssue.getCall()) &&
                !isEmpty(callToIssue.getRefereeId())) {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest putRequest = new StringRequest(Request.Method.POST, SubmitEvalURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            currentGame.setTimeInMillis(time);
                            Gson gson = new Gson();
                            String json = gson.toJson(currentGame);
                            Intent intent = new Intent(getApplicationContext(), Evaluation.class);
                            intent.putExtra("playing", json);
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.e("Error.Response", String.valueOf(error));
                        }
                    }
            ) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", id);

                    params.put("gameId", currentGame.getGameId());
                    params.put("period", String.valueOf(callToIssue.getPeriod()));
                    String timeText = txtMinute1.getText().toString() + txtMinute2.getText().toString() + ":" +
                            txtSecond1.getText().toString() + txtSecond2.getText().toString() + ":" +
                            txtMillis1.getText().toString() + txtMillis2.getText().toString();
                    params.put("time", timeText);
                    params.put("timeInMillis", String.valueOf(time));
                    params.put("callType", callToIssue.getCallType());
                    params.put("call", callToIssue.getCall());
                    params.put("committingType", callToIssue.getCommittingType());
                    params.put("committingTeam", callToIssue.getCommittingTeam());
                    params.put("committing", callToIssue.getCommitting());
                    if (!isEmpty(callToIssue.getDis())) {
                        params.put("disType", callToIssue.getDisType());
                        params.put("disTeam", callToIssue.getDisTeam());
                        params.put("dis", callToIssue.getDis());
                    }
                    currentGame.setTime(timeText);
                    params.put("refereeId", callToIssue.getRefereeId());
                    if(!isEmpty(callToIssue.getArea())){
                        params.put("area", callToIssue.getArea());
                    }
                    if(!isEmpty(callToIssue.getAreaOfPlay())){
                        params.put("areaOfPlay", callToIssue.getAreaOfPlay());
                    }
                    params.put("reviewDecision", callToIssue.getReviewDecision());
                    if (!isEmpty(txtComment.getText().toString())) {
                        params.put("comment", txtComment.getText().toString());
                    }
                    params.put("scoreA", callToIssue.getScoreA());
                    params.put("scoreB", callToIssue.getScoreB());
                    return params;
                }

            };

            queue.add(putRequest);
        } else {
            return;
        }

    }

    private void initTime() {
        long minutes = (time / 1000) / 60;
        long seconds = (time / 1000) % 60;
        long millis = time - (minutes * 60000) - (seconds * 1000);
        updateText(txtMinute1, txtMinute2, minutes);
        updateText(txtSecond1, txtSecond2, seconds);
        updateText(txtMillis1, txtMillis2, millis);
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
        Log.e("current time", String.valueOf(time));
        currentGame.setTimeInMillis(time);
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
        Intent intent = new Intent(getApplicationContext(), Evaluation.class);
        Gson gson = new Gson();
        String json = gson.toJson(currentGame);
        intent.putExtra("playing", json);
        startActivity(intent);
    }
}
