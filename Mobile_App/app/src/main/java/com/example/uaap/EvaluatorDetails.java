package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.Game;
import com.example.uaap.Model.GameId;
import com.example.uaap.Model.PlayersDetails;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class EvaluatorDetails extends AppCompatActivity {

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
    private String GetGameDetailsURL = "http://68.183.49.18/uaap/public/getGameDetails";

    private boolean committingTeam;
    private String committingType;
    private String committing;

    private boolean disTeam;
    private String disType;
    private String dis;

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
        getThisGame();

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            btnCommA[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnCommA, finalI, true);  //true if team A
                    clearCommStaff();
                    clearButtons(btnCommB, false);
                    committingTeam = true;
                    committingType = "player";
                    committing = btnCommA[finalI].getText().toString();
                }
            });
            btnCommA[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnCommA, true);
                    committing = null;
                    return true;
                }
            });
            btnCommB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnCommB, finalI, false);  //true if team A
                    clearCommStaff();
                    clearButtons(btnCommA, true);
                    committingTeam = false;
                    committingType = "player";
                    committing = btnCommB[finalI].getText().toString();
                }
            });
            btnCommB[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnCommB, false);
                    committing = null;
                    return true;
                }
            });
            btnDisA[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnDisA, finalI, true);
                    clearButtons(btnDisB, false);
                    disTeam = true;
                    disType = "player";
                    dis = btnDisA[finalI].getText().toString();
                }
            });
            btnDisA[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnDisA, true);
                    dis = null;
                    return true;
                }
            });
            btnDisB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSelected(btnDisB, finalI, false);
                    clearButtons(btnDisA, true);
                    disTeam = false;
                    disType = "player";
                    dis = btnDisB[finalI].getText().toString();
                }
            });
            btnDisB[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clearButtons(btnDisB, false);
                    dis = null;
                    return true;
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
                committing = null;
                committingType = null;
                return true;
            }
        });
        btnStaffB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnStaffB.setBackgroundColor(Color.parseColor("#820000"));
                btnStaffB.setTextColor(Color.parseColor("#FFFFFF"));
                committing = null;
                committingType = null;
                return true;
            }
        });
        btnSubmitEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("committing", committing);
                Log.e("comittingTeam", String.valueOf(committingTeam));
                Log.e("committingType", committingType);
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
    private void clearCommStaff(){
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

        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroup_staff);
        Button btnSubmitStaff = (Button) dialog.findViewById(R.id.btnSubmitStaff);
        if (team){
            for (int i = 0; i < game.staffA.size(); i++) {
                RadioButton rb = new RadioButton(EvaluatorDetails.this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(game.staffA.get(i).name+" ("+teamA+")");
                rb.setId(Integer.parseInt(game.staffA.get(i).id));
                rg.addView(rb);
            }
        }
        else{
            for (int i = 0; i < game.staffB.size(); i++) {
                RadioButton rb = new RadioButton(EvaluatorDetails.this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setText(game.staffB.get(i).name+" ("+teamB+")");
                rb.setId(Integer.parseInt(game.staffB.get(i).id));
                rg.addView(rb);
            }
        }



        btnSubmitStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(team){
                    clearButtons(btnCommA, true);
                    clearButtons(btnCommB, false);
                    clearCommStaff();
                    btnStaffA.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    btnStaffA.setTextColor(Color.parseColor("#038500"));
                    committingTeam = true;


                }
                else{
                    clearButtons(btnCommA, true);
                    clearButtons(btnCommB, false);
                    clearCommStaff();
                    btnStaffB.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    btnStaffB.setTextColor(Color.parseColor("#820000"));

                    committingTeam=false;

                }
                dialog.dismiss();
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                committingType = "staff";
                committing = String.valueOf(checkedId);
//                rgb.clearCheck();
            }
        });

        dialog.show();

    }
}
