package com.ufcg.les.aep.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MediaUtil {
  
  public static class NetworkAccess extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... src) {
      try {
        URL url = new URL(src[0]);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap result = BitmapFactory.decodeStream(input);
        input.close();
        connection.disconnect();
        return result;
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
  }
  
  public static Bitmap getBitmapFromURL(String url){
    Bitmap result = null;
    try {
      result = new NetworkAccess().execute(url).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return result;//
  }
  
}
