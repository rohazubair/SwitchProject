package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
public class HomepageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private List<Articles> articlesList;
    private FirebaseFirestore db;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private static final String TAG = "HomepageActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_recyclerview);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handling navigation item clicks
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent homeIntent = new Intent(HomepageActivity.this, HomepageActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.nav_about_us:
                        Intent aboutUsIntent = new Intent(HomepageActivity.this, AboutUsActivity.class);
                        startActivity(aboutUsIntent);
                        break;
                    case R.id.nav_courses:
                        Intent coursesIntent = new Intent(HomepageActivity.this, CoursesActivity.class);
                        startActivity(coursesIntent);
                        break;
                }
                return true;
            }
        });

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        CollectionReference articlesRef = db.collection("Articles");
        articlesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    articlesList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String Title = document.getString("Title");
                        String Data = document.getString("Data");
                        String image = document.getString("image");
                        String audio = document.getString("audio");

                        Articles article = new Articles(Title, Data, image, audio);
                        articlesList.add(article);
                    }
                    articlesAdapter = new ArticlesAdapter(HomepageActivity.this, articlesList);
                    recyclerView.setAdapter(articlesAdapter);
                    articlesAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
