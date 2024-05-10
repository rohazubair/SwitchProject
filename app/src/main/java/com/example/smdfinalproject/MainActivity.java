package com.example.smdfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MainActivity extends AppCompatActivity {


    private static final int ROTATION_DURATION = 1000;
    Handler handler = new Handler ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding the ImageView for the logo
        final ImageView imageView = findViewById(R.id.imageViewlogo);

        // Creating the rotation animator
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f);
        rotationAnimator.setDuration(ROTATION_DURATION);
        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        // Creating the scaling animator
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.5f);
        scaleXAnimator.setDuration(ROTATION_DURATION / 2);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.5f);
        scaleYAnimator.setDuration(ROTATION_DURATION / 2);

        // Combining rotation and scaling animators into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotationAnimator).with(scaleXAnimator).with(scaleYAnimator);

        // Starting the rotation and scaling animation
        animatorSet.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);




    }

}