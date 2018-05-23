package com.ufcg.les.aep.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.os.Environment.DIRECTORY_PICTURES;
import static com.ufcg.les.aep.util.LogTag.FAILURE;

public class MediaUtil {
  
  private static final String AUTHORITY = "android.gerson.com.cameraapp";
  private static final String JPG = "jpg";
  private static final String MP4 = "mp4";
  
  public static final int HIGH_QUALITY = 1;
  
  
  public static Uri getUriFromFile(final Context context, final File file) {
    return FileProvider.getUriForFile(context, AUTHORITY, file);
  }
  
  public static File createImageFile(final Context context) {
    return createMediaFile(context.getExternalFilesDir(DIRECTORY_PICTURES), JPG);
  }
  
  public static File createVideoFile(final Context context) {
    return createMediaFile(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), MP4);
  }
  
  
  private static File createMediaFile(final File storageDirectory, final String extension) {
    final @SuppressLint("SimpleDateFormat") String timeStamp =
       new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    
    final String fileName = extension.toUpperCase() + "_" + timeStamp + "_";
    File mediaFile = null;
    try {
      mediaFile = File.createTempFile(
         fileName,  /* prefix */
         "." + extension,         /* suffix */
         storageDirectory      /* directory */
      );
      
    } catch (IOException e) {
      Log.e(FAILURE, e.getMessage(), e);
    }
    return mediaFile;
  }
  
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

  /**
   * This method create a Bitmap thumbnail from the specified file path.
   *
   * @param width     The desired width of the thumbnail.
   * @param height    The desired height of the thumbnail.
   * @param imagePath The path of the file that will be user to create the thumbnail.
   * @return A new generated bitmap.
   */
  public static Bitmap getThumbnailFromPath(final int width, final int height, final String imagePath) {
    final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, bmOptions);
    final int photoW = bmOptions.outWidth;
    final int photoH = bmOptions.outHeight;
    final int scaleFactor = Math.min(photoW / width, photoH / height);
    
    bmOptions.inJustDecodeBounds = false;
    bmOptions.inSampleSize = scaleFactor;
    bmOptions.inPurgeable = true;
    
    return BitmapFactory.decodeFile(imagePath, bmOptions);
  }
  
}
