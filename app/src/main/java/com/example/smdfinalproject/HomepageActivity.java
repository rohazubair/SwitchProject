package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
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

public class HomepageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private List<Articles> articlesList;
    private FirebaseFirestore db;
    private static final String TAG = "HomepageActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_recyclerview);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Articles a1 = new Articles();
//        articlesList = new ArrayList<>();
//        a1.setTitle("Landmarks Detection and Correction for Pakistan Sign Language (PSL) Recognition");
//        a1.setDetail("Key Takeaways: The project, Landmarks Detection and Correction for Pakistan Sign Language (PSL) Recognition, stands at the forefront of technological");
//        a1.setImage("https://github.com/rohazubair/Switch/blob/b84bfb72a5752cbb493ab5181870f7b3626316cb/article1img.jpg?raw=true");
//        a1.setAudio("");
//        articlesList.add(a1);
//        articlesAdapter = new ArticlesAdapter(HomepageActivity.this, articlesList);
//        recyclerView.setAdapter(articlesAdapter);
//        articlesAdapter.notifyDataSetChanged();



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

}
