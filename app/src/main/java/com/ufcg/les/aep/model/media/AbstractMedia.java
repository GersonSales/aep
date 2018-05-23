package com.ufcg.les.aep.model.media;

import android.graphics.Bitmap;
import android.net.Uri;

import com.ufcg.les.aep.util.MediaUtil;

import java.io.Serializable;

public class AbstractMedia implements Serializable{
  
  private static final long serialVersionUID = -3428456054992368708L;
  private final Uri uri;
  private final Bitmap thumbnail;
  
  AbstractMedia(final Uri uri) {
    this.uri = uri;
    this.thumbnail = MediaUtil.getThumbnailFromPath(100,100,uri.getPath());
  }
  
  public Uri getUri() {
    return uri;
  }
  
  public Bitmap getThumbnail() {
    return thumbnail;
  }
}
