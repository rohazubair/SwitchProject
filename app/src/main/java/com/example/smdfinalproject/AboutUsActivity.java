package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AboutUsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AboutUsAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_us);
        recyclerView = findViewById(R.id.about_us_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Integer> imageList = getImageList(); // Function to get list of image resource IDs
        adapter = new AboutUsAdapter(this, imageList);
        recyclerView.setAdapter(adapter);
    }
    private List<Integer> getImageList() {
        List<Integer> imageList = new ArrayList<>();
        // Add your image resource IDs here
        imageList.add(R.drawable.samra);
        imageList.add(R.drawable.zaidi);
        imageList.add(R.drawable.shaheer);
        imageList.add(R.drawable.sijal);
        imageList.add(R.drawable.mamoona);
        imageList.add(R.drawable.roha);
        imageList.add(R.drawable.fareeha);

        // Add more images if needed
        return imageList;
    }
}