package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    private League details;

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

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

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
                try{
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
                        } else {
                            if (spinnerTeamA.getSelectedItem().toString().equals(spinnerTeamB.getSelectedItem().toString())) {
                                Erro1 = "Same teams are not allowed.";
                            }
                            if (ref1.equals(ref2) || ref1.equals(ref3) || ref2.equals(ref3)) {
                                Erro2 = " Same ref are not allowed.";
                            } else {
                                createGame();
                            }

                            Toast.makeText(EvaluatorActivity.this,
                                    Erro1 + Erro2, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        checkGameCode(edtGameCode.getText().toString());////
                        ello
                    }
                }catch (Exception e){
                    Toast.makeText(EvaluatorActivity.this,
                            "Empty items are not allowed", Toast.LENGTH_LONG).show();
                }
            }
        });
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

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();


                        editor.putString("teamA", teamA.name);
                        editor.putString("teamB", teamB.name);
                        editor.putInt("scoreA", 0);
                        editor.putInt("scoreB", 0);
                        editor.apply();
                        prepareEval(gameId.gameId, gameId.gameCode);
//
//                        Intent intent = new Intent(getApplicationContext(), Evaluation.class);
//                        intent.putExtra("gameId", gameId.gameId);
//                        intent.putExtra("gameCode", gameId.gameCode);
//                        startActivity(intent);


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
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("teamA", obj.getString("teamA"));
                                editor.putString("teamB", obj.getString("teamB"));
                                editor.putInt("scoreA", obj.getInt("scoreA"));
                                editor.putInt("scoreB", obj.getInt("scoreB"));
                                editor.apply();
                                prepareEval(obj.getString("gameId"), gameCode);
//                                Intent intent = new Intent(EvaluatorActivity.this, Evaluation.class);
//                                intent.putExtra("gameId", obj.getString("gameId"));
//                                intent.putExtra("gameCode", gameCode);
//                                startActivity(intent);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        GameId gameId = gson.fromJson(response, GameId.class);


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
                        Game game = gson.fromJson(response, Game.class);
                        CurrentGame currentGame = new CurrentGame();
                        String[] tempA = new String[5];
                        String[] tempB = new String[5];
                        for (int i = 0; i < 5; i++) {
                            tempA[i] = game.playerA.get(i).jerseyNumber;
                            tempB[i] = game.playerB.get(i).jerseyNumber;
                        }
                        currentGame.setPlayingA(tempA);
                        currentGame.setPlayingB(tempB);
                        Gson cur = new Gson();
                        String json = cur.toJson(currentGame);
                        Log.e("JSON", currentGame.getPlayingA()[0]);
                        Intent intent = new Intent(EvaluatorActivity.this, Evaluation.class);
                        intent.putExtra("gameId", gameId);
                        intent.putExtra("gameCode", gameCode);
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


}