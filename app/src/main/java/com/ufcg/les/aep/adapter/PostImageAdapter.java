package com.ufcg.les.aep.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView;
import com.ufcg.les.aep.model.media.AbstractMedia;

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
    AdjustableImageView imageView = new AdjustableImageView(container.getContext());
    imageView.setAdjustViewBounds(true);
    
    
//    ImageView mImageView = new ImageView(container.getContext());
//    mImageView.setScaleType(ImageView.ScaleType.MATRIX);
//    mImageView.setScaleType(ImageView.ScaleType.CENTER);
//    mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
//    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//    mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//    mImageView.setScaleType(ImageView.ScaleType.FIT_END);
//    mImageView.setScaleType(ImageView.ScaleType.FIT_START);
    
    
    imageView.setImageBitmap(this.mediaList.get(position).getThumbnail());
    container.addView(imageView, 0);
    return imageView;
  }
  
}
