package com.example.uaap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Adapter.EvaluationListAdapter;
import com.example.uaap.Adapter.RefSumListAdapter;
import com.example.uaap.Adapter.ViewPagerAdapter;
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.EvaluationModel;
import com.example.uaap.Model.RefSumModel;
import com.example.uaap.Model.RefereeSum;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AfterRefereeSummary extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private CurrentGame currentGame;
    private String playing;
    private RefSumListAdapter listAdapter;
    private RefSumModel calls;
    private String GetRefSummaryURL = "http://68.183.49.18/uaap/public/getRefSummary";
    private ListView refSumList;

    private Button btnToGameSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_referee_summary);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            playing = extras.getString("playing");

        }
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);

        refSumList = findViewById(R.id.refSumList);
        btnToGameSum = findViewById(R.id.btnToGameSum);
        getRefSum();

        btnToGameSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterRefereeSummary.this, AfterGameSummary.class);
                intent.putExtra("playing", playing);
                startActivity(intent);
            }
        });
    }
    private void getRefSum(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, GetRefSummaryURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        Gson gson = new Gson();
                        calls = gson.fromJson(response, RefSumModel.class);
                        ArrayList<RefereeSum> dataModelArrayList = calls.result;
                        if (!dataModelArrayList.isEmpty()) {
                            listAdapter = new RefSumListAdapter(getApplicationContext(), dataModelArrayList);
                            refSumList.setAdapter(listAdapter);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AfterRefereeSummary.this, AfterGameSummary.class);
        intent.putExtra("playing", playing);
        startActivity(intent);
    }
}