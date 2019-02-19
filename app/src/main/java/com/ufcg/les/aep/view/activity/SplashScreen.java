package com.ufcg.les.aep.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
import com.ufcg.les.aep.R;

public class SplashScreen extends AppCompatActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);
    FirebaseApp.initializeApp(this);
    ImageView image =findViewById(R.id.image_splash);
    image.setScaleType(ImageView.ScaleType.FIT_XY);
    new Handler().postDelayed(() -> {
      startActivity(new Intent(SplashScreen.this, SignActivity.class));
      finish();
    }, 1000);
  }
  

  
}
