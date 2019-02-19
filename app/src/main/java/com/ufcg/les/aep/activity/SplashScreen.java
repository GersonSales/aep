package com.ufcg.les.aep.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import com.ufcg.les.aep.model.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.mock.Mock;
import com.ufcg.les.aep.model.mock.Mocker;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.MediaUtil;

import java.util.ArrayList;
import java.util.List;

import static com.ufcg.les.aep.util.MediaUtil.getBitmapListFromURL;

public class SplashScreen extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    
    new Thread() {
      @Override
      public void run() {
        initPostMock();
        startActivity(new Intent(SplashScreen.this, FeedActivity.class));
        finish();
      }
    }.start();
  }


  
  private void initPostMock() {
  
    Mocker.POST_MOCK.addAll(MediaUtil.readPostList(this));
    
    final String[] iphone4LinkList = {
       "https://goo.gl/C5JgAJ",
       "https://goo.gl/ud3n9L"
    };

    List<AbstractMedia> medias = new ArrayList<>();
    
    for (final Bitmap bitmap : getBitmapListFromURL(iphone4LinkList)) {
//      medias.add(new Image(bitmap));
    }
  

//    final Post iphone4Post = new Post("Iphone 4", "description", medias, null, null);
//    final Post iphone4Post = new Post("Iphone4", "I've lost an Iphone 4", getBitmapListFromURL(iphone4LinkList), null, null);
//    Mocker.POST_MOCK.add(iphone4Post);
//
//
//    final String[] s8LinkList = {
//       "https://goo.gl/grQs1z",
//       "https://goo.gl/MkQLXG",
//       "https://goo.gl/cTcxyC"
//    };
//
//    final Post s8Post = new Post("Galaxy S8", "I've lost my Samsumg Galaxy S8", getBitmapListFromURL(s8LinkList), null);
//    Mocker.POST_MOCK.add(s8Post);
//
//    final String[] dellLinkList = {
//       "https://goo.gl/EbrH2z",
//    };
//
//    final Post dellPost = new Post("DELL laptop", "I've found a DELL laptop.", getBitmapListFromURL(dellLinkList), null);
//    Mocker.POST_MOCK.add(dellPost);
//
//    final String[] raybanLinkList = {
//       "https://goo.gl/KZKZr9",
//       "https://goo.gl/TAfYHW"
//    };
//
//    final Post raybanPost = new Post("RayBan", "I've lost my RayBan.", getBitmapListFromURL(raybanLinkList), null);
//    Mocker.POST_MOCK.add(raybanPost);
    
    
  }
  

  
}
