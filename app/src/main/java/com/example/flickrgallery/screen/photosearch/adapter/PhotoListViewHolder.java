package com.example.flickrgallery.screen.photosearch.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.flickrgallery.R;
import com.example.flickrgallery.injection.Injector;
import com.example.flickrgallery.screen.photosearch.model.Photo;
import com.example.flickrgallery.screen.photosearch.presenter.PhotoItemPresenter;
import com.example.flickrgallery.screen.photosearch.view.PhotoItemView;

public class PhotoListViewHolder extends RecyclerView.ViewHolder implements PhotoItemView {
    private final Handler handler;

    PhotoItemPresenter presenter;

    protected ImageView photoView;
    protected ProgressBar progress;
    protected TextView errorMessage;
    protected Button retry;

    public PhotoListViewHolder(View itemView) {
        super(itemView);
        photoView = itemView.findViewById(R.id.photo);
        progress = itemView.findViewById(R.id.progress);
        errorMessage = itemView.findViewById(R.id.errorMessage);
        retry = itemView.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.retry();
            }
        });
        handler = new Handler();
    }

    public void setItem(Photo item) {
        this.presenter = new PhotoItemPresenter(
                item,
                Injector.provideImageLoader(),
                Injector.providePhotoUrlProvider(),
                Injector.provideThrowableTranslator()
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
}
