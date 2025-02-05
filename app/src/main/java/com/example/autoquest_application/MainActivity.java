package com.example.autoquest_application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // variable declaration
    private static int SPLASH_SCREEN = 4000;
    android.view.animation.Animation startAnim, endAnim;
    ImageView logo_image;
    TextView welcome_message, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // animations
        startAnim= AnimationUtils.loadAnimation(this, R.anim.start_animation);
        endAnim= AnimationUtils.loadAnimation(this, R.anim.end_animation);

        // initialisation
        logo_image = findViewById(R.id.imageView2);
        welcome_message = findViewById(R.id.textView1);
        slogan = findViewById(R.id.textView2);

        // setting animation
        logo_image.setAnimation(endAnim);
        welcome_message.setAnimation(startAnim);
        slogan.setAnimation(endAnim);

        // delay process and intent
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}