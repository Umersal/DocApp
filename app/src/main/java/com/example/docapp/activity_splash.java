package com.example.docapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //to get rid of action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        logo = (ImageView) findViewById(R.id.logostart);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(activity_splash.this,MainActivity.class);
                activity_splash.this.startActivity(mainIntent);
                activity_splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        logo.startAnimation(myanim);
    }
}

