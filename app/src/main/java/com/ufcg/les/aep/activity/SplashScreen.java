package com.ufcg.les.aep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.mock.Mocker;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Constant;
import com.ufcg.les.aep.util.LogTag;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    
    new Thread() {
      @Override
      public void run() {
        try {
          sleep(Constant.TWO_SECONDS);
          initPostMock();
          startActivity(new Intent(SplashScreen.this, FeedActivity.class));
          finish();
        } catch (InterruptedException error) {
          Log.e(LogTag.FAILURE, error.getMessage(), error);
        }
      }
    }.start();
  }
  
  private void initPostMock() {
    Mocker.POST_MOCK.add(new Post("Smart phone","Description", new ArrayList<>(), new ArrayList<>()));
    Mocker.POST_MOCK.add(new Post("Child","Description", new ArrayList<>(), new ArrayList<>()));
  }
}
