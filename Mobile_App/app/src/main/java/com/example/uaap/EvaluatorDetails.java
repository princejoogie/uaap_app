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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluatorDetails extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String gameId;
    private String gameCode;
    private String playing;
    private String teamA;
    private String teamB;
    private Game game;

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
    private String GetGameDetailsURL = "http://68.183.49.18/uaap/public/getGameDetails";

    private Button[] refButtons;
    private Button[] areaButtons;
    private Button[] aopButtons;
    private Button[] reviewButtons;
    private CallToIssue callToIssue;

    private ListView foulListView;
    private FoulListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameId = extras.getString("gameId");
            gameCode = extras.getString("gameCode");
            playing = extras.getString("playing");
            teamA = extras.getString("teamA");
            teamB = extras.getString("teamB");
        }
        Toast.makeText(getApplicationContext(), playing, Toast.LENGTH_SHORT).show();
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

        final Button[] refButtons = {btnRefA, btnRefB, btnRefC};
        final Button[] areaButtons = {btnAreaLead, btnAreaCenter, btnAreaTrail};
        final Button[] aopButtons = {btnAoPLead, btnAoPCenter, btnAoPTrail};
        final Button[] reviewButtons = {btnReviewCC, btnReviewINC, btnReviewCFR, btnReviewNCFR};

        foulListView = findViewById(R.id.foulVioList);
        foulListView.setOnItemClickListener(this);
        callToIssue = new CallToIssue();
        getThisGame();

        genFoul(getResources().getStringArray(R.array.foul));

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            btnCommA[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnCommA, finalI, true);  //true if team A
                    clearCommStaff();
                    clearButtons(btnCommB, false);
                    callToIssue.setCommittingTeam(true);
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
                    callToIssue.setCommittingTeam(false);
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
                    callToIssue.setDisTeam(true);
                    callToIssue.setDisType("player");
                    callToIssue.setDis(btnDisA[finalI].getText().toString());

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
                    callToIssue.setDisTeam(false);
                    callToIssue.setDisType("player");
                    callToIssue.setDis(btnDisB[finalI].getText().toString());
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
                setInfo(game.referee.get(0).name, 0, "referee", refButtons);
            }
        });
        btnRefB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo(game.referee.get(1).name, 1, "referee", refButtons);
            }
        });
        btnRefC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo(game.referee.get(2).name, 2, "referee", refButtons);
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
                setInfo("NCFR",3,"review", reviewButtons);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest putRequest = new StringRequest(Request.Method.POST, GetGameDetailsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        game = gson.fromJson(response, Game.class);

                        btnRefA.setText(game.referee.get(0).name);
                        btnRefB.setText(game.referee.get(1).name);
                        btnRefC.setText(game.referee.get(2).name);
                        setPlayers();
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

    private void setPlayers() {

        Gson gson = new Gson();
        CurrentGame currentGame = gson.fromJson(playing, CurrentGame.class);
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
            for (int i = 0; i < game.staffA.size(); i++) {
                RadioButton rb = new RadioButton(EvaluatorDetails.this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(game.staffA.get(i).name + " (" + teamA + ")");
                rb.setId(Integer.parseInt(game.staffA.get(i).id));
                rg.addView(rb);
            }
        } else {
            for (int i = 0; i < game.staffB.size(); i++) {
                RadioButton rb = new RadioButton(EvaluatorDetails.this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(game.staffB.get(i).name + " (" + teamB + ")");
                rb.setId(Integer.parseInt(game.staffB.get(i).id));
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
                    callToIssue.setCommittingTeam(false);

                } else {
                    clearButtons(btnCommA, true);
                    clearButtons(btnCommB, false);
                    clearCommStaff();
                    btnStaffB.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    btnStaffB.setTextColor(Color.parseColor("#820000"));

                    callToIssue.setCommittingTeam(true);

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
        final CurrentGame currentGame = gson.fromJson(playing, CurrentGame.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(EvaluatorDetails.this);
        ArrayList<PlayersDetails> playingList = game.getPlayerA();
        if (team) {
            playingList = game.getPlayerA();
        } else {
            playingList = game.getPlayerB();
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
                    String json = cur.toJson(currentGame);
                    Intent intent = new Intent(getApplicationContext(), EvaluatorDetails.class);
                    intent.putExtra("gameId", gameId);
                    intent.putExtra("gameCode", gameCode);
                    intent.putExtra("playing", json);
                    intent.putExtra("teamA", teamA);
                    intent.putExtra("teamB", teamB);
                    Toast.makeText(getApplicationContext(), "Players substitution", Toast.LENGTH_SHORT).show();
                    Log.e("New playing", json);
                    startActivity(intent);
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

    private void setInfo(String id, int pos, String designation, Button[] buttons){
        for(int i=0;i<buttons.length;i++){
            if(i==pos){
                buttons[i].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_eval_selected));
            }
            else{
                buttons[i].setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_eval));
            }
        }
        if(designation.equals("referee")){
            callToIssue.setRefereeId(id);
        }
        else if(designation.equals("area")){
            callToIssue.setArea(id);
        }
        else if(designation.equals("aop")){
            callToIssue.setAreaOfPlay(id);
        }
        else if(designation.equals("review")){
            callToIssue.setReviewDecision(id);
        }
    }


}
