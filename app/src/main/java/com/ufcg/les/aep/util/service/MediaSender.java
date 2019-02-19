package com.ufcg.les.aep.util.service;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.Image;
import com.ufcg.les.aep.util.MediaUtil;

import java.util.ArrayList;
import java.util.List;

public class MediaSender {
  private static MediaSender instance;
  
  public static MediaSender getInstance() {
    if (instance == null) {
      instance = new MediaSender();
    }
    return instance;
  }
  
  private MediaSender() {
    storageReference = FirebaseStorage.getInstance().getReference();
  }
  
  private final StorageReference storageReference;
  
  
  public List<String> uploadMedia(final String key, final List<AbstractMedia> mediaList) {
    final List<String> mediaPathList = new ArrayList<>();
    
    for (final AbstractMedia media : mediaList) {
      final String path = "images/" + key + "/" + media.hashCode() + "jpg";
      mediaPathList.add(path);
      StorageReference reference = storageReference.child(path);
      reference.putFile(media.getUri()).addOnSuccessListener(taskSnapshot -> {
            PostSender.getInstance().sendPost(key);
         Log.i("MEDIA", "successful");
      }
      
      );
    }
    
    return mediaPathList;
  }
  
  public void downloadMedia(final String path, final Context context, final String postKey) {
    AbstractMedia abstractMedia = new Image(Uri.fromFile(MediaUtil.createImageFile(context)));
    final StorageReference reference = storageReference.child(path);
    reference.getFile(abstractMedia.getUri()).addOnSuccessListener(taskSnapshot -> {
      attachDownloadedMedia(postKey, abstractMedia);
      Log.i("DOWNLOAD", "Image successful downloaded.");
    });
  }
  
  
  private void attachDownloadedMedia(final String postKey, AbstractMedia abstractMedia) {
      PostBuffer.getInstance().attachMedia(postKey, abstractMedia);
  }
  
}
