package com.ancroid.work.clockview;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClockView clockView = findViewById(R.id.clockview);
        clockView.setClockColor(Color.BLUE);
    }
}
