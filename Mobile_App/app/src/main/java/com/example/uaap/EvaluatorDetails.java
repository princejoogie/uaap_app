package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.uaap.Adapter.FoulListAdapter;
import com.example.uaap.Model.CallToIssue;
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.Game;
import com.example.uaap.Model.GameId;
import com.example.uaap.Model.PlayersDetails;
import com.google.gson.Gson;

import org.apache.commons.text.WordUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.text.TextUtils.concat;
import static android.text.TextUtils.isEmpty;

public class EvaluatorDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private String playing;

    private String[] playingA;
    private String[] playingB;

    private Button btnRefA;
    private Button btnRefB;
    private Button btnRefC;
    private Button[] btnCommA;
    private Button[] btnCommB;
    private Button[] btnDisA;
    private Button[] btnDisB;
    private Button btnStaffA;
    private Button btnStaffB;
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
    private int finalMinute, finalSecond, finalMilli;
    private ListView foulListView;
    private FoulListAdapter listAdapter;

    private CurrentGame currentGame;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            playing = extras.getString("playing");

        }
        Log.e("playing", playing);
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);
        time = currentGame.getTimeInMillis();
        playingA = new String[5];
        playingB = new String[5];
        btnCommA = new Button[5];
        btnCommB = new Button[5];
        btnDisA = new Button[5];
        btnDisB = new Button[5];

        btnRefA = findViewById(R.id.btnRefA);
        btnRefB = findViewById(R.id.btnRefB);
        btnRefC = findViewById(R.id.btnRefC);

        btnCommA[0] = findViewById(R.id.btnCommA1);
        btnCommA[1] = findViewById(R.id.btnCommA2);
        btnCommA[2] = findViewById(R.id.btnCommA3);
        btnCommA[3] = findViewById(R.id.btnCommA4);
        btnCommA[4] = findViewById(R.id.btnCommA5);

        btnCommB[0] = findViewById(R.id.btnCommB1);
        btnCommB[1] = findViewById(R.id.btnCommB2);
        btnCommB[2] = findViewById(R.id.btnCommB3);
        btnCommB[3] = findViewById(R.id.btnCommB4);
        btnCommB[4] = findViewById(R.id.btnCommB5);

        btnDisA[0] = findViewById(R.id.btnDisA1);
        btnDisA[1] = findViewById(R.id.btnDisA2);
        btnDisA[2] = findViewById(R.id.btnDisA3);
        btnDisA[3] = findViewById(R.id.btnDisA4);
        btnDisA[4] = findViewById(R.id.btnDisA5);

        btnDisB[0] = findViewById(R.id.btnDisB1);
        btnDisB[1] = findViewById(R.id.btnDisB2);
        btnDisB[2] = findViewById(R.id.btnDisB3);
        btnDisB[3] = findViewById(R.id.btnDisB4);
        btnDisB[4] = findViewById(R.id.btnDisB5);

        btnStaffA = findViewById(R.id.btnStaffA);
        btnStaffB = findViewById(R.id.btnStaffB);
        btnSubmitEval = findViewById(R.id.btnSubmitEval);
        btnFoul = findViewById(R.id.btnFoul);
        btnViolation = findViewById(R.id.btnViolation);
        btnSubA = findViewById(R.id.btnSubA);
        btnSubB = findViewById(R.id.btnSubB);
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
        foulListView = findViewById(R.id.foulVioList);


        foulListView.setOnItemClickListener(this);
        callToIssue = new CallToIssue();
        setInfo(currentGame.getPeriodName(), currentGame.getPeriod(), "period", periodButtons);
        getThisGame();
        callToIssue.setCallType("Foul");
        callToIssue.setPeriod(currentGame.getPeriod());
        callToIssue.setPeriodName(currentGame.getPeriodName());
        genFoul(getResources().getStringArray(R.array.foul));

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            btnCommA[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnCommA, finalI, true);  //true if team A
                    clearCommStaff();
                    clearButtons(btnCommB, false);
                    callToIssue.setCommittingTeam(currentGame.getTeamAId());
                    callToIssue.setCommittingType("player");
                    callToIssue.setCommitting(btnCommA[finalI].getText().toString());
                }
            });
            btnCommA[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnCommA, true);
                    callToIssue.setCommitting(null);
                    return true;
                }
            });
            btnCommB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnCommB, finalI, false);  //true if team A
                    clearCommStaff();
                    clearButtons(btnCommA, true);
                    callToIssue.setCommittingTeam(currentGame.getTeamBId());
                    callToIssue.setCommittingType("player");
                    callToIssue.setCommitting(btnCommB[finalI].getText().toString());
                }
            });
            btnCommB[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnCommB, false);
                    callToIssue.setCommitting(null);
                    return true;
                }
            });
            btnDisA[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnDisA, finalI, true);
                    clearButtons(btnDisB, false);
                    callToIssue.setDisTeam(currentGame.getTeamAId());
                    callToIssue.setDisType("player");
                    callToIssue.setDis(btnDisA[finalI].getText().toString());
                    Toast.makeText(getApplicationContext(), btnDisA[finalI].getText(), Toast.LENGTH_LONG).show();

                }
            });
            btnDisA[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnDisA, true);
                    callToIssue.setDis(null);
                    return true;
                }
            });
            btnDisB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnDisB, finalI, false);
                    clearButtons(btnDisA, true);
                    callToIssue.setDisTeam(currentGame.getTeamBId());
                    callToIssue.setDisType("player");
                    callToIssue.setDis(btnDisB[finalI].getText().toString());
                    Toast.makeText(getApplicationContext(), callToIssue.getDis(), Toast.LENGTH_SHORT).show();
                }
            });
            btnDisB[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnDisB, false);
                    callToIssue.setDis(null);
                    return true;
                }
            });
            btnViolation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    callToIssue.setCallType("Violation");
                    genFoul(getResources().getStringArray(R.array.violation));

                }
            });
            btnFoul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFoul.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
                    btnViolation.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
                    callToIssue.setCallType("Foul");
                    genFoul(getResources().getStringArray(R.array.foul));
                }
            });
        }
        btnStaffA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialog(true);
            }
        });
        btnStaffB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialog(false);
            }
        });
        btnStaffA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnStaffA.setBackgroundColor(Color.parseColor("#038500"));
                btnStaffA.setTextColor(Color.parseColor("#FFFFFF"));
                callToIssue.setCommitting(null);
                callToIssue.setCommittingType(null);
                return true;
            }
        });
        btnStaffB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnStaffB.setBackgroundColor(Color.parseColor("#820000"));
                btnStaffB.setTextColor(Color.parseColor("#FFFFFF"));
                callToIssue.setCommitting(null);
                callToIssue.setCommittingType(null);
                return true;
            }
        });
        btnSubmitEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEval();
            }
        });
        btnSubA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialogSub(true);
            }
        });
        btnSubB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRadioButtonDialogSub(false);
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


    }

    private void buttonSelected(Button[] buttons, int index, boolean team) {
        //reset all buttons
        for (int i = 0; i < 5; i++) {
            if (team) {
                buttons[i].setBackgroundColor(Color.parseColor("#038500"));
            } else {
                buttons[i].setBackgroundColor(Color.parseColor("#820000"));
            }
            buttons[i].setTextColor(Color.parseColor("#FFFFFF"));
        }
        //trigger button
        buttons[index].setBackgroundColor(Color.parseColor("#FFFFFF"));
        if (team) {
            buttons[index].setTextColor(Color.parseColor("#038500"));
        } else {
            buttons[index].setTextColor(Color.parseColor("#820000"));
        }
    }

    private void clearButtons(Button[] buttons, boolean team) {
        for (int i = 0; i < 5; i++) {
            if (team) {
                buttons[i].setBackgroundColor(Color.parseColor("#038500"));
            } else {
                buttons[i].setBackgroundColor(Color.parseColor("#820000"));
            }
            buttons[i].setTextColor(Color.parseColor("#FFFFFF"));
        }

    }

    private void clearCommStaff() {
        btnStaffB.setBackgroundColor(Color.parseColor("#820000"));
        btnStaffB.setTextColor(Color.parseColor("#FFFFFF"));
        btnStaffA.setBackgroundColor(Color.parseColor("#038500"));
        btnStaffA.setTextColor(Color.parseColor("#FFFFFF"));
    }

    private void getThisGame() {
        btnRefA.setText(currentGame.referee.get(0).name);
        btnRefB.setText(currentGame.referee.get(1).name);
        btnRefC.setText(currentGame.referee.get(2).name);
        setPlayers();
    }

    private void setPlayers() {
        playingA = currentGame.getPlayingA();
        playingB = currentGame.getPlayingB();
        for (int i = 0; i < 5; i++) {
            btnCommA[i].setText(playingA[i]);
            btnDisA[i].setText(playingA[i]);

            btnCommB[i].setText(playingB[i]);
            btnDisB[i].setText(playingB[i]);

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
                if (team) {
                    clearButtons(btnCommA, true);
                    clearButtons(btnCommB, false);
                    clearCommStaff();
                    btnStaffA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    btnStaffA.setTextColor(Color.parseColor("#038500"));
                    callToIssue.setCommittingTeam(currentGame.getTeamBId());

                } else {
                    clearButtons(btnCommA, true);
                    clearButtons(btnCommB, false);
                    clearCommStaff();
                    btnStaffB.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    btnStaffB.setTextColor(Color.parseColor("#820000"));

                    callToIssue.setCommittingTeam(currentGame.getTeamAId());

                }
                dialog.dismiss();
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

    private void genFoul(String[] list) {

        List<String> fouls = Arrays.asList(list);
        listAdapter = new FoulListAdapter(getApplicationContext(), fouls);
        foulListView.setAdapter(listAdapter);


    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        TextView textView = (TextView) v.findViewById(R.id.txtFoul);
        callToIssue.setCall(WordUtils.capitalizeFully(textView.getText().toString()));
    }

    private void showRadioButtonDialogSub(final boolean team) {
        Gson gson = new Gson();

        AlertDialog.Builder builder = new AlertDialog.Builder(EvaluatorDetails.this);
        ArrayList<PlayersDetails> playingList = currentGame.getPlayerA();
        if (team) {
            playingList = currentGame.getPlayerA();
        } else {
            playingList = currentGame.getPlayerB();
        }
        final String[] allPlayers = new String[playingList.size()];
        for (int i = 0; i < playingList.size(); i++) {
            allPlayers[i] = playingList.get(i).jerseyNumber + " " + playingList.get(i).name;
        }
        final boolean[] checkedPlayers = new boolean[playingList.size()];
        for (int i = 0; i < playingList.size(); i++) {
            boolean found = false;
            for (int x = 0; x < 5; x++) {
                if (team) {
                    if (currentGame.playingA[x].equals(playingList.get(i).jerseyNumber)) {
                        found = true;
                    }
                } else {
                    if (currentGame.playingB[x].equals(playingList.get(i).jerseyNumber)) {
                        found = true;
                    }
                }

            }
            if (found) {
                checkedPlayers[i] = true;
            } else {
                checkedPlayers[i] = false;
            }
        }


        builder.setMultiChoiceItems(allPlayers, checkedPlayers, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedPlayers[which] = isChecked;
            }
        });

        builder.setCancelable(true);
        builder.setTitle("Select Players");
        final ArrayList<PlayersDetails> finalPlayingList = playingList;
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int count = 0;
                for (int i = 0; i < checkedPlayers.length; i++) {
                    if (checkedPlayers[i]) {
                        count++;
                    }
                }
                if (count != 5) {
                    Toast.makeText(getApplicationContext(), "You must select 5 players", Toast.LENGTH_SHORT).show();
                } else {
                    int index = 0;
                    for (int i = 0; i < checkedPlayers.length; i++) {
                        if (checkedPlayers[i]) {
                            if (team) {
                                playingA[index] = finalPlayingList.get(i).jerseyNumber;
                            } else {
                                playingB[index] = finalPlayingList.get(i).jerseyNumber;
                            }
                            index++;
                        }
                    }
                    dialog.dismiss();
                    currentGame.setPlayingA(playingA);
                    currentGame.setPlayingB(playingB);
                    Gson cur = new Gson();
                    playing = cur.toJson(currentGame);
                    clearButtons(btnCommA, true);
                    clearButtons(btnCommB, false);
                    clearButtons(btnDisA, true);
                    clearButtons(btnDisB, false);
                    callToIssue.setCommitting(null);
                    callToIssue.setDis(null);
                    setPlayers();

                }
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
                    String time = txtMinute1.getText().toString()+txtMinute2.getText().toString()+":"+
                            txtSecond1.getText().toString()+txtSecond2.getText().toString()+":"+
                            txtMillis1.getText().toString()+txtMillis2.getText().toString();
                    params.put("time", time);
                    params.put("callType", callToIssue.getCallType());
                    params.put("call", callToIssue.getCall());
                    params.put("committingType", callToIssue.getCommittingType());
                    params.put("committingTeam", callToIssue.getCommittingTeam());
                    params.put("committing", callToIssue.getCommitting());
                    if(!isEmpty(callToIssue.getDis())){
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
