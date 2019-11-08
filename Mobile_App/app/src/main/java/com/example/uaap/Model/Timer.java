package com.example.uaap.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import com.example.uaap.R;


public class Timer extends AppCompatActivity {
    Button minuteUp, secondUp, miliUp, minuteDown, secondDown, miliDown, start, reset;
    TextView minutes, seconds, milliseconds;
    int finalMinute, finalSecond, finalMilli;
    private Handler handler = new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        minuteUp = findViewById(R.id.minuteUp);
        secondUp = findViewById(R.id.secondUp);
        miliUp = findViewById(R.id.miliUp);
        minuteDown = findViewById(R.id.minuteDown);
        secondDown = findViewById(R.id.secondDown);
        miliDown = findViewById(R.id.miliDown);
        minutes = findViewById(R.id.minutes);
        seconds = findViewById(R.id.seconds);
        milliseconds = findViewById(R.id.millisecods);
        start = findViewById(R.id.start);
        reset = findViewById(R.id.reset);
        final String minute = minutes.getText().toString();
        finalMinute = Integer.parseInt(minute);
        String second = seconds.getText().toString();
        finalSecond = Integer.parseInt(second);
        String milli = milliseconds.getText().toString();
        finalMilli = Integer.parseInt(milli);
        finalMinute = 10;
        finalSecond = 0;
        finalMilli = 60;
        update();


        minuteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMinute >= 10) {
                    finalMinute = 0;

                } else {
                    finalMinute = finalMinute + 1;
                }
                update();
            }
        });

        secondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalSecond >= 60) {
                    finalSecond = 0;

                } else {
                    finalSecond = finalSecond + 1;
                }
                update();
            }
        });

        miliUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMilli >= 60) {
                    finalMilli = 0;
                } else {
                    finalMilli = finalMilli + 1;
                }
                update();
            }
        });

        minuteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMinute <= 0) {
                    finalMinute = 10;

                } else {
                    finalMinute = finalMinute - 1;
                }
                update();

            }
        });

        secondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalSecond <= 0) {
                    finalSecond = 60;

                } else {
                    finalSecond = finalSecond - 1;
                }
                update();
            }
        });

        miliDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalMilli <= 0) {
                    finalMilli = 60;
                } else {
                    finalMilli = finalMilli - 1;
                }
                update();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disable();
                countDown();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void update(){
        String m = Integer.toString(finalMinute);
        String s= Integer.toString(finalSecond);
        String ms = Integer.toString(finalMilli);
        minutes.setText(m);
        seconds.setText(s);
        milliseconds.setText(ms);
    }

    private void disable(){
        minuteUp.setEnabled(false);
        secondUp.setEnabled(false);
        miliUp.setEnabled(false);
        minuteDown.setEnabled(false);
        secondDown.setEnabled(false);
        miliDown.setEnabled(false);

    }


    private void countDown(){
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    finalMinute = finalMinute - 1;
                    update();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}


