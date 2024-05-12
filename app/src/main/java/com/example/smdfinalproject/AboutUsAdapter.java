package com.example.smdfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder> {

    private Context context;
    private List<Integer> imageList;

    public AboutUsAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public AboutUsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.about_us_cardview, parent, false);
        return new AboutUsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutUsViewHolder holder, int position) {
        int imageResId = imageList.get(position);
        holder.imageView.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class AboutUsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public AboutUsViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view1);
        }
    }
}

