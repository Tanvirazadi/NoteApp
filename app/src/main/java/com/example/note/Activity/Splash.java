package com.example.note.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.note.R;

public class Splash extends AppCompatActivity {
    ImageView img;
    Animation zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        img = findViewById(R.id.splashimg);
        img.startAnimation(zoom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,MainActivity.class));
                finish();

            }
        },3000);

    }
}