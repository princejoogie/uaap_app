package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
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
import com.example.uaap.Adapter.FoulListAdapter;
import com.example.uaap.Model.CallToIssue;
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.PlayersDetails;
import com.google.gson.Gson;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

public class EvaluatorDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private String playing;

    private String[] playingA;
    private String[] playingB;

    private Button btnRefA;
    private Button btnRefB;
    private Button btnRefC;
    private Button[] btnComm;
    private Button[] btnDis;
    private Button btnStaffComm;
    private Button btnStaffDis;
    private Button btnChange;
    private Button btnSubmitEval;
    private Button btnFoul;
    private Button btnViolation;
    private Button btnSubA;
    private Button btnSubB;
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
    private String SubmitEvalURL = "http://68.183.49.18/uaap/public/createEvaluation";

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

        }
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);
        Log.e("thisplaying", playing);
        Log.e("player", currentGame.playerA.get(0).jerseyNumber);


        time = currentGame.getTimeInMillis();
        btnComm = new Button[20];
        btnDis = new Button[20];

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
        initTime();
        final Button[] refButtons = {btnRefA, btnRefB, btnRefC};
        final Button[] areaButtons = {btnAreaLead, btnAreaCenter, btnAreaTrail};
        final Button[] aopButtons = {btnAoPLead, btnAoPCenter, btnAoPTrail};
        final Button[] reviewButtons = {btnReviewCC, btnReviewINC, btnReviewCFR, btnReviewNCFR};
        final Button[] periodButtons = {btnQ1, btnQ2, btnQ3, btnQ4, btnOT};

        callToIssue = new CallToIssue();
        setInfo(currentGame.getPeriodName(), currentGame.getPeriod(), "period", periodButtons);
        playingA = new String[currentGame.playerA.size()];
        playingB = new String[currentGame.playerB.size()];

        for(int i = 0; i < currentGame.playerA.size();i++ ){
            playingA[i] =  currentGame.playerA.get(i).jerseyNumber;
        }
        for(int i = 0; i < currentGame.playerB.size();i++ ){
            playingB[i] = currentGame.playerB.get(i).jerseyNumber;
        }

        getThisGame();
        callToIssue.setCallType("Foul");
        callToIssue.setPeriod(currentGame.getPeriod());
        callToIssue.setPeriodName(currentGame.getPeriodName());

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            btnComm[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    buttonSelected(btnComm, finalI, true);  //true if team A
//                    clearCommStaff();
//                    clearButtons(btnCommB, false);
//                    callToIssue.setCommittingTeam(currentGame.getTeamAId());
//                    callToIssue.setCommittingType("player");
//                    callToIssue.setCommitting(btnCommA[finalI].getText().toString());
                }
            });
            btnComm[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    clearButtons(btnCommA, true);
//                    callToIssue.setCommitting(null);
                    return true;
                }
            });

            btnViolation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    callToIssue.setCallType("Violation");

                }
            });
            btnFoul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    callToIssue.setCallType("Foul");
//                    genFoul(getResources().getStringArray(R.array.foul));
                }
            });
        }
        btnStaffComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialog(true);
            }
        });
        btnStaffDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialog(false);
            }
        });
