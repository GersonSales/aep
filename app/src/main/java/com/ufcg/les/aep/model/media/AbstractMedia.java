package com.ufcg.les.aep.model.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ufcg.les.aep.util.MediaUtil;

import java.io.Serializable;

public class AbstractMedia implements Serializable{
  
  private static final long serialVersionUID = -3428456054992368708L;
  private transient Uri uri;
  private final String uriPath;
  private transient Bitmap thumbnail;

  public AbstractMedia(@NonNull final Uri uri) {
    this.uri = uri;
    this.uriPath = uri.toString();
    this.thumbnail = getThumbnail();
  }
  
  private Bitmap getThumbnailFromPath() {
    if (uri != null) {
      final Bitmap bitmap = MediaUtil.getThumbnailFromPath(350,350,uri.getPath());
      return MediaUtil.rotateBitmap(bitmap, 90);
    }
    return null;
    
  }
  
  public Uri getUri() {
    if (this.uri == null) {
      this.uri = Uri.parse(this.uriPath);
    }
    return uri;
  }
  
  public Bitmap getThumbnail() {
    if (thumbnail == null) {
      thumbnail = getThumbnailFromPath();
    }
    return thumbnail;
  }
  
  
  public void setThumbnail(final Bitmap thumbnail) {
    this.thumbnail = thumbnail;
  }
}
