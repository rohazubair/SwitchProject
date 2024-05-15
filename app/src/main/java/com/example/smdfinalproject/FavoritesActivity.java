package com.example.smdfinalproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnItemClickListener {


    private RecyclerView recyclerView;
    private FavoritesAdapter adapter; // You need to create this adapter
    private List<String> favoriteArticles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewFavorites); // Add a RecyclerView to your layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteArticles = new ArrayList<>();
        adapter = new FavoritesAdapter(favoriteArticles, this);
        recyclerView.setAdapter(adapter);

        fetchFavorites();
    }

    private void fetchFavorites() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userDocRef = db.collection("users").document(userId);

        userDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> favorites = (Map<String, Object>) document.get("favorites");
                    if (favorites != null) {
                        for (Map.Entry<String, Object> entry : favorites.entrySet()) {
                            String articleTitle = entry.getKey();
                            favoriteArticles.add(articleTitle);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(FavoritesActivity.this, "No favorites found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FavoritesActivity.this, "Failed to fetch favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onItemClick(int position) {
        showDeleteConfirmationDialog(position);
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Favorite");
        builder.setMessage("Are you sure you want to delete this favorite?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Delete the item from the list
            favoriteArticles.remove(position);
            // Notify the adapter
            adapter.notifyItemRemoved(position);
            // Dismiss the dialog
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

