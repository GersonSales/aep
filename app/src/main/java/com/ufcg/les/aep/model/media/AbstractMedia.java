package com.ufcg.les.aep.model.media;

import android.graphics.Bitmap;
import android.net.Uri;

import com.ufcg.les.aep.util.MediaUtil;

import java.io.File;
import java.io.Serializable;

public class AbstractMedia implements Serializable{
  
  private static final long serialVersionUID = -3428456054992368708L;
  private transient final Uri uri;
  private final File file;
  private transient  Bitmap thumbnail;
  
  AbstractMedia(final File file, final Uri uri) {
    System.out.println("AbstractMedia>>>>" + uri);
    this.uri = uri;
    this.file = file;
//    this.thumbnail = MediaUtil.getThumbnailFromPath(350,350, uri.getPath());
  }
  
  public Uri getUri() {
    return uri;
  }
  
  public Bitmap getThumbnail() {
    return MediaUtil.getThumbnailFromPath(350,350, this.file.getAbsolutePath());
  }
  
  
}
