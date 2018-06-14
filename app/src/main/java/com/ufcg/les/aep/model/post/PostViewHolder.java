package com.ufcg.les.aep.model.post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.activity.PostDetailsActivity;
import com.ufcg.les.aep.adapter.PostImageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ufcg.les.aep.util.Tag.POST;

public class PostViewHolder extends RecyclerView.ViewHolder {
  
  private final Context context;
  
  @BindView(R.id.postTitle_textView)
  TextView title;
  
//  @BindView(R.id.postImage_imageView)
//  ImageView imagePost;
  
  @BindView(R.id.postImages_viewPager)
  ViewPager imageViewPager;
  
  @BindView(R.id.postDescription_textView)
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
    this.postType.setText(post.getTagType());
    initViewPager();
  }
  
  
  @OnClick
  public void onClick() {
    final Intent detailsIntent = new Intent(this.context, PostDetailsActivity.class);
    detailsIntent.putExtra(POST, this.post);
    this.context.startActivity(detailsIntent);//TODO Start the specific activity of the current post.
  }
  
  @OnClick(R.id.postImages_viewPager)
  public void onViewPagerClick() {
    onClick();
  }
  
  @OnClick(R.id.postType_textView)
  public void onTypeClick() {
    System.out.println("TYPE");
  }
  
}
