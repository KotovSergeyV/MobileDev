package ru.mirea.kotovsv.mireaproject;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Uri> imageUris = new ArrayList<>();
    private final int imageSize;

    public ImageAdapter(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int padding = (int) (3 * 2 * metrics.density); // 4dp padding
        this.imageSize = (screenWidth - padding) / 3;   // 3 image в строке
    }

    public void addImage(Uri uri) {
        imageUris.add(uri);
        notifyItemInserted(imageUris.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());

        // resize image SK
        imageView.setLayoutParams(new ViewGroup.LayoutParams(imageSize, imageSize));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // Scale down
        imageView.setPadding(3, 3, 3, 3);    // Internal padding

        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageURI(imageUris.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ViewHolder(ImageView itemView) {
            super(itemView);
            imageView = itemView;
        }
    }
}