package com.ufcg.les.aep.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.util.Constant;
import com.ufcg.les.aep.util.LogTag;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(Constant.TWO_SECONDS);
                    startActivity(new Intent(SplashScreen.this, FeedActivity.class));
                } catch (InterruptedException error) {
                    Log.e(LogTag.FAILURE, error.getMessage(), error);
                }
            }
        }.start();
    }
}
