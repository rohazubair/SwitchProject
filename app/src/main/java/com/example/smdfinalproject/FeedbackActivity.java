package com.example.smdfinalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FeedbackActivity extends AppCompatActivity {

    EditText editTextEmailAddress, editTextFeedback;
    Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextFeedback = findViewById(R.id.editTextFeedback);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackEmail();
            }
        });
    }

        private void sendFeedbackEmail() {
            String emailAddress = editTextEmailAddress.getText().toString().trim();
            String feedback = editTextFeedback.getText().toString().trim();

            if (emailAddress.isEmpty() || feedback.isEmpty()) {
                Toast.makeText(this, "Please enter email address and feedback", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] TO = {"switch.magazine1@gmail.com"}; // Administrator's email address
            String[] CC = {emailAddress}; // User's email address

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from " + emailAddress);
            emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send feedback..."));
                Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}