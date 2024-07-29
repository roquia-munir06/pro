package com.hiskytech.feastfleet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterImage extends FirestoreRecyclerAdapter<ModelImage, AdapterImage.ImageViewHolder> {
    private final Context context;

    public AdapterImage(FirestoreRecyclerOptions<ModelImage> options, Context context) {
        super(options);
        this.context = context;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    protected void onBindViewHolder(ImageViewHolder holder, int position, ModelImage model) {
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityDetails.class);
            intent.putExtra("imageUrl", model.getImageUrl());
            intent.putExtra("name", model.getName());
            intent.putExtra("description", model.getDescription());
            intent.putExtra("price", model.getPrice());
            context.startActivity(intent);
        });
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }
}
