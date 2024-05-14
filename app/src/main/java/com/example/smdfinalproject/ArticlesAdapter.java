package com.example.smdfinalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Articles> articlesList;
    private List<Articles> articlesListFull; // Copy of original list for filtering

    public ArticlesAdapter(Context context, List<Articles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
        this.articlesListFull = new ArrayList<>(articlesList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_homepage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles article = articlesList.get(position);

        holder.title.setText(article.getTitle());
        holder.detail.setText(article.getData());
        // Load and display image using Picasso
        Picasso.get()
                .load(article.getImage()) // Assuming getImage() returns the image URL
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.error_image) // Error image if unable to load
                .into(holder.imageViewArticleImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the item associated with the clicked position
                Articles item = articlesList.get(position);

                // Create an Intent to start ItemDetailsActivity
                Intent intent = new Intent(context, ActivityDetails.class);

                // Pass the item details as extras to the Intent
                intent.putExtra("itemTitle", item.getTitle());
                intent.putExtra("itemDescription", item.getData());
                intent.putExtra("itemImage", item.getImage());
                intent.putExtra("itemAudio", item.getAudio());
                // Add more extras as needed

                // Start the new activity
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    @Override
    public Filter getFilter() {
        return articlesFilter;
    }

    private Filter articlesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Articles> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(articlesListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Articles article : articlesListFull) {
                    if (article.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(article);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            articlesList.clear();
            articlesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, detail;
        ImageView imageViewArticleImage;


        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.subhead);
            imageViewArticleImage = itemView.findViewById(R.id.header_image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
