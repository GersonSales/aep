package com.ufcg.les.aep.model.media;

import android.net.Uri;

import java.io.File;

final class Image extends AbstractMedia {
  
  Image(final File file, final Uri uri) {
    super(file, uri);
  }
}
