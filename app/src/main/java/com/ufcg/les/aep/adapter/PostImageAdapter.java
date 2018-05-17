package com.ufcg.les.aep.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class PostImageAdapter extends PagerAdapter {
  
  private final List<Bitmap> bitmapList;
  
  public PostImageAdapter(final List<Bitmap> bitmapList) {
    this.bitmapList = bitmapList;
  }
  
  @Override
  public int getCount() {
    return this.bitmapList.size();
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
    mImageView.setImageBitmap(this.bitmapList.get(position));
    container.addView(mImageView, 0);
    return mImageView;
  }
  
}
