package com.ufcg.les.aep.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostImageAdapter;
import com.ufcg.les.aep.model.post.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailsFragment extends Fragment {
  
  @BindView(R.id.post_title_tv)
  TextView postTitle;
  
  @BindView(R.id.postDate_textView)
  TextView postDate;
  
  @BindView(R.id.post_description_tv)
  TextView postDescription;
  
  @BindView(R.id.post_image_tv)
  ViewPager imageViewPager;
  
  @BindView(R.id.post_user_name_tv)
  TextView postName;
  
  @BindView(R.id.post_user_phone_number_tv)
  TextView postPhoneNumber;
  
  @BindView(R.id.post_user_email_tv)
  TextView postEmail;

  
  private Post post;
  
  public static PostDetailsFragment newInstance(Post post) {
    final PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
    postDetailsFragment.setPost(post);
    return postDetailsFragment;
  }
  
  
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                           final Bundle savedInstanceState) {
    clearViewGroup(container);
    
    final View view = inflater.inflate(R.layout.fragment_post_details, container, false);
    ButterKnife.bind(this, view);
    initViewPager();
    setPostContent(this.post);
    setHasOptionsMenu(true);
    
    return view;
  }
  
  @Override
  public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    inflater.inflate(R.menu.menu_post_details, menu);
    super.onPrepareOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.close_post_details:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
  
  private void finish() {
    final FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null) {
      fragmentManager
         .beginTransaction()
         .replace(R.id.content_frame_layout, new FeedFragment())
         .detach(this)
         .commit();
    }
  }
  
  private void clearViewGroup(final ViewGroup container) {
    if (container != null) {
      container.removeAllViews();
    }
  }
  
  private void initViewPager() {
    final PostImageAdapter imageAdapter = new PostImageAdapter(this.post.getMediaList());
    imageViewPager.setAdapter(imageAdapter);
  }
  
  private void setPostContent(final Post post) {
    postTitle.setText(post.getTitle());
    postDate.setText(post.getCreationDate());
    
    postDescription.setText(post.getDescription());
    postName.setText(post.getOwnerName());
    postPhoneNumber.setText(post.getOwnerPhoneNumber());
    postEmail.setText(post.getOwnerEmail());
    
  }
  
  private void setPost(final Post post) {
    this.post = post;
  }
}
