package com.ufcg.les.aep.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.util.service.UserImageStorage;

import java.io.IOException;

public class ImageStorageAdapter extends BaseAdapter {
  
  private static ImageStorageAdapter instance;
  private final Context context;
  
  private ImageStorageAdapter(final Context context) {
    this.context = context;
  }
  
  public static ImageStorageAdapter getInstance(final Context context) {
    if (instance == null) {
      instance = new ImageStorageAdapter(context);
    }
    
    return instance;
  }
  
  @Override
  public int getCount() {
    return UserImageStorage.getInstance().getItemCount();
  }
  
  @Override
  public AbstractMedia getItem(final int position) {
    return UserImageStorage.getInstance().getByPosition(position);
  }
  
  @Override
  public long getItemId(final int position) {
    return UserImageStorage.getInstance().getByPosition(position).hashCode();
  }
  
  @Override
  public View getView(final int position, final View view, final ViewGroup viewGroup) {
    ImageView imageView;
    if (view == null) {
      imageView = new ImageView(this.context);
      imageView.setLayoutParams(new GridView.LayoutParams(440, 440));
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    } else {
      imageView = (ImageView) view;
    }
    
    imageView.setImageBitmap(getBitmap(getItem(position)));
    return imageView;
  }
  
  @Nullable
  private Bitmap getBitmap(final AbstractMedia media) {
    Bitmap thumbnail = media.getThumbnail();
    if (thumbnail == null) {
  
      ContentResolver contentResolver = context.getContentResolver();
      try {
        thumbnail = MediaStore.Images.Media.getBitmap(contentResolver, media.getUri());
        media.setThumbnail(thumbnail);
      } catch (IOException | NullPointerException e) {
        e.printStackTrace();
      }
    }
    return thumbnail;
  }
  
  
}
