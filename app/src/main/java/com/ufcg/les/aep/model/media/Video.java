package com.ufcg.les.aep.model.media;

import android.net.Uri;

import java.io.File;

public final class Video extends AbstractMedia {
  
  Video(final File file, final Uri uri) {
    super(file, uri);
  }
}
