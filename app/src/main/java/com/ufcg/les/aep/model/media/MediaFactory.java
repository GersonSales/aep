package com.ufcg.les.aep.model.media;

import android.content.Context;
import android.gerson.com.cameraapp.util.MediaUtil;

import java.io.File;

public final class MediaFactory {
  
  public enum MediaKey{
    IMAGE, VIDEO
  }
  
  public static AbstractMedia getMedia(final Context context, final MediaKey mediaKey) {
    AbstractMedia mediaResult = null;
    switch (mediaKey) {
      case IMAGE:
        mediaResult = createImage(context);
        break;
      case VIDEO:
        mediaResult = createVideo(context);
        break;
    }
    
    return mediaResult;
  }
  
  private static AbstractMedia createVideo(final Context context) {
    final File file  = MediaUtil.createVideoFile(context);
    return new Video(MediaUtil.getUriFromFile(context, file));
  }
  
  private static Image createImage(final Context context) {
    final File file = MediaUtil.createImageFile(context);
    return new Image(MediaUtil.getUriFromFile(context, file));
  }
}
