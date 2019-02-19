package com.ufcg.les.aep.model.post;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostImageAdapter;
import com.ufcg.les.aep.view.activity.PostDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostViewHolder extends RecyclerView.ViewHolder {
  
  private final Context context;
  
  @BindView(R.id.post_title_tv)
  TextView title;
  
//  @BindView(R.id.postImage_imageView)
//  ImageView imagePost;
  
  @BindView(R.id.postImages_viewPager)
  ViewPager imageViewPager;
  
  @BindView(R.id.post_description_tv)
  TextView description;
  
  @BindView(R.id.postType_textView)
  /*default*/ TextView postType;
  
  private Post post;
  
  public PostViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
    ButterKnife.bind(this, itemView);
  }
  
  private void initViewPager() {
    final PostImageAdapter imageAdapter = new PostImageAdapter(this.post.getMediaList());
    imageViewPager.setAdapter(imageAdapter);
  }
  
  @SuppressLint({ "ResourceAsColor", "NewApi" })
  public void bind(final Post post) {
    this.post = post;
    this.title.setText(post.getTitle());
    this.description.setText(post.getDescription());
    this.postType.setBackgroundColor(context.getColor(post.getTypeColor()));
    this.postType.setText(post.getType());
    initViewPager();
  }
  
  
  @OnClick
  public void onClick() {
    if (context instanceof FragmentActivity) {
      final FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
      if (fragmentManager != null) {
        fragmentManager.beginTransaction().replace(R.id.content_frame_layout, PostDetailsFragment.newInstance(post)).commit();
      }
    }
    
    
//    final Intent detailsIntent = new Intent(this.context, PostDetailsFragment.class);
//    detailsIntent.putExtra(POST, this.post);
//    this.context.startActivity(detailsIntent);//TODO Start the specific activity of the current post.
  }
  
  @OnClick(R.id.postImages_viewPager)
  public void onViewPagerClick() {
//    onClick();
  }
  
  @OnClick(R.id.postType_textView)
  public void onTypeClick() {
    System.out.println("TYPE");
  }
  
}
