package com.ufcg.les.aep.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
  
  private static Bitmap getBitmapFromURL(String url){
    Bitmap result = null;
    try {
      result = new NetworkAccess().execute(url).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return result;
  }
  
  public  static Bitmap decodeByteArray(byte[] byteArray) {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
  }
  
  public static List<Bitmap> decodeByteArrayList(final List<byte[]> byteArrayList) {
    final List<Bitmap> result = new ArrayList<>();
    for (final byte[] byteArray : byteArrayList) {
      result.add(decodeByteArray(byteArray));
    }
    return result;
  }
  
  public static byte[] encodeBitmap(final Bitmap bitmap) {
    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
    return bStream.toByteArray();
  }
  
  public static List<byte[]> encodeBitmapList(final List<Bitmap> bitmapList) {
    final List<byte[]> result = new ArrayList<>();
    for (final Bitmap bitmap : bitmapList) {
      result.add(encodeBitmap(bitmap));
    }
    return result;
  }
  
  public static List<Bitmap> getBitmapListFromURL(String... urlList) {
    final List<Bitmap> result  = new ArrayList<>();
    for (final String url : urlList) {
      result.add(MediaUtil.getBitmapFromURL(url));
    }
    
    return result;
    
  }
  
}
