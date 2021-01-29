package com.player.coachesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 4000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        NextScreen();
    }

    public void NextScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayerPrefrences.getInfo(Constant.LOGIN_STATUS, context).equalsIgnoreCase("1"))
                    CallIntent(1);
                else
                    CallIntent(0);
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }


    public void CallIntent(int var0) {
        if (var0 == 0) {
            startActivity(new Intent(SplashActivity.this, SummaryActivity.class));
            finish();
        } else {
            String id = "", type = "";
            try{
                if(null != getIntent() && getIntent().hasExtra(Constant.REFERENCE_ID)){
                    id = getIntent().getStringExtra(Constant.REFERENCE_ID);
                    type = getIntent().getStringExtra(Constant.NOTIFY_TYPE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if(TextUtils.isEmpty(id) && TextUtils.isEmpty(type) ){
                startActivity(new Intent(SplashActivity.this, HomeScreen.class));
            }else {
                startActivity(new Intent(SplashActivity.this, HomeScreen.class)
                        .putExtra(Constant.REFERENCE_ID, id)
                        .putExtra(Constant.NOTIFY_TYPE, type)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

            finish();
        }
    }

}
