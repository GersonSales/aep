package com.ufcg.les.aep.adapter;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ufcg.les.aep.model.media.AbstractMedia;

import java.io.IOException;
import java.util.List;

public class PostImageAdapter extends PagerAdapter {
  
  private final List<AbstractMedia> mediaList;
  
  public PostImageAdapter(final List<AbstractMedia> mediaList) {
    this.mediaList = mediaList;
  }
  
  @Override
  public int getCount() {
    return this.mediaList.size();
  }
  
  @Override
  public void destroyItem(@NonNull final ViewGroup container, final int position,
                          @NonNull final Object object) {
    container.removeView((ImageView) object);
  }
  @Override
  public boolean isViewFromObject(@NonNull final View view, @NonNull final Object object) {
    return view == object;
  }
  
  @NonNull
  @Override
  public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
    ImageView mImageView = new ImageView(container.getContext());
    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

    Bitmap thumbnail = null;//this.mediaList.get(position).getThumbnail();
    ContentResolver contentResolver = container.getContext().getContentResolver();
    try {
      thumbnail = MediaStore.Images.Media.getBitmap(contentResolver, this.mediaList.get(position).getUri());
    } catch (IOException e) {
      e.printStackTrace();
    }
    mImageView.setImageBitmap(thumbnail);
    
    container.addView(mImageView, 0);
    return mImageView;
  }
  
}
