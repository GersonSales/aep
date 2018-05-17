package com.ufcg.les.aep.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.mock.Mocker;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Constant;
import com.ufcg.les.aep.util.LogTag;
import com.ufcg.les.aep.util.MediaUtil;

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
    Bitmap bitmap = MediaUtil.getBitmapFromURL("https://conteudo.imguol.com.br/p/pp/2013/assistencia-tecnica/dicas/thumb_windows_531x306.jpg");
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    for (int i = 0; i < 3; i ++) {
      bitmaps.add(bitmap);
    }
    Mocker.POST_MOCK.add(new Post("Smart phone","Description", bitmaps , new ArrayList<>()));
    Mocker.POST_MOCK.add(new Post("Child","Description", bitmaps, new ArrayList<>()));
  }
}
