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
<<<<<<< HEAD
=======
    int counter = 0;
>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
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
<<<<<<< HEAD
        finalMinute = 10;
        finalSecond = 0;
        finalMilli = 60;
=======
        finalMinute = 9;
        finalSecond = 59;
        finalMilli = 59;
>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
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
<<<<<<< HEAD
                disable();
                countDown();

=======
                buttonStart();
>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD

=======
                onReset();
>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
            }
        });

    }
<<<<<<< HEAD
=======

    private void buttonStart(){
        if (counter == 0) {
            counter = counter + 1;
            disable();
            countDown();
            start.setText("Pause");
        }else{
            onStop();
            counter = 0;
            start.setText("Start");
        }
    }

    private void onReset(){
        onStop();
        enable();
        finalMinute = 9;
        finalSecond = 59;
        finalMilli = 59;
        update();
        start.setText("Start");
    }

>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
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

<<<<<<< HEAD

=======
>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
    private void countDown(){
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
<<<<<<< HEAD
                    handler.postDelayed(this, 1000);
                    finalMinute = finalMinute - 1;
                    update();
=======
                    handler.postDelayed(this, 1);
                    finalMilli = finalMilli- 1;
                    update();
                    if(finalMilli== 0){
                        finalSecond = finalSecond - 1;
                        finalMilli = 60;
                        if(finalSecond == 0){
                            finalMinute = finalMinute - 1;
                            finalSecond = 60;
                            finalMilli = 60;
                            if(finalMinute == 0){
                                finalMinute = 9;
                                finalSecond = 59;
                                finalMilli = 59;
                                update();
                                onStop();
                                enable();
                            }
                        }
                    }
>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
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
<<<<<<< HEAD
=======

    private void enable(){
        minuteUp.setEnabled(true);
        secondUp.setEnabled(true);
        miliUp.setEnabled(true);
        minuteDown.setEnabled(true);
        secondDown.setEnabled(true);
        miliDown.setEnabled(true);

    }


>>>>>>> 16a15aa44c812737f9868f24338eb9de9f3261df
}


