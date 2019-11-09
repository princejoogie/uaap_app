package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Manage.AddTeam;
import com.example.uaap.Manage.EvaluationSummary;
import com.example.uaap.Model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private CardView login_pressed;
    private EditText edtUsername;
    private EditText edtPassword;
    private String URL = "http://68.183.49.18/uaap/public/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        login_pressed = findViewById(R.id.login_pressed);

        login_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                logging(username, password);

            }
        });

    }

    private void logging(final String username,final String password) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response, User.class);

                        if(user.status.equals("true")){
                            if (user.result.get(0).accountType.equals("evaluator")){
                                Intent intent = new Intent(getApplicationContext(), EvaluationSummary.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "admin", Toast.LENGTH_LONG).show();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You have entered an invalid username or password", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        queue.add(putRequest);
    }

//    public void login_pressed(View v) {
//        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//    }
}
