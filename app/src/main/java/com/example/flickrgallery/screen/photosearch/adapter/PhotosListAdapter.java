package com.example.flickrgallery.screen.photosearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flickrgallery.R;
import com.example.flickrgallery.screen.photosearch.model.Photo;

import java.util.List;

public class PhotosListAdapter extends RecyclerView.Adapter<PhotoListViewHolder> {

    protected List<Photo> photoList;

    public PhotosListAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public void onViewAttachedToWindow(PhotoListViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.start();
    }

    @Override
    public void onViewDetachedFromWindow(PhotoListViewHolder holder) {
        holder.destroy();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new PhotoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoListViewHolder holder, int position) {
        Photo item = photoList.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
