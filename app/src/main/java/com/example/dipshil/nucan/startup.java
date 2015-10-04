package com.example.dipshil.nucan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Dipshil on 07-08-2015.
 */
public class startup extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        AppController ac= new AppController();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        new CountDownTimer(700,30){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                Intent i = new Intent(startup.this,MainActivity.class);
                startActivity(i);

            }
        }.start();
    }
}
