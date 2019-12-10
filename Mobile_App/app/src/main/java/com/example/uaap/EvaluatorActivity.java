package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.uaap.Model.League;
import com.example.uaap.Model.LeagueDetails;
import com.example.uaap.Model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import petrov.kristiyan.colorpicker.ColorPicker;

public class EvaluatorActivity extends AppCompatActivity {
    private Spinner spinLeague;
    private Spinner spinnerTeamA;
    private Spinner spinnerTeamB;
    private Spinner spinnerRefereeA;
    private Spinner spinnerRefereeB;
    private Spinner spinnerRefereeC;
    private CardView btnStartGame;
    private EditText edtGameCode;

    private String LeagueURL = "http://68.183.49.18/uaap/public/getLeague";
    private String TeamURL = "http://68.183.49.18/uaap/public/getTeam";
    private String RefereeURL = "http://68.183.49.18/uaap/public/getReferee";
    private String CreateGameURL = "http://68.183.49.18/uaap/public/createGame";
    private String CheckGameCodeURL = "http://68.183.49.18/uaap/public/checkGameCode";
    private String GetGameDetailsURL = "http://68.183.49.18/uaap/public/getGameDetails";

    private Button btnColorTeamA;
    private Button btnColorTeamB;
    private League details;
    private CurrentGame currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator);

        spinLeague = findViewById(R.id.spinLeague);
        spinnerTeamA = findViewById(R.id.spinnerTeamA);
        spinnerTeamB = findViewById(R.id.spinnerTeamB);
        spinnerRefereeA = findViewById(R.id.spinnerRefereeA);
        spinnerRefereeB = findViewById(R.id.spinnerRefereeB);
        spinnerRefereeC = findViewById(R.id.spinnerRefereeC);
        btnStartGame = findViewById(R.id.btnStartGame);
        edtGameCode = findViewById(R.id.edtGameCode);
        btnColorTeamA = findViewById(R.id.btnColorTeamA);
        btnColorTeamB = findViewById(R.id.btnColorTeamB);
        btnColorTeamA.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnColorTeamB.setBackgroundColor(Color.parseColor("#FFFFFF"));

        currentGame = new CurrentGame();

        get(RefereeURL, spinnerRefereeA);
        get(RefereeURL, spinnerRefereeB);
        get(RefereeURL, spinnerRefereeC);
        get(LeagueURL, spinLeague);
        // String teamA = spinnerTeamA.getSelectedItem().toString();
        //String teamB = spinnerTeamB.getSelectedItem().toString();


        spinLeague.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeagueDetails leagueDetails = (LeagueDetails) parent.getSelectedItem();
                getTeam(TeamURL, spinnerTeamA, leagueDetails.id);
                getTeam(TeamURL, spinnerTeamB, leagueDetails.id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ref1 = spinnerRefereeA.getSelectedItem().toString();
                    String ref2 = spinnerRefereeB.getSelectedItem().toString();
                    String ref3 = spinnerRefereeC.getSelectedItem().toString();
                    String schoolA = spinnerTeamA.getSelectedItem().toString();
                    String schoolB = spinnerTeamB.getSelectedItem().toString();
                    String Erro1 = "";
                    String Erro2 = "";
                    if (edtGameCode.getText().toString().isEmpty()) {
                        if (ref1.equals(null) || ref2.equals(null) || ref3.equals(null) || schoolA.equals(null) || schoolB.equals(null)) {
                            Toast.makeText(EvaluatorActivity.this,
                                    "Empty items are not allowed", Toast.LENGTH_LONG).show();
                        }
                        else if (spinnerTeamA.getSelectedItem().toString().equals(spinnerTeamB.getSelectedItem().toString())) {
                            Toast.makeText(getApplicationContext(), "Same teams are not allowed", Toast.LENGTH_SHORT).show();
                        }
                        else if (ref1.equals(ref2) || ref1.equals(ref3) || ref2.equals(ref3)) {
                            Toast.makeText(getApplicationContext(), "Same referees are not allowed", Toast.LENGTH_SHORT).show();
                        } else {
                            createGame();
                        }


                    } else {
                        checkGameCode(edtGameCode.getText().toString());////
                    }
                } catch (Exception e) {
                    Toast.makeText(EvaluatorActivity.this,
                            "Empty items are not allowed", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnColorTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTeamColor(btnColorTeamA);
            }
        });
        btnColorTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTeamColor(btnColorTeamB);
            }
        });
    }

    private void setTeamColor(final Button btnColorTeam) {
        final ColorPicker colorPicker = new ColorPicker(this);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#FFFFFF");
        colors.add("#000000");
        colors.add("#696969");
        colors.add("#DC143C");
        colors.add("#8B0000");
        colors.add("#FF1493");
        colors.add("#DB7093");
        colors.add("#FF6347");
        colors.add("#FFA500");
        colors.add("#BDB76B");
        colors.add("#FF00FF");
        colors.add("#9932CC");
        colors.add("#4B0082");
        colors.add("#483D8B");
        colors.add("#228B22");
        colors.add("#9ACD32");
        colors.add("#556B2F");
        colors.add("#20B2AA");
        colors.add("#008080");
        colors.add("#4682B4");
        colors.add("#0000CD");
        colors.add("#191970");
        colors.add("#8B4513");
        colorPicker.setColors(colors)
                .setColumns(5)
                .setRoundColorButton(false)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        btnColorTeam.setBackgroundColor(color);
                        Log.e("color", String.valueOf(color));

                    }

                    @Override
                    public void onCancel() {
                        btnColorTeam.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    }
                }).show();

    }

    private void getTeam(final String URL, final Spinner spinner, final String id) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        details = gson.fromJson(response, League.class);
                        ArrayAdapter<LeagueDetails> adapter = new ArrayAdapter<LeagueDetails>(getApplicationContext(), R.layout.spinner_item, details.result);
                        spinner.setAdapter(adapter);


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
                params.put("leagueId", id);
                return params;
            }

        };

        queue.add(putRequest);
    }

    private void get(String URL, final Spinner spinner) {
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest putRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Here", response);
                            Gson gson = new Gson();
                            details = gson.fromJson(response, League.class);
                            ArrayAdapter<LeagueDetails> adapter = new ArrayAdapter<LeagueDetails>(getApplicationContext(), R.layout.spinner_item, details.result);
                            spinner.setAdapter(adapter);

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

    private void createGame() {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, CreateGameURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        GameId gameId = gson.fromJson(response, GameId.class);
                        LeagueDetails teamA = (LeagueDetails) spinnerTeamA.getSelectedItem();
                        LeagueDetails teamB = (LeagueDetails) spinnerTeamB.getSelectedItem();
                        currentGame.setTeamA(teamA.name);
                        currentGame.setTeamB(teamB.name);
                        currentGame.setPeriod(0);
                        currentGame.setTimeInMillis(600000);
                        currentGame.setScoreB(0);
                        currentGame.setScoreA(0);
                        currentGame.setColorTeamA(((ColorDrawable) btnColorTeamA.getBackground()).getColor());
                        currentGame.setColorTeamB(((ColorDrawable) btnColorTeamB.getBackground()).getColor());
                        currentGame.setLeagueName(((LeagueDetails) spinLeague.getSelectedItem()).name);
                        prepareEval(gameId.gameId, gameId.gameCode);


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
                LeagueDetails leagueId = (LeagueDetails) spinLeague.getSelectedItem();
                LeagueDetails teamA = (LeagueDetails) spinnerTeamA.getSelectedItem();
                LeagueDetails teamB = (LeagueDetails) spinnerTeamB.getSelectedItem();
                LeagueDetails refereeA = (LeagueDetails) spinnerRefereeA.getSelectedItem();
                LeagueDetails refereeB = (LeagueDetails) spinnerRefereeB.getSelectedItem();
                LeagueDetails refereeC = (LeagueDetails) spinnerRefereeC.getSelectedItem();


                Map<String, String> params = new HashMap<String, String>();
                params.put("leagueId", leagueId.id);
                params.put("teamA", teamA.id);
                params.put("teamB", teamB.id);
                params.put("refereeA", refereeA.id);
                params.put("refereeB", refereeB.id);
                params.put("refereeC", refereeC.id);
                params.put("colorA", String.valueOf(((ColorDrawable) btnColorTeamA.getBackground()).getColor()));
                params.put("colorB", String.valueOf(((ColorDrawable) btnColorTeamB.getBackground()).getColor()));

                return params;
            }

        };

        queue.add(putRequest);

    }


    private void checkGameCode(final String gameCode) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, CheckGameCodeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            if (status.equals("false")) {
                                Toast.makeText(getApplicationContext(), "Invalid Game Code", Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("Response", response);
                                currentGame.setTeamA(obj.getString("teamA"));
                                currentGame.setTeamB(obj.getString("teamB"));
                                currentGame.setScoreA(obj.getInt("scoreA"));
                                Log.e("score", String.valueOf(obj.getInt("scoreA")));
                                currentGame.setScoreB(obj.getInt("scoreB"));
                                currentGame.setTimeInMillis(Long.parseLong(obj.getString("timeInMillis")));
                                currentGame.setPeriod(Integer.parseInt(obj.getString("period")));
                                currentGame.setColorTeamA(obj.getInt("teamAColor"));
                                currentGame.setColorTeamB(obj.getInt("teamBColor"));
                                currentGame.setLeagueName(obj.getString("leagueName"));
                                prepareEval(obj.getString("gameId"), obj.getString("gameCode"));


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gameCode", gameCode);
                return params;
            }

        };

        queue.add(putRequest);
    }

    private void prepareEval(final String gameId, final String gameCode) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest putRequest = new StringRequest(Request.Method.POST, GetGameDetailsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        CurrentGame thisGame = gson.fromJson(response, CurrentGame.class);
                        currentGame.setPlayerA(thisGame.playerA);
                        currentGame.setPlayerB(thisGame.playerB);
                        currentGame.setGameCode(gameCode);
                        currentGame.setGameId(gameId);
                        currentGame.setStaffA(thisGame.staffA);
                        currentGame.setStaffB(thisGame.staffB);
                        currentGame.setReferee(thisGame.referee);
                        currentGame.setTeamAId(thisGame.getTeamAId());
                        currentGame.setTeamBId(thisGame.getTeamBId());
                        currentGame.setPlayerA(thisGame.getPlayerA());
                        currentGame.setPlayerB(thisGame.getPlayerB());

                        Gson cur = new Gson();
                        String json = cur.toJson(currentGame);
                        Intent intent = new Intent(EvaluatorActivity.this, Evaluation.class);
                        intent.putExtra("playing", json);
                        startActivity(intent);

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
        Intent intent = new Intent(EvaluatorActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}