package com.ufcg.les.aep.model.media;

import android.net.Uri;

public class AbstractMedia {
  
  private final Uri uri;
  
  AbstractMedia(final Uri uri) {
    this.uri = uri;
  }
  
  public Uri getUri() {
    return uri;
  }
}
