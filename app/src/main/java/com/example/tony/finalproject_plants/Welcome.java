package com.example.tony.finalproject_plants;

import android.app.Activity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;

/**
 * Created by Paul Hu on 2017/11/26.
 */

public class Welcome extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Welcome.this,MainActivity.class));
                finish();

                }
            }, 2200);
        }

    }

