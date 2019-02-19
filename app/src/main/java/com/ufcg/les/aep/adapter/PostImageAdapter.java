package com.ufcg.les.aep.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ufcg.les.aep.model.media.AbstractMedia;

import java.util.List;

public class PostImageAdapter extends PagerAdapter {
  
  
  private final List<AbstractMedia> mediaList;
  private ImageView mImageView;
  
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
    container.removeView((View) object);
    unbindDrawables((View) object);
  }
  
  private void unbindDrawables(View view) {
    if (view.getBackground() != null) {
      view.getBackground().setCallback(null);
    }
    if (view instanceof ViewGroup) {
      for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
        unbindDrawables(((ViewGroup) view).getChildAt(i));
      }
      ((ViewGroup) view).removeAllViews();
    }
  }
  
  @Override
  public boolean isViewFromObject(@NonNull final View view, @NonNull final Object object) {
    return view == object;
  }
  
  @NonNull
  @Override
  public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
    
    mImageView = new ImageView(container.getContext());
    
    setupImageClickListener();
    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    Bitmap thumbnail = this.mediaList.get(position).getThumbnail();
  
    mImageView.setImageBitmap(thumbnail);
    
    container.addView(mImageView, 0);
    return mImageView;
  }
  
  private void setupImageClickListener() {
    mImageView.setOnClickListener(view -> System.out.println("ON CLICK"));
  }
  
}
