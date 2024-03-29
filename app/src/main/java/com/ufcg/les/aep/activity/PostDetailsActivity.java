package com.ufcg.les.aep.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostImageAdapter;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Tag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import butterknife.OnTouch;

public class PostDetailsActivity extends AppCompatActivity {
  
  @BindView(R.id.postTitle_textView)
  TextView postTitle;
  
  @BindView(R.id.postImage_viewpager)
  ViewPager imageViewPager;

  @BindView(R.id.postDescription_textView)
  TextView postDescription;

  @BindView(R.id.postName_textView)
  TextView postName;

  @BindView(R.id.postContact_textView)
  TextView postContact;
  
  private Post post;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    
    setContentView(R.layout.activity_post_details);
    ButterKnife.bind(this);
    getPostFromIntent();
    initViewPager();
  }
  
  private void initViewPager() {
    final PostImageAdapter imageAdapter = new PostImageAdapter(this.post.getMediaList());
    imageViewPager.setAdapter(imageAdapter);
  }
  
  private void getPostFromIntent() {
    final Bundle extras = getIntent().getExtras();
    if (extras != null) {
      final Object postObject = extras.get(Tag.POST);
      if (postObject instanceof Post) {
        this.post = (Post) postObject;
        postTitle.setText(Tag.DETAILS_OF.concat(" ").concat(this.post.getTitle()));

        setPostImages(this.post);

        setPostContent(this.post);
      }
    }
  }

  private void setPostImages(final Post post) {
//    if(post.getImages() != null && post.getImages().size() > 0)
//        postImage.setImageBitmap(post.getImages().get(0));
  }

  private void setPostContent(Post post) {
    postDescription.setText("Descrição: " + post.getDescription());
    postName.setText("Name: " + post.getNameContact());
    if(post.getEmailContact() != null && post.getNumberContact() != null) {
      postContact.setText("Numero: " + post.getNumberContact() + " " + "Email: " + post.getEmailContact());
    }
    else if (post.getEmailContact() != null) {
      postContact.setText("Email: " + post.getEmailContact());
    }
    else if (post.getNumberContact() != null) {
      postContact.setText("Numero: " + post.getNumberContact());
    }
    
    //postTags.setText
  }
}
