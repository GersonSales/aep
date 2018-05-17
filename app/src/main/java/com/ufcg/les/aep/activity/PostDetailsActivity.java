package com.ufcg.les.aep.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostImageAdapter;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Tag;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailsActivity extends AppCompatActivity {
  
  @BindView(R.id.postTitle_textView)
  TextView postTitle;
  
  @BindView(R.id.postImage_viewpager)
  ViewPager imageViewPager;

//  @BindView(R.id.postImage_imageView)
//  ImageView postImage;

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
    setContentView(R.layout.activity_post_details);
    ButterKnife.bind(this);
    getPostFromIntent();
    initViewPager();
  }
  
  private void initViewPager() {
    final PostImageAdapter imageAdapter = new PostImageAdapter(this.post.getImages());
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
    postDescription.setText(post.getDescription());

    postName.setText("Name: ".concat(" Nome a criar no model POST"));

    postContact.setText("Contato: ".concat("Contato a criar no model POST"));
  }
}
