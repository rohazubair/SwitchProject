package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class ActivityDetails extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    MediaController mediaController;
    Button playAudioButton;
    Intent intent = getIntent();
    String itemAudio = intent.getStringExtra("itemAudio");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        MediaPlayer mediaPlayer;
        MediaController mediaController;
        Button playAudioButton;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra("itemTitle");
        String itemDescription = intent.getStringExtra("itemDescription");
        String itemImage = intent.getStringExtra("itemImage");
        String itemAudio = intent.getStringExtra("itemAudio");

        TextView itemTitleTextView = findViewById(R.id.textViewTitle);
        TextView itemDescriptionTextView = findViewById(R.id.textViewDescription);
        ImageView itemImageView = findViewById(R.id.imageView);


        itemTitleTextView.setText(itemTitle);
        itemDescriptionTextView.setText(itemDescription);
        Picasso.get().load(itemImage).into(itemImageView);

//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .setUsage(AudioAttributes.USAGE_MEDIA)
//                .build());
//
//        // Initialize MediaController
//        mediaController = new MediaController(this);


        // Initialize play audio button
        playAudioButton = findViewById(R.id.buttonPlayAudio);
        playAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // playAudio();
            }
        });

    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.drawable.menu_item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when the Up button is pressed
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void playAudio() {
//        try {
//            mediaPlayer.reset();
//            mediaPlayer.setDataSource(itemAudio);
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mediaPlayer.start();
//                    mediaController.show();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
}