//        btnStaffA.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                btnStaffA.setBackgroundColor(Color.parseColor("#038500"));
//                btnStaffA.setTextColor(Color.parseColor("#FFFFFF"));
//                callToIssue.setCommitting(null);
//                callToIssue.setCommittingType(null);
//                return true;
//            }
//        });
//        btnStaffB.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                btnStaffB.setBackgroundColor(Color.parseColor("#820000"));
//                btnStaffB.setTextColor(Color.parseColor("#FFFFFF"));
//                callToIssue.setCommitting(null);
//                callToIssue.setCommittingType(null);
//                return true;
//            }
//        });
        btnSubmitEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEval();
            }
        });

        btnRefA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo(currentGame.referee.get(0).name, 0, "referee", refButtons);
            }
        });
        btnRefB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo(currentGame.referee.get(1).name, 1, "referee", refButtons);
            }
        });
        btnRefC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo(currentGame.referee.get(2).name, 2, "referee", refButtons);
            }
        });
        btnAreaLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Lead", 0, "area", areaButtons);
            }
        });
        btnAreaCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Center", 1, "area", areaButtons);
            }
        });
        btnAreaTrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Trail", 2, "area", areaButtons);
            }
        });
        btnAoPLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Lead", 0, "aop", aopButtons);
            }
        });
        btnAoPCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Center", 1, "aop", aopButtons);

            }
        });
        btnAoPTrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Trail", 2, "aop", aopButtons);
            }
        });
        btnReviewCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("CC", 0, "review", reviewButtons);
            }
        });
        btnReviewINC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("INC", 1, "review", reviewButtons);
            }
        });
        btnReviewCFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("CFR", 2, "review", reviewButtons);
            }
        });
        btnReviewNCFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("NCFR", 3, "review", reviewButtons);
            }
        });
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Q1", 0, "period", periodButtons);

            }
        });

        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Q2", 1, "period", periodButtons);
            }
        });
        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Q3", 2, "period", periodButtons);
            }
        });
        btnQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("Q4", 3, "period", periodButtons);
            }
        });
        btnOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo("OT", 4, "period", periodButtons);
            }
        });
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
                changed = !changed;
                setPlayers(changed);
            }
        });


    }

    private void getThisGame() {
        btnRefA.setText(currentGame.referee.get(0).name);
        btnRefB.setText(currentGame.referee.get(1).name);
        btnRefC.setText(currentGame.referee.get(2).name);
        changed = false;
        setPlayers(changed);
    }

    private void setPlayers(boolean changed) {

        for (int i = 0; i < 20; i++) {
            btnComm[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnComm[i].setText("");
            btnDis[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnDis[i].setText("");

        }
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

    private void showRadioButtonDialog(final boolean team) {
        // custom dialog
        final Dialog dialog = new Dialog(EvaluatorDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_staff);

        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        Button btnSubmitStaff = (Button) dialog.findViewById(R.id.btnSubmitStaff);
        if (team) {
            for (int i = 0; i < currentGame.staffA.size(); i++) {
                RadioButton rb = new RadioButton(EvaluatorDetails.this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(currentGame.staffA.get(i).name + " (" + currentGame.getTeamA() + ")");
                rb.setId(Integer.parseInt(currentGame.staffA.get(i).id));
                rg.addView(rb);
            }
        } else {
            for (int i = 0; i < currentGame.staffB.size(); i++) {
                RadioButton rb = new RadioButton(EvaluatorDetails.this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(currentGame.staffB.get(i).name + " (" + currentGame.getTeamB() + ")");
                rb.setId(Integer.parseInt(currentGame.staffB.get(i).id));
                rg.addView(rb);
            }
        }


        btnSubmitStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (changed) {
//                    clearButtons(btnCommA, true);
//                    clearButtons(btnCommB, false);
//                    clearCommStaff();
//                    btnStaffA.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    btnStaffA.setTextColor(Color.parseColor("#038500"));
//                    callToIssue.setCommittingTeam(currentGame.getTeamBId());
//
//                } else {
//                    clearButtons(btnCommA, true);
//                    clearButtons(btnCommB, false);
//                    clearCommStaff();
//                    btnStaffB.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    btnStaffB.setTextColor(Color.parseColor("#820000"));
//
//                    callToIssue.setCommittingTeam(currentGame.getTeamAId());
//
//                }
//                dialog.dismiss();
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                callToIssue.setCommittingType("staff");
                callToIssue.setCommitting(String.valueOf(checkedId));
            }
        });

        dialog.show();

    }



    public void onItemClick(AdapterView parent, View v, int position, long id) {
        TextView textView = (TextView) v.findViewById(R.id.txtFoul);
        callToIssue.setCall(WordUtils.capitalizeFully(textView.getText().toString()));
    }



    private void setInfo(String string, int pos, String designation, Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == pos) {
                buttons[i].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
            } else {
                buttons[i].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
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
            callToIssue.setPeriodName(string);
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
        if (isEmpty(callToIssue.getArea())) {
            Toast.makeText(getApplicationContext(), "Please select an area", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(callToIssue.getAreaOfPlay())) {
            Toast.makeText(getApplicationContext(), "Please select an area of play", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(callToIssue.getReviewDecision())) {
            Toast.makeText(getApplicationContext(), "Please select a review decision", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(callToIssue.getPeriodName())) {
            Toast.makeText(getApplicationContext(), "Please select a period", Toast.LENGTH_SHORT).show();
        }
        if (!isEmpty(callToIssue.getCommitting()) &&
                !isEmpty(callToIssue.getCallType()) &&
                !isEmpty(callToIssue.getCall()) &&
                !isEmpty(callToIssue.getRefereeId()) &&
                !isEmpty(callToIssue.getArea()) &&
                !isEmpty(callToIssue.getAreaOfPlay()) &&
                !isEmpty(callToIssue.getReviewDecision())) {

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
                    Gson gson = new Gson();

                    params.put("gameId", currentGame.getGameId());
                    params.put("period", String.valueOf(callToIssue.getPeriod()));
                    params.put("periodName", callToIssue.getPeriodName());
                    Log.e("period", String.valueOf(callToIssue.getPeriod()));
                    Log.e("periodName", callToIssue.getPeriodName());
                    String time = txtMinute1.getText().toString() + txtMinute2.getText().toString() + ":" +
                            txtSecond1.getText().toString() + txtSecond2.getText().toString() + ":" +
                            txtMillis1.getText().toString() + txtMillis2.getText().toString();
                    params.put("time", time);
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
                    currentGame.setTime(time);
                    params.put("refereeId", callToIssue.getRefereeId());
                    params.put("area", callToIssue.getArea());
                    params.put("areaOfPlay", callToIssue.getAreaOfPlay());
                    params.put("reviewDecision", callToIssue.getReviewDecision());
                    if (!isEmpty(txtComment.getText().toString())) {
                        params.put("comment", txtComment.getText().toString());
                    }
                    String json = gson.toJson(currentGame);
                    params.put("currentGame", json);
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
