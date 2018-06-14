package com.ufcg.les.aep.model.media;

import android.graphics.Bitmap;
import android.net.Uri;

public final class Image extends AbstractMedia {
  
  Image(final Uri uri) {
    super(uri);
  }
  public Image(final Bitmap bitmap) {
    super(bitmap);
  }
}
