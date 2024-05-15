package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
    private EditText searchEditText;

    ImageButton searchButton;

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
                    case R.id.nav_favourites:
                        Intent favouritesIntent = new Intent(HomepageActivity.this, FavoritesActivity.class);
                        startActivity(favouritesIntent);
                        break;
                    case R.id.nav_feedback:
                        Intent feedbackIntent = new Intent(HomepageActivity.this, FeedbackActivity.class);
                        startActivity(feedbackIntent);
                        break;
                    case R.id.nav_signOut:
                        signOut();
                        return true;

                }
                return true;
            }
        });

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEditText.getVisibility() == View.VISIBLE) {
                    searchEditText.setVisibility(View.GONE);
                } else {
                    searchEditText.setVisibility(View.VISIBLE);
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        loadArticles();

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

    private void filter(String text) {
        List<Articles> filteredList = new ArrayList<>();
        for (Articles article : articlesList) {
            if (article.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(article);
            }
        }
        articlesAdapter.getFilter().filter(text); // Update this line
    }

    private void loadArticles() {
        CollectionReference articlesRef = db.collection("Articles");
        articlesRef.orderBy("Title", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation item clicks here
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(HomepageActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(HomepageActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
