package com.ufcg.les.aep.model.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.ufcg.les.aep.util.MediaUtil;

import java.io.Serializable;

public class AbstractMedia implements Serializable{
  
  private static final long serialVersionUID = -3428456054992368708L;
  private transient final Uri uri;
  private transient Bitmap thumbnail;
  
  AbstractMedia(final Bitmap bitmap) {
    this.uri = null;
    this.thumbnail = bitmap;
  }
  
  AbstractMedia(final Uri uri) {
    this.uri = uri;
    this.thumbnail = getThumbnailFromPath(uri);
  }
  
  private Bitmap getThumbnailFromPath(Uri uri) {
    return MediaUtil.getThumbnailFromPath(350,350,uri.getPath());
    
  }
  
  public Uri getUri() {
    return uri;
  }
  
  public Bitmap getThumbnail() {
    if (thumbnail == null) {
      thumbnail = getThumbnailFromPath(uri);
    }
    return thumbnail;
  }
  
  
}
