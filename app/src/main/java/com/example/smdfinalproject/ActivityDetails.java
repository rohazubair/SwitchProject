package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ActivityDetails extends AppCompatActivity {

    ImageView PlayPause;
    TextView CurrTime, TotalTime;
    SeekBar PlayerBar;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    private ImageButton btnFavorite;
    private String articleTitle; // Assume this is passed when starting the activity
    private String userId;
    private DocumentReference userDocRef;

    Button playAudioButton;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Receive Data From Adapter
        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra("itemTitle");
        String itemDescription = intent.getStringExtra("itemDescription");
        String itemImage = intent.getStringExtra("itemImage");
        String itemAudio = intent.getStringExtra("itemAudio");

        TextView itemTitleTextView = findViewById(R.id.textViewTitle);
        TextView itemDescriptionTextView = findViewById(R.id.textViewDescription);
        ImageView itemImageView = findViewById(R.id.imageView);
        PlayPause = findViewById(R.id.playPause);
        CurrTime = findViewById(R.id.currentTime);
        TotalTime = findViewById(R.id.TotalDuration);
        PlayerBar = findViewById(R.id.playerSeekBar);
        PlayerBar.setMax(100);


        //Set data into XML components from adapter
        itemTitleTextView.setText(itemTitle);
        itemDescriptionTextView.setText(itemDescription);
        Picasso.get().load(itemImage).into(itemImageView);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());



        // Initialize play audio button
        PlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying())
                {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    Toast.makeText(ActivityDetails.this, "Paused.", Toast.LENGTH_SHORT).show();
                    PlayPause.setImageResource(R.drawable.baseline_play_circle_filled_24);
                }
                else{
                    Toast.makeText(ActivityDetails.this, "Button is pressed.", Toast.LENGTH_SHORT).show();
                    playAudio(itemAudio);
                    PlayPause.setImageResource(R.drawable.baseline_adjust_24);
                }



            }
        });
        handler.postDelayed(updater, 1000);

        // Initialize Firebase Auth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userDocRef = db.collection("users").document(userId);

        // Initialize UI elements
        btnFavorite = findViewById(R.id.imageStarButton);

        // Get the article title (assuming it's passed via intent)
       // articleTitle = getIntent().getStringExtra("itemTitle");

        // Set click listener for the favorite button
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite(itemTitle);
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



    private void addFavorite(String title) {
        // Use Firestore's merge feature to add the favorite field without overwriting existing data
        Map<String, Object> favoriteMap = new HashMap<>();
        favoriteMap.put(title, true);

        Map<String, Object> favorites = new HashMap<>();
        favorites.put("favorites", favoriteMap);

        userDocRef.set(favorites, SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ActivityDetails.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ActivityDetails.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playAudio(String itemAudioUrl) {

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(itemAudioUrl);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    Toast.makeText(ActivityDetails.this, "Audio will play shortly", Toast.LENGTH_SHORT).show();
                    // Set the total duration text view
                    TotalTime.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ActivityDetails.this, "Error playing audio", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long CurrentDuration = mediaPlayer.getCurrentPosition();
            CurrTime.setText(milliSecondsToTimer(CurrentDuration));

            handler.postDelayed(this, 1000);
        }
    };

    private void updateSeekBar()
    {
        if(mediaPlayer.isPlaying())
        {
            PlayerBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            handler.postDelayed(updater, 1000);
        }
    }


    private String milliSecondsToTimer(long Milliseconds)
    {
        String timerString=" ", secondString;
        int hours = (int)(Milliseconds/(1000*60*60));
        int minutes = (int)(Milliseconds%(1000*60*60))/(1000*60);
        int seconds = (int)((Milliseconds%(1000*60*60))%(1000*60)/1000);

        if (hours>0)
        {
            timerString = hours + ":";
        }
        if (seconds < 10)
        {
            secondString = "0" + seconds;
        }
        else
        {
            secondString = "" + seconds;
        }
        timerString = timerString + minutes + ":" +seconds;
        return timerString;
    }

}