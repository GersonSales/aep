package com.ufcg.les.aep.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Tag;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailsActivity extends AppCompatActivity {
  
  @BindView(R.id.details_textView)
  TextView details;

  @BindView(R.id.postImage_imageView)
  ImageView postImage;
  
  private Post post;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_details);
    ButterKnife.bind(this);
    getPostFromIntent();
  }
  
  private void getPostFromIntent() {
    final Bundle extras = getIntent().getExtras();
    if (extras != null) {
      final Object postObject = extras.get(Tag.POST);
      if (postObject instanceof Post) {
        this.post = (Post) postObject;
        details.setText(Tag.DETAILS_OF.concat(" ").concat(this.post.getTitle()));

        if(this.post.getImages().size() > 0) {
          postImage.setImageBitmap(this.post.getImages().get(0));
        }
      }
    }
  }
}
