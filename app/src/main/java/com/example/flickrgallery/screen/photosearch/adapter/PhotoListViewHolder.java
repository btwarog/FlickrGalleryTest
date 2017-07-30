package com.example.flickrgallery.screen.photosearch.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;
import com.example.flickrgallery.injection.Injector;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.model.PhotoMeadata;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoItemPresenter;
import com.example.flickrgallery.screen.photosearch.view.PhotoItemView;

public class PhotoListViewHolder extends RecyclerView.ViewHolder implements PhotoItemView, View.OnClickListener {
    private final Handler handler;
    private final OnPhotoClickedListener onPhotoClickedListener;

    PhotoItemPresenter presenter;

    protected ImageView photoView;
    protected ProgressBar progress;
    protected TextView errorMessage;
    protected ViewGroup metadataContainer;
    protected TextView title;
    protected TextView dateTaken;
    protected TextView datePublished;
    protected Button retry;
    private Photo item;

    public PhotoListViewHolder(View itemView, OnPhotoClickedListener onPhotoClickedListener) {
        super(itemView);
        this.onPhotoClickedListener = onPhotoClickedListener;
        handler = new Handler();

        initView(itemView);
    }

    private void initView(View itemView) {
        photoView = itemView.findViewById(R.id.photo);
        itemView.setOnClickListener(this);
        progress = itemView.findViewById(R.id.progress);
        errorMessage = itemView.findViewById(R.id.errorMessage);
        metadataContainer = itemView.findViewById(R.id.metadata_container);
        title = itemView.findViewById(R.id.title);
        dateTaken = itemView.findViewById(R.id.date_taken);
        datePublished = itemView.findViewById(R.id.date_published);
        retry = itemView.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.retry();
            }
        });
    }

    public void setItem(Photo item) {
        this.item = item;
        this.presenter = new PhotoItemPresenter(
                item,
                Injector.provideImageLoader(),
                Injector.providePhotoUrlProvider(),
                Injector.provideThrowableTranslator(),
                Injector.provideLoadPhotoMetadataAction()
        );
        this.presenter.setView(this);
    }

    public void start() {
        presenter.start();
    }

    public void destroy() {
        presenter.destroy();
    }

    @Override
    public void showBitmap(final Bitmap bitmap) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                photoView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void showLoading(final boolean show) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void showFailed(final boolean show, final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                errorMessage.setVisibility(show ? View.VISIBLE : View.GONE);
                retry.setVisibility(show ? View.VISIBLE : View.GONE);
                if(message != null) {
                    errorMessage.setText(message);
                }
            }
        });
    }

    @Override
    public void showPhotoMetadata(final PhotoMeadata loadedPhotoMetadata) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(loadedPhotoMetadata == null) {
                    metadataContainer.setVisibility(View.INVISIBLE);
                } else {
                    metadataContainer.setVisibility(View.VISIBLE);
                    title.setText(loadedPhotoMetadata.getTitle());
                    datePublished.setText(loadedPhotoMetadata.getPublished());
                    dateTaken.setText(loadedPhotoMetadata.getDateTaken());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        onPhotoClickedListener.onPhotoClicked(item);
    }
}
