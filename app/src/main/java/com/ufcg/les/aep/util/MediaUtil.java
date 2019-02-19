package com.ufcg.les.aep.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ufcg.les.aep.model.post.Post;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.os.Environment.DIRECTORY_PICTURES;
import static com.ufcg.les.aep.util.LogTag.FAILURE;

public class MediaUtil {
  
  public static final int HIGH_QUALITY = 1;
  private static final String AUTHORITY = "com.ufcg.les.aep";
  private static final String JPG = "jpg";
  private static final String MP4 = "mp4";
  private static final String DAT = "dat";
  public static int MEDIA_CAPTURE;
  
  
  public static Uri getUriFromFile(final Context context, final File file) {
    return FileProvider.getUriForFile(context, AUTHORITY, file);
  }
  
  public static File createObjectFile(final Context context) {
    return createMediaFile(context.getExternalFilesDir(Environment.DIRECTORY_DCIM), DAT, "object");
  }
  
  public static File createImageFile(final Context context) {
    return createMediaFile(context.getExternalFilesDir(DIRECTORY_PICTURES), JPG);
  }
  
  public static File createVideoFile(final Context context) {
    return createMediaFile(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), MP4);
  }
  
  public static Bitmap getResizedBitmap(final Bitmap image, final int bitmapWidth, final int bitmapHeight) {
    return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
  }
  
  
  private static File createMediaFile(final File storageDirectory, final String extension) {
    final @SuppressLint("SimpleDateFormat") String timeStamp =
       new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String fileName = extension.toUpperCase() + "_" + timeStamp + "_";
    return createMediaFile(storageDirectory, extension, fileName);
    
  }
  
  private static File createMediaFile(final File storageDirectory, final String extension, final String fileName) {
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
  
  public static Bitmap getBitmapFromURL(String url) {
    Bitmap result = null;
    try {
      result = new NetworkAccess().execute(url).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return result;
  }
  
  public static Bitmap decodeByteArray(byte[] byteArray) {
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
    final List<Bitmap> result = new ArrayList<>();
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
//    bmOptions.inPurgeable = true;
    
    return BitmapFactory.decodeFile(imagePath, bmOptions);
  }
  
  public static void writePost(final Context context, final Post post) {
    final File file = createObjectFile(context);
    try {
      FileOutputStream fos = new FileOutputStream(file, true);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(post);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static Bitmap rotateBitmap(final Bitmap loadedImage, final float degrees) {
    Bitmap result = null;
    if (loadedImage != null) {
      Matrix rotateMatrix = new Matrix();
      rotateMatrix.postRotate(degrees);
      result = Bitmap.createBitmap(loadedImage, 0, 0,
         loadedImage.getWidth(), loadedImage.getHeight(),
         rotateMatrix, false);
    }
    return result;
  }
  
  public static List<Post> readPostList(final Context context) {
    final File folder = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
    final List<Post> postList = new ArrayList<>();
    if (folder == null) return postList;
    
    for (File file : folder.listFiles()) {
      try {
        FileInputStream fos = new FileInputStream(file);
        ObjectInputStream oos = new ObjectInputStream(fos);
        final Object object = oos.readObject();
        if (object instanceof Post && !postList.contains(object)) {
          postList.add((Post) object);
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return postList;
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
  
}
