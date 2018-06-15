package com.ufcg.les.aep.model.media;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ufcg.les.aep.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class ImageViewHolder extends RecyclerView.ViewHolder {
  
  private final Context context;
  @BindView(R.id.capturedImage_imageView)
  /*default*/ ImageView capturedImage;
  
  public ImageViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
    ButterKnife.bind(this, itemView);
  
  }
  
  public void bind(final AbstractMedia media) {
    Bitmap thumbnail = null;
    ContentResolver contentResolver = context.getContentResolver();
    try {
      thumbnail = MediaStore.Images.Media.getBitmap(contentResolver, media.getUri());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
    capturedImage.setImageBitmap(thumbnail);
    capturedImage.setScaleType(ImageView.ScaleType.FIT_XY);
  }
}